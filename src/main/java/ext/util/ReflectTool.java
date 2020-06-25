package ext.util;

import ext.annotation.Rename;
import ext.annotation.Entity;
import ext.annotation.Primary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 为反射提供工具类
 */
public class ReflectTool {
    public static boolean isFieldPublic(Field field){
        return Modifier.isPublic(field.getModifiers());
    }

    //TODO：无法应用boolean型，因为其奇怪的特性。
    private static Method getFieldGetter(Class<?> type, Field field) throws NoSuchMethodException {
        return type.getMethod("get" + firstToUpper(field.getName()));
    }

    //TODO：无法应用boolean型，因为其奇怪的特性。
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

    public static Field fieldOfRename(Class<?> type, String rename){
        for(Field field: type.getDeclaredFields()){
            if(renameOfField(field).equals(rename)){
                return field;
            }
        }
        return null;
    }

    public static String renameOfField(Field field){
        Rename rename = field.getAnnotation(Rename.class);
        if (rename == null){
            return field.getName();
        } else {
            String name = rename.name();
            if(name.equals("")){
                return field.getName();
            } else {
                return name;
            }
        }
    }

    public static void setRename(Object object, String rename, Object value) throws IllegalAccessException {
        setValue(object, fieldOfRename(object.getClass(), rename), value);
    }

    public static Object getRename(Object object, String rename) throws  IllegalAccessException {
        return getValue(object, fieldOfRename(object.getClass(), rename));
    }

    public static String getEntityName(Class<?> type){
        Entity entity = type.getAnnotation(Entity.class);
        if(entity != null) {
            return entity.model();
        } else {
            return type.getSimpleName();
        }
    }

    public static <T> Field getPrimaryRename(Class<T> type) throws NoSuchFieldException {
        for(Field field: type.getDeclaredFields()){
            Primary primary = field.getAnnotation(Primary.class);
            if(primary != null){
                return field;
            }
        }
        return type.getDeclaredField("id");
    }

    public static <T> String getPrimaryRenameName(Class<T> type) throws NoSuchFieldException {
        return renameOfField(getPrimaryRename(type));
    }

    public static <T> Object getPrimaryValue(T element) throws NoSuchFieldException, IllegalAccessException {
        return getValue(element, getPrimaryRename(element.getClass()));
    }

    public static <N extends Annotation> boolean hasAnnotation(Field field, Class<N> annotation){
        N ano = field.getAnnotation(annotation);
        return ano != null;
    }
}
