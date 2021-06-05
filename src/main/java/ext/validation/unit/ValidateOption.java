package ext.validation.unit;

import java.util.Arrays;

public class ValidateOption<T> implements IValidate {
    private final T[] args;


    public ValidateOption(T... args) {
        this.args = args;
    }
    @Override
    public String validate(String field, Object value) {
        for (var arg: args) {
            if (value.equals(arg)) {
                return null;
            }
        }
        return "没有找到匹配的项";
    }
}
