package ext.admin;

public class RoleNotSupportedException extends Throwable {
    public RoleNotSupportedException() {

    }

    public RoleNotSupportedException(String msg){
        super(msg);
    }

    public RoleNotSupportedException(String msg, Throwable cause){
        super(msg, cause);
    }
}
