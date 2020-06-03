package ext.validation.unit;

public class ValidateReg implements IValidate {
    private final String reg;
    private final String msg;

    public ValidateReg (String reg, String msg){
        this.reg = reg;
        this.msg = msg;
    }

    @Override
    public String validate(String field, Object value) {

        String v = (String)value;

        if(!v.matches(reg)){
            return field + "字段" + msg;
        }

        return null;
    }
}
