package dao;

import java.util.HashMap;

public class AcquireViewModel {
    public AcquireViewModel(){

    }


    /**
     * 表单验证的错误信息
     */
    private HashMap<String, String> errors = null;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
