package ext.exception;

import ext.validation.ValidateMsg;

public class ValidateFailedException extends Exception {
    private ValidateMsg msg;


    public ValidateFailedException(ValidateMsg msg){
        super(msg.toString());
        this.msg = msg;
    }

    public ValidateMsg getMsg() {
        return msg;
    }
}
