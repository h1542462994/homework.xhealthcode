package ext.validation;

import ext.annotation.*;
import ext.exception.ValidateFailedException;
import ext.util.ReflectTool;
import ext.util.ValueConverter;
import ext.validation.unit.IValidate;
import ext.validation.unit.ValidateReg;
import ext.validation.unit.ValidateRegion;
import ext.validation.unit.ValidateRequired;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Validator {
    public static <T> T assertValue(Class<T> type, HttpServletRequest request) throws ValidateFailedException {
        try {
            T element = type.getConstructor().newInstance();
            fill(element, request);
            HashMap<String, String> msg = check(element);
            if(msg != null){
                throw new ValidateFailedException(msg);
            }
            return element;
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ValidateFailedException(new HashMap<>());
        }
    }

    public static boolean tryGet(Object model, HttpServletRequest request){
        return true;
    }

    public static <T> void fill(T element, HttpServletRequest request) throws IllegalAccessException {
        for (Field field: element.getClass().getDeclaredFields()) {
            fillOne(element, field, request);
        }
    }

    private static <T> void fillOne(T element, Field field, HttpServletRequest request) throws IllegalAccessException {
        Class<T> elementType = (Class<T>)element.getClass();
        String column = ReflectTool.renameOfField(field);
        if (!ReflectTool.hasAnnotation(field, ValidationIgnore.class)) {
            String preValue = request.getParameter(column);
            if(preValue != null){
                ReflectTool.setValue(element, field, ValueConverter.string2Type(field.getType(), preValue));
            }
        }
    }

    /**
     * 检查对象
     * @param element 元素
     * @param <T> 元素的类型
     * @return 验证的数据，如果值为null，则通过验证。
     */
    public static <T> HashMap<String, String> check(T element) throws ValidateFailedException, IllegalAccessException {
        ArrayList<ValidateRuleUnit> rules = new ArrayList<>();
        for (Field field : element.getClass().getDeclaredFields()) {
            ArrayList<IValidate> validates = new ArrayList<>();
            Required required = field.getAnnotation(Required.class);
            if(required != null){
                validates.add(new ValidateRequired());
            }
            Region region = field.getAnnotation(Region.class);
            if(region != null){
                validates.add(new ValidateRegion(region.min(), region.max()));
            }
            Reg reg = field.getAnnotation(Reg.class);
            if(reg != null){
                validates.add(new ValidateReg(reg.reg(), reg.msg()));
            }
            if(validates.size() > 0){
                rules.add(new ValidateRuleUnit(ReflectTool.renameOfField(field), validates));
            }
        }

        ValidateRule rule = new ValidateRule(rules);
        return rule.validate(element);
    }

    @Deprecated
    private static <T> String requiredPass(T element, Field field) throws IllegalAccessException {
        boolean flag = true;
        Class<?> fieldType = field.getType();
        Object fieldValue = ReflectTool.getValue(element, field);
        if(ReflectTool.hasAnnotation(field, Required.class)){
            if(fieldValue == null){
                flag = false;
            } else if (fieldType.equals(String.class) && ((String)fieldValue).isEmpty()){
                flag = false;
            }

            if(!flag){
                return ReflectTool.renameOfField(field) + "字段不能为空";
            }
        }
        return null;
    }

    @Deprecated
    private static <T> String regionPass(T element, Field field) throws IllegalAccessException {
        boolean flag = true;
        String msg = null;
        Class<?> fieldType = field.getType();
        Object fieldValue = ReflectTool.getValue(element, field);
        Region region = field.getAnnotation(Region.class);
        if(region != null) {
            if(fieldValue == null){
                flag = false;
                msg = "字段不能为空";
            } else if (fieldType.equals(String.class)){
                String v = (String)fieldValue;
                if(v.length() < region.min()){
                    flag = false;
                    msg = "字段的长度不应小于" + region.min();
                } else if(v.length() > region.max()){
                    flag = false;
                    msg = "字段的长度不应大于" + region.max();
                }
            } else {
                Number v = (Number)fieldValue;
                if(v.intValue() < region.min()){
                    flag = false;
                    msg = "字段的数值不应小于" + region.min();
                } else if(v.intValue() > region.max()) {
                    flag = false;
                    msg = "字段的数值不应大于" + region.max();
                }
            }

            if(!flag){
                return ReflectTool.renameOfField(field) + msg;
            }
        }
        return null;
    }

    @Deprecated
    private static <T> String regionDoublePass(T element, Field field) throws IllegalAccessException {
        boolean flag = true;
        String msg = null;
        Class<?> fieldType = field.getType();
        Object fieldValue = ReflectTool.getValue(element, field);
        RegionDouble regionDouble = field.getAnnotation(RegionDouble.class);
        if(regionDouble != null) {
            if(fieldValue == null){
                flag = false;
                msg = "字段不能为空";
            } else {
                Number v = (Number)fieldValue;
                if(v.doubleValue() < regionDouble.min()){
                    flag = false;
                    msg = "字段的数值不应小于" + regionDouble.min();
                } else if(v.doubleValue() > regionDouble.max()) {
                    flag = false;
                    msg = "字段的数值不应大于" + regionDouble.max();
                }
            }

            if(!flag){
                return ReflectTool.renameOfField(field) + msg;
            }
        }
        return null;
    }

    @Deprecated
    private static <T> String emailPass(T element, Field field) throws IllegalAccessException {
        boolean flag = true;
        String msg = null;
        Class<?> fieldType = field.getType();
        Object fieldValue = ReflectTool.getValue(element, field);
        if(ReflectTool.hasAnnotation(field, Email.class)) {
            String v = (String)fieldValue;
            if(!v.matches("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")){
                return ReflectTool.renameOfField(field) + "字段不符合邮箱的规则";
            }
        }
        return null;
    }

    @Deprecated
    private static <T> String regPass(T element, Field field) throws IllegalAccessException {
        boolean flag = true;
        String msg = null;
        Class<?> fieldType = field.getType();
        Object fieldValue = ReflectTool.getValue(element, field);
        Reg reg = field.getAnnotation(Reg.class);
        if(reg != null) {
            String v = (String)fieldValue;
            if(!v.matches(reg.reg())){
                return ReflectTool.renameOfField(field) + "字段" + reg.msg();
            }
        }
        return null;
    }
}
