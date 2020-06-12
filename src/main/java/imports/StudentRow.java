package imports;


import util.Checker;

public class StudentRow {
    private int index;
    private String name;
    private String number;
    private String idCard;
    private String xclass;
    private String msg;

    public boolean isValid(){
        msg = "";
        boolean valid = true;
        String checkName= Checker.checkName(name);
        String checkNumber = Checker.checkNumber(number);
        String checkIdCard = Checker.checkIdCard(idCard);
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
        if(xclass == null){
            msg += "xclass为空!;";
            valid =false;
        }

        return valid;
    }

    public StudentRow(int index, String name, String number, String idCard, String xclass) {
        this.index = index;
        this.name = name;
        this.number = number;
        this.idCard = idCard;
        this.xclass = xclass;
    }

    public StudentRow() {
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

    public String getXclass() {
        return xclass;
    }

    public void setXclass(String xclass) {
        this.xclass = xclass;
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
