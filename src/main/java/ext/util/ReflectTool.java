package ext.util;

import ext.Tuple;
import ext.sql.Column;
import ext.sql.Entity;
import ext.sql.Primary;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * 为反射提供工具类
 */
public class ReflectTool {
    public static boolean isFieldPublic(Field field){
        return Modifier.isPublic(field.getModifiers());
    }

    private static Method getFieldGetter(Class<?> type, Field field) throws NoSuchMethodException {
        return type.getMethod("get" + firstToUpper(field.getName()));
    }

    private static Method getFieldSetter(Class<?> type, Field field) throws NoSuchMethodException {
        return type.getMethod("set" + firstToUpper(field.getName()), field.getType());
    }

    public static Object getValue(Object object, Field field) throws IllegalAccessException {
        try {
            Method getter = getFieldGetter(object.getClass(), field);
            return getter.invoke(object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return field.get(object);
        }
    }

    public static void setValue(Object object, Field field, Object value) throws IllegalAccessException {
        try {
            Method setter = getFieldSetter(object.getClass(), field);
            setter.invoke(object, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            field.set(object, value);
        }
    }

    public static String firstToUpper(String value){
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static Field fieldOfColumn(Class<?> type, String column){
        for(Field field: type.getDeclaredFields()){
            if(columnOfField(field).equals(column)){
                return field;
            }
        }
        return null;
    }

    public static String columnOfField(Field field){
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

    public static void setColumn(Object object, String column, Object value) throws IllegalAccessException {
        setValue(object, fieldOfColumn(object.getClass(), column), value);
    }

    public static Object getColumn(Object object, String column) throws  IllegalAccessException {
        return getValue(object, fieldOfColumn(object.getClass(), column));
    }

    public static String getEntityName(Class<?> type){
        Entity entity = type.getAnnotation(Entity.class);
        if(entity != null) {
            return entity.model();
        } else {
            return type.getSimpleName();
        }
    }

    public static <T> Field getPrimaryColumn(Class<T> type) throws NoSuchFieldException {
        for(Field field: type.getDeclaredFields()){
            Primary primary = field.getAnnotation(Primary.class);
            if(primary != null){
                return field;
            }
        }
        return type.getDeclaredField("id");
    }

    public static <T> String getPrimaryColumnName(Class<T> type) throws NoSuchFieldException {
        return columnOfField(getPrimaryColumn(type));
    }

    public static <T> Object getPrimaryValue(T element) throws NoSuchFieldException, IllegalAccessException {
        return getValue(element, getPrimaryColumn(element.getClass()));
    }

}
