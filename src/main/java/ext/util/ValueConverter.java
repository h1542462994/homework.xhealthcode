package ext.util;

public class ValueConverter {
    public static Object string2Type(Class<?> type, String value){

        if (type.equals(String.class)){
            return value;
        } else if(type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if(type.equals(long.class) || type.equals(Long.class)) {
            return Long.valueOf(value);
        } else if(type.equals(float.class) || type.equals(Float.class)) {
            return Float.valueOf(value);
        } else if(type.equals(double.class) || type.equals(Double.class)) {
            return Double.valueOf(value);
        } else {
            return value;
        }
    }
}
