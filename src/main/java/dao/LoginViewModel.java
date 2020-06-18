package dao;

import java.util.HashMap;

public class LoginViewModel extends ViewModel {
    public LoginViewModel(){

    }

    /**
     * 表单验证的错误信息
     */
    private HashMap<String, String> errors = null;

    private String redirectUrl = null;


    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
