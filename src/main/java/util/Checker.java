package util;

public class Checker {
    public static String checkName(String value){
        if(value == null || value.equals("")){
            return "name为空";
        } else if(value.length() > 20){
            return "name超过20字符";
        }
        return null;
    }

    public static String checkNumber(String value){
        if(value == null || value.equals("")){
            return "number为空";
        } else if(value.length() < 6){
            return "number长度小于6个字符";
        } else if(value.length() > 20){
            return "number长度大于20个字符";
        }
        return null;
    }

    public static String checkIdCard(String value){
        if(value == null || value.equals("")) {
            return "idCard为空";
        } else if(value.length() != 18){
            return "idCard的长度不为18";
        }
        return null;
    }

    public static String checkPassword(String value){
        if(value == null || value.equals("")){
            return "password为空";
        } else if(value.length() < 6){
            return "password长度小于6个字符";
        } else if(value.length() > 128){
            return "password长度大于128个字符";
        }
        return null;
    }
}
