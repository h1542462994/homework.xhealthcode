package ext.validation;

import ext.exception.ValidateFailedException;

import javax.servlet.http.HttpServletRequest;

public class Validator {
    public static <T> T assertValue(Class<T> type, HttpServletRequest request) throws ValidateFailedException {
        return  null;
    }

    public static boolean tryGet(Object model, HttpServletRequest request){
        return true;
    }
}
