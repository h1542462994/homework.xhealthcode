package ext.exception;

public class OperationFailedException extends Exception {
    public OperationFailedException(String message){
        super(message);
    }

    public OperationFailedException(String message, Exception cause){
        super(message, cause);
    }
}