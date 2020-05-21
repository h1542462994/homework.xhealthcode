package ext.sql;

import ext.Tuple;
import ext.util.ReflectTool;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class EntitySqlCreator {
    private static final String selectAny = "select * from ";
    private static final String where = " where ";
    private static final String set = " set ";
    private static final String update = "update ";
    private static final String insert = "insert into ";

    private static <T> String entityName(Class<T> type){
        return ReflectTool.getEntityName(type);
    }

    public static <T> String all(Class<T> type){
        return selectAny + entityName(type);
    }
    public static <T> String query(Class<T> type, String query){
        return selectAny + entityName(type) + where + query;
    }
    public static <T> String get(Class<T> type){
        try {
            return selectAny + entityName(type) + where + ReflectTool.getPrimaryColumnName(type) + " = ?";
        } catch (NoSuchFieldException e){
            e.printStackTrace();
            return null;
        }
    }

    private static <T> Tuple<String, ArrayList<Object>> setStatement (T element){
        try {
            Field primary = ReflectTool.getPrimaryColumn(element.getClass());
            String templateUpdate = "%s = ?";
            ArrayList<String> columnDefs = new ArrayList<>();
            ArrayList<Object> columnVals = new ArrayList<>();
            for (Field field: element.getClass().getDeclaredFields()){
                if(!field.equals(primary)){
                    columnDefs.add(String.format(templateUpdate, ReflectTool.columnOfField(field)));
                    columnVals.add(ReflectTool.getValue(element, field));
                }
            }

            String defs = String.join(",", columnDefs);

            return new Tuple<>(defs, columnVals);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Tuple<String, ArrayList<Object>> update(T element){
        try {
            Field primary = ReflectTool.getPrimaryColumn(element.getClass());
            Tuple<String, ArrayList<Object>> d = setStatement(element);
            assert d != null;
            d.second.add(ReflectTool.getValue(element, primary));
            String sql = update + entityName(element.getClass()) + set + d.first + where + ReflectTool.columnOfField(primary) + " = ?";
            return new Tuple<>(sql, d.second);
        } catch (NoSuchFieldException | IllegalAccessException e){
            return null;
        }
    }

    public static <T> Tuple<String, ArrayList<Object>> add(T element){
        Tuple<String, ArrayList<Object>> d = setStatement(element);
        assert d != null;
        String sql = insert + entityName(element.getClass()) + set + d.first;
        return new Tuple<>(sql, d.second);
    }

    public static <T> String first(Class<T> type){
        try {
            return selectAny + entityName(type) + " order by " + ReflectTool.getPrimaryColumnName(type) + " limit 1";
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static <T> String last(Class<T> type){
        try {
            return selectAny + entityName(type) + " order by " + ReflectTool.getPrimaryColumnName(type) + " desc limit 1";
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
