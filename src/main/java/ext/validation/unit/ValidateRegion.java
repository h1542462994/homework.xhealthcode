package ext.validation.unit;

import ext.annotation.Region;

import java.lang.reflect.Field;

public class ValidateRegion implements IValidate {
    private int min;
    private int max;

    public ValidateRegion(int min, int max){
        this.min = min;
        this.max = max;
    }

    public static ValidateRegion fromRegionAnnotation(Field field){
        Region region = field.getAnnotation(Region.class);
        if(region != null){
            return new ValidateRegion(region.min(), region.max());
        }
        return null;
    }

    @Override
    public String validate(String field, Object value) {
        boolean flag = true;
        String msg = null;

        if(value == null){
            flag = false;
            msg = "字段不能为空";
        } else if (value.getClass().equals(String.class)){
            String v = (String)value;
            if(v.length() < min){
                flag = false;
                msg = "字段的长度不应小于" + min;
            } else if(v.length() > max){
                flag = false;
                msg = "字段的长度不应大于" + max;
            }
        } else {
            Number v = (Number)value;
            if(v.intValue() < min){
                flag = false;
                msg = "字段的数值不应小于" + min;
            } else if(v.intValue() > max) {
                flag = false;
                msg = "字段的数值不应大于" + max;
            }
        }

        if(!flag){
            return field + msg;
        }

        return null;
    }
}
