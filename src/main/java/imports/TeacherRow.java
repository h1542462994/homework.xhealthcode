package imports;

import util.Checker;

import javax.servlet.annotation.WebInitParam;

public class TeacherRow {
    private int index;
    private String name;
    private String number;
    private String idCard;
    private String college;
    private String adminType;
    private String password;
    private String msg;

    public boolean isValid(){
        msg = "";
        boolean valid = true;
        String checkName = Checker.checkName(name);
        String checkNumber = Checker.checkNumber(number);
        String checkIdCard = Checker.checkIdCard(idCard);
        String checkPassword = Checker.checkPassword(password);

        if(checkName != null){
            msg += checkName + ";";
            valid = false;
        }
        if(checkNumber != null){
            msg += checkNumber + ";";
            valid = false;
        }
        if(checkIdCard != null){
            msg += checkIdCard + ";";
            valid = false;
        }

        if(adminType != null){
            if(adminType.equals("校级管理员") || adminType.equals("院级管理员")){
                if(checkPassword != null){
                    msg += checkPassword + ";";
                    valid = false;
                }
                if(adminType.equals("院级管理员") && college == null){
                    msg += "院级管理员必须有对应的college;";
                    valid = false;
                }
            }
        }
        return valid;
    }

    public boolean isNormal(){
       return adminType == null || adminType.equals("院级管理员");
    }

    public TeacherRow(int index, String name, String number, String idCard, String college, String adminType, String password) {
        this.index = index;
        this.name = name;
        this.number = number;
        this.idCard = idCard;
        this.college = college;
        this.adminType = adminType;
        this.password = password;
    }

    public TeacherRow() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getAdminType() {
        return adminType;
    }

    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
