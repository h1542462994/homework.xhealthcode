package ext.sql;

import ext.ServiceConstructException;
import ext.ServiceContainer;
import ext.Tuple;
import ext.util.TypeNo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class DbSet<T>  {
    private Class<T> type;

    public DbSet (Class<T> type){
        this.type = type;
    }

    /**
     * 基于SqlCursorHandle的迭代器对象。
     */
    private class DbSetIterator implements Iterable<T>, Iterator<T>, AutoCloseable {
        private SqlCursorHandle handle;

        public DbSetIterator(SqlCursorHandle handle){
            this.handle = handle;
        }

        @Override
        public boolean hasNext() {
            try {
                boolean next = handle.getSet().next();
                if(!next){ // 如果发现没有下一条记录则释放资源
                    handle.close();
                }
                return next;
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public T next() {
            try {
                return (T)fill(handle.getSet());
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void close() throws Exception {
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
                    TypeNo.setValue(instance, field, set.getObject(TypeNo.fieldColumnName(field), field.getType()));
                }
                return instance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

//    public Iterable<T> all() throws ServiceConstructException {
//        ArrayList<T> list = new ArrayList<>();
//        // 调用dbContext服务。
//        ServiceContainer.get().dbContext().executeQuery((rs)->{
//            while(rs.next()){
//                T obj = fill(rs);
//                list.add(obj);
//            }
//        },"select * from " + TypeNo.modelName(type));
//        return list;
//    }

    /**
     * 获取数据库中当前模型的所有数据
     * @return 所有数据的可迭代对象
     * @throws ServiceConstructException 服务构建异常.
     */
    public Iterable<T> all() throws ServiceConstructException {
        SqlCursorHandle handle = ServiceContainer.get().dbContext().executeQuery("select * from " + TypeNo.modelName(type));
        return new DbSetIterator(handle);
    }

    /**
     * 获取数据库中与主键对应的相应记录
     * @param primary 主键，有模型的@Primary注释指定，或者为默认的id，当前仅支持单主键搜索。
     * @param <E> 逐渐的类型
     * @return 返回的数据，若没有查到，则返回null.
     * @throws ServiceConstructException 服务构建异常.
     */
    public <E> T get(E primary) throws ServiceConstructException {
        Tuple<String, Class<?>> p = TypeNo.modelPrimaryColumn(type);
        SqlCursorHandle handle = ServiceContainer.get().dbContext().executeQuery("select * from " + TypeNo.modelName(type) + " where " + p.first + " = ? ", primary);
        Iterator<T> iterator = new DbSetIterator(handle);
        if(iterator.hasNext()){
            return iterator.next();
        } else {
            return null;
        }
    }



}
