package dao;

import java.util.HashMap;

public class LoginViewModel {
    public LoginViewModel(){

    }

    /**
     * 登录界面显示的消息
     */
    private String msg = null;
    /**
     * 表单验证的错误信息
     */
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
