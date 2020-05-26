package ext.validation;

import ext.util.ReflectTool;
import ext.validation.unit.IValidate;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 表示一个验证规则的验证单元
 */
public class ValidateRuleUnit {
    private String field;
    private Iterable<IValidate> validates;

    public ValidateRuleUnit(String field, Iterable<IValidate> validates){
        this.field =field;
        this.validates = validates;
    }
    public ValidateRuleUnit(String field, IValidate... validates){
        this.field = field;
        this.validates = Arrays.asList(validates);
    }

    public String validate(Object element) throws IllegalAccessException {
        Field field = ReflectTool.fieldOfRename(element.getClass(), this.field);
        for (IValidate validate: validates){
            String u = validate.validate(this.field, ReflectTool.getValue(element, field));
            if(u != null)
                return u;
        }
        return null;
    }

    public String getField(){
        return this.field;
    }

}
