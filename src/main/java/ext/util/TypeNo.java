package ext.util;

import ext.Tuple;
import ext.sql.Column;
import ext.sql.Entity;
import ext.sql.Primary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class TypeNo {
    public static boolean isPublic(Field field){
        return (field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC;
    }

    public static Object getValue(Object object, Field field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            Method getter = object.getClass().getMethod("get" + firstToUpper(field.getName()));
            return getter.invoke(object);
        } catch (NoSuchMethodException e){
            // e.printStackTrace();
            if (isPublic(field)){
                return field.get(object);
            } else {
                throw e;
            }
        }
    }

    public static void setValue(Object object, Field field, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            Method setter = object.getClass().getMethod("set" + firstToUpper(field.getName()), field.getType());
            setter.invoke(object, value);
        } catch (NoSuchMethodException e){
            if (isPublic(field)){
                field.set(object, value);
            } else {
                throw e;
            }
        }
    }

    public static void setColumn(Object object, String column, Object value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        setValue(object, getField(object.getClass(), column), value);
    }

    public static Object getColumn(Object object, String column) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return getValue(object, getField(object.getClass(), column));
    }

    public static String modelName(Class<?> type){
        Entity entity = type.getAnnotation(Entity.class);
        if(entity != null) {
            return entity.model();
        } else {
            return type.getSimpleName();
        }
    }

    public static Tuple<String, Class<?>> modelPrimaryColumn(Class<?> type){
        for(Field field: type.getDeclaredFields()){
            Primary primary = field.getAnnotation(Primary.class);
            if(primary != null) {
                return new Tuple<>(fieldColumnName(field), field.getType());
            }
        }
        return new Tuple<>("id", getField(type, "id").getType());
    }

    public static String fieldColumnName(Field field){
        Column column = field.getAnnotation(Column.class);
        if (column == null){
            return field.getName();
        } else {
            String name = column.name();
            if(name.equals("")){
                return field.getName();
            } else {
                return name;
            }
        }
    }

    public static Field getField(Class<?> type, String columnName){
        for(Field field: type.getDeclaredFields()){
            if(fieldColumnName(field).equals(columnName)){
                return field;
            }
        }
        return null;
    }

    public static String firstToUpper(String value){
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }
}
