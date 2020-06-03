package ext.exception;

import java.util.HashMap;

public class ValidateFailedException extends Exception {
    private final HashMap<String, String> msg;


    public ValidateFailedException(HashMap<String, String> msg){
        super(msg.toString());
        this.msg = msg;
    }

    public HashMap<String, String> getMsg() {
        return msg;
    }
}
