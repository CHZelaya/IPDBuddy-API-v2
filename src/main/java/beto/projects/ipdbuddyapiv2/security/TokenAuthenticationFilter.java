package beto.projects.ipdbuddyapiv2.security;

import beto.projects.ipdbuddyapiv2.utils.JwtDebugger;
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

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.info("Skipping TokenAuthenticationFilter for preflight OPTIONS request.");
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        log.info("Authorization Header Received: '{}'", authorizationHeader);
        log.info("Incoming Request: [{} {}]", request.getMethod(), request.getRequestURI());


        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.replace(BEARER_PREFIX, "").trim();
            JwtDebugger.debugToken(token);

            log.debug("Received Bearer token: {}", token);

            Optional<FirebaseToken> firebaseTokenOpt = extractFirebaseTokenFromToken(token);

            if (firebaseTokenOpt.isPresent()) {
                FirebaseToken firebaseToken = firebaseTokenOpt.get();

                log.info("Token validated successfully. User ID: {}", firebaseToken.getUid());

                var authentication = new UsernamePasswordAuthenticationToken(firebaseToken, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.warn("Failed to validate token: {}", token);
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
            log.error("FirebaseAuthException while verifying token: {}", exception.getMessage());
            return Optional.empty();
        }
    }




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



    private void setAuthErrorDetails(HttpServletResponse response) throws IOException {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        response.setStatus(unauthorized.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(unauthorized,
                "Authentication failure: Token missing, invalid or expired");
        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));
    }

}
