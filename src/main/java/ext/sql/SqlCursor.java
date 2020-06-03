package ext.sql;

import ext.util.ReflectTool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Sql元素指针
 * @param <T> 元素类型
 */
public class SqlCursor<T> implements Iterator<T>, Iterable<T>, AutoCloseable {
    private final Class<T> type;

    public SqlCursorHandle getHandle() {
        return handle;
    }

    private final SqlCursorHandle handle;

    public SqlCursor(Class<T> type, SqlCursorHandle handle){
        this.type = type;
        this.handle = handle;
    }

    @Override
    public boolean hasNext() {
        if(handle == null){
            return false;
        }
        try {
            boolean next = handle.getSet().next();
            if(!next){ // 如果发现没有下一条记录则释放资源
                handle.close();
            }
            return next;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public T next() {
        try {
            return fill(handle.getSet());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        handle.close();
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    private T fill(ResultSet set) throws SQLException {
        try {
            T instance = type.getConstructor().newInstance();
            for(Field field: type.getDeclaredFields()){
                ReflectTool.setValue(instance, field, set.getObject(ReflectTool.renameOfField(field), field.getType()));
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public T unique(){
        if(hasNext()){
            T next = next();
            this.close();
            return next;
        } else {
            return null;
        }
    }
}
