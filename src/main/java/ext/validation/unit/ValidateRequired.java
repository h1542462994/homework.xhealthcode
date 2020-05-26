package ext.validation.unit;

public class ValidateRequired implements IValidate {
    @Override
    public String validate(String field, Object value){
        boolean flag = true;

        if (value == null) {
            flag = false;
        } else if (value.getClass().equals(String.class) && ((String)value).isEmpty()) {
            flag = false;
        }

        if (!flag) {
            return field + "字段不能为空";
        }
        return null;
    }
}
