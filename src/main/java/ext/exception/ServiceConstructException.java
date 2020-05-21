package ext.exception;

public class ServiceConstructException extends Exception {
    public ServiceConstructException(String message){
        super(message);
    }

    public ServiceConstructException(String message, Exception cause){
        super(message, cause);
    }
}
