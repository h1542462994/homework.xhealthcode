package ext.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Deprecated
public class StateConverter {
    public static void setValue(PreparedStatement statement, int index, Object value) throws SQLException {
        statement.setObject(index, value);
//        if (value instanceof String) {
//            statement.setString(index, (String)value);
//        } else if(value instanceof Boolean) {
//            statement.setBoolean(index, (Boolean)value);
//        } else if(value instanceof Short) {
//            statement.setShort(index, (Short)value);
//        } else if(value instanceof Integer) {
//            statement.setInt(index, (Integer)value);
//        } else if(value instanceof Long) {
//            statement.setLong(index, (Long)value);
//        } else if(value instanceof Float) {
//            statement.setFloat(index, (Float)value);
//        } else if (value instanceof Double) {
//            statement.setDouble(index, (Double)value);
//        }
    }
}
