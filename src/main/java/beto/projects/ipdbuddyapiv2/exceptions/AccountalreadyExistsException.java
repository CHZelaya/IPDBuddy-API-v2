package beto.projects.ipdbuddyapiv2.exceptions;

import io.opencensus.internal.DefaultVisibilityForTesting;

public class AccountalreadyExistsException extends RuntimeException{
    public AccountalreadyExistsException(String message){
        super(message);
    }
}
