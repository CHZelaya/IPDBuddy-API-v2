package beto.projects.ipdbuddyapiv2.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);


    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ID_CLAIM = "user_id";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final FirebaseAuth firebaseAuth;
    private final ObjectMapper objectMapper;

    // Constructor + Dependency Injection
    public TokenAuthenticationFilter(FirebaseAuth firebaseAuth, ObjectMapper objectMapper) {
        this.firebaseAuth = firebaseAuth;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Skip filter for preflight OPTIONS requests
        // This is important for CORS preflight requests, which do not require authentication
        // Originally added while troubleshooting CORS issues
        // The real issue ended up being incorrect Netlify environment variable configuration.
        // However, keeping this check as a good practice to avoid unnecessary processing
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.info("Skipping TokenAuthenticationFilter for preflight OPTIONS request.");
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        log.debug("Authorization Header Received (token redacted)");
        log.info("Incoming Request: [{} {}]", request.getMethod(), request.getRequestURI());


        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.replace(BEARER_PREFIX, "").trim();


            Optional<FirebaseToken> firebaseTokenOpt = extractFirebaseTokenFromToken(token);

            if (firebaseTokenOpt.isPresent()) {
                FirebaseToken firebaseToken = firebaseTokenOpt.get();

                log.info("Token validated successfully. User ID: {}", firebaseToken.getUid());

                var authentication = new UsernamePasswordAuthenticationToken(firebaseToken, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.warn("Failed to validate token: token invalid or expired");
                setAuthErrorDetails(response);
                return;
            }
        } else {
            log.debug("No Bearer token found in Authorization header");
        }

        filterChain.doFilter(request, response);
    }

    private Optional<FirebaseToken> extractFirebaseTokenFromToken(String token) {
        try {
            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token, true);
            log.debug("Extracted user_id claim: {}", firebaseToken.getUid());
            return Optional.of(firebaseToken);
        } catch (FirebaseAuthException exception) {
            log.error("FirebaseAuthException while verifying token: {}", exception.getAuthErrorCode());
            return Optional.empty();
        }
    }


/*
    // This method is commented out because it was not used in the current implementation.
    // It was originally intended to extract the user_id claim from the Firebase token,
    // The contractors are being identified by their email address instead.
 */

//    private Optional<FirebaseToken> extractUserIdFromToken(String token) {
//        try {
//            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token, true);
//            //String userId = String.valueOf(firebaseToken.getClaims().get(USER_ID_CLAIM));
//            //! Logger
//            log.debug("Extracted user_id claim: {}", firebaseToken.getUid());
//
//            return Optional.of(firebaseToken);
//        } catch (FirebaseAuthException exception) {
//            //! Logger
//            log.error("FirebaseAuthException while verifying token: {}", exception.getNotes());
//            return Optional.empty();
//        }
//    }


    /**
     * Sets the HTTP response status and content type for authentication errors.
     * This method is called when the token is missing, invalid, or expired.
     *
     * @param response the HttpServletResponse to set the error details on
     * @throws IOException if an I/O error occurs while writing the response
     */
    private void setAuthErrorDetails(HttpServletResponse response) throws IOException {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        response.setStatus(unauthorized.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(unauthorized,
                "Authentication failure: Token missing, invalid or expired");
        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));
    }

}
