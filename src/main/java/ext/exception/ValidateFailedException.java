package ext.exception;

public class ValidateFailedException extends Exception {
    public ValidateFailedException(String message){
        super(message);
    }

    public ValidateFailedException(String message, Exception cause){
        super(message, cause);
    }

}
