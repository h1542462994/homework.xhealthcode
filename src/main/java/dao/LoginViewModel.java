package dao;

import java.util.HashMap;

public class LoginViewModel {
    public LoginViewModel(){

    }


    private String msg = null;
    private HashMap<String, String> errors = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
