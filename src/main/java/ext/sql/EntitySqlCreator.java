package ext.sql;

import ext.Tuple;
import ext.util.ReflectTool;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class EntitySqlCreator {
    private static final String selectAny = "select * from ";
    private static final String selectCount = "select count(*) from ";
    private static final String where = " where ";
    private static final String set = " set ";
    private static final String update = "update ";
    private static final String insert = "insert into ";
    private static final String delete = "delete from ";

    private static <T> String entityName(Class<T> type){
        return ReflectTool.getEntityName(type);
    }

    public static <T> String all(Class<T> type){
        return selectAny + entityName(type);
    }
    public static <T> String count(Class<T> type) {
        return selectCount + entityName(type);
    }
    public static <T> String query(Class<T> type, String query){
        return selectAny + entityName(type) + where + query;
    }
    public static <T> String queryCount(Class<T> type, String query){
        return selectCount + entityName(type) + where + query;
    }

    public static <T> String queryPage(Class<T> type, String query){
        return selectAny + entityName(type) + where + query + " limit ?,?";
    }
    public static <T> String page(Class<T> type){
        return selectAny + entityName(type) + " limit ?,?";
    }
    public static <T> String get(Class<T> type){
        try {
            return selectAny + entityName(type) + where + ReflectTool.getPrimaryRenameName(type) + " = ?";
        } catch (NoSuchFieldException e){
            e.printStackTrace();
            return null;
        }
    }

    private static <T> Tuple<String, ArrayList<Object>> setStatement (T element){
        try {
            Field primary = ReflectTool.getPrimaryRename(element.getClass());
            String templateUpdate = "%s = ?";
            ArrayList<String> renameDefs = new ArrayList<>();
            ArrayList<Object> renameVals = new ArrayList<>();
            for (Field field: element.getClass().getDeclaredFields()){
                if(!field.equals(primary)){
                    renameDefs.add(String.format(templateUpdate, ReflectTool.renameOfField(field)));
                    renameVals.add(ReflectTool.getValue(element, field));
                }
            }

            String defs = String.join(",", renameDefs);

            return new Tuple<>(defs, renameVals);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Tuple<String, ArrayList<Object>> update(T element){
        try {
            Field primary = ReflectTool.getPrimaryRename(element.getClass());
            Tuple<String, ArrayList<Object>> d = setStatement(element);
            assert d != null;
            d.second.add(ReflectTool.getValue(element, primary));
            String sql = update + entityName(element.getClass()) + set + d.first + where + ReflectTool.renameOfField(primary) + " = ?";
            return new Tuple<>(sql, d.second);
        } catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
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
            return selectAny + entityName(type) + " order by " + ReflectTool.getPrimaryRenameName(type) + " limit 1";
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String last(Class<T> type){
        try {
            return selectAny + entityName(type) + " order by " + ReflectTool.getPrimaryRenameName(type) + " desc limit 1";
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String delete(Class<T> type) {
        try {
            return delete + entityName(type) + where + ReflectTool.getPrimaryRenameName(type) + " = ?";
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }
}
