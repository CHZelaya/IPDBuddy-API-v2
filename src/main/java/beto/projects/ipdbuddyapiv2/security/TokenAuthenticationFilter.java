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
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        log.info("Authorization Header Received: '{}'", authorizationHeader);


        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.replace(BEARER_PREFIX, "").trim();
            JwtDebugger.debugToken(token);


            //! Logger
            log.debug("Received Bearer token: {}", token);

            Optional<String> userId = extractUserIdFromToken(token);

            if (userId.isPresent()) {

                //! Logger
                log.info("Token validated successfully. User ID: {}", userId.get());

                var authentication = new UsernamePasswordAuthenticationToken(userId.get(), null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.info("Authorization Header Received: '{}'", authorizationHeader);
                //! Logger
                log.warn("Failed to validate token: {}", token);
                setAuthErrorDetails(response);
                return;
            }
        } else {
            // ! Logger
            log.debug("No Bearer token found in Authorization header");
        }
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Unexpected error in filter chain", ex);
            throw ex;
        }
    }



    private Optional<String> extractUserIdFromToken(String token) {
        try {
            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token, true);
            String userId= firebaseToken.getUid();
            //String userId = String.valueOf(firebaseToken.getClaims().get(USER_ID_CLAIM));
            //! Logger
            log.debug("Extracted user_id claim: {}", userId);

            return Optional.of(userId);
        } catch (FirebaseAuthException exception) {
            //! Logger
            log.error("FirebaseAuthException while verifying token: {}", exception.getMessage());
            return Optional.empty();
        }
    }



    private void setAuthErrorDetails(HttpServletResponse response) throws IOException {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        response.setStatus(unauthorized.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(unauthorized,
                "Authentication failure: Token missing, invalid or expired");
        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));
    }

}
