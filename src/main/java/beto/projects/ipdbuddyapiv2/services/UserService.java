package beto.projects.ipdbuddyapiv2.services;
import beto.projects.ipdbuddyapiv2.exceptions.AccountalreadyExistsException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.FirebaseAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final FirebaseAuth firebaseAuth;

    public UserService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }
    private static final String DUPLICATE_ACCOUNT_ERROR = "EMAIL_EXISTS";

    public void createUser(String emailId, String password) throws FirebaseAuthException {
        log.info("Attempting to create a user with email: {}", emailId);

        UserRecord.CreateRequest request = new UserRecord.CreateRequest();
        request.setEmail(emailId);
        request.setPassword(password);
        request.setEmailVerified(Boolean.TRUE);

        try {
            UserRecord userRecord = firebaseAuth.createUser(request);
            log.info("User Created successfully with UID: {} and Email: {}", userRecord.getUid(), userRecord.getEmail());
        } catch (FirebaseAuthException exception){

            if (exception.getMessage() != null && exception.getMessage().contains(DUPLICATE_ACCOUNT_ERROR)) {
                log.warn("Account with email {} already exists", emailId);
                throw new AccountalreadyExistsException("Account with given email-id already exists");
            }

            log.error("Unexpected error while creating user: {}", exception.getMessage(), exception);
            throw new RuntimeException("Failed to create user", exception);
        }
    }
}
