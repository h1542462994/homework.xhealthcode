package ext.sql;

import ext.OperationFailedException;
import ext.ServiceConstructException;
import ext.ServiceContainerBase;
import ext.Tuple;
import ext.declare.DbContextBase;
import ext.util.TypeNo;

import javax.lang.model.element.Element;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 数据库集合类，用于给实体提供可迭代的容器。
 * @param <T>
 */
public class DbSet<T> implements Iterable<T>  {
    private Class<T> type;

    public DbSet (Class<T> type){
        this.type = type;
    }

    /**
     * 从当前的服务容器单例中寻找DbContextBase服务。
     * @return DbContextBase服务，提供基础的数据库查询
     * @throws ServiceConstructException 构建服务异常
     */
    private DbContextBase getDbContextBase() throws ServiceConstructException {
        ServiceContainerBase serviceContainerBase = ServiceContainerBase.assertGet();
        return (DbContextBase) serviceContainerBase.getService(DbContextBase.class);
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
            if(handle == null){
                return false;
            }
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
     * @throws OperationFailedException 服务构建异常.
     */
    public Iterable<T> all() throws OperationFailedException {
        try {
            SqlCursorHandle handle = getDbContextBase().executeQuery("select * from " + TypeNo.modelName(type));
            return new DbSetIterator(handle);
        } catch (ServiceConstructException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            throw new OperationFailedException("执行select操作出现异常", e);
        }
    }

    /**
     * 执行查找操作
     * @param queryStatement 查找字符串
     * @param args 参数
     * @return 数据的迭代器
     * @throws OperationFailedException 操作异常
     */
    public Iterable<T> query(String queryStatement, Object ... args) throws OperationFailedException {
        try {
            SqlCursorHandle handle = getDbContextBase().executeQueryArray("select * from " + TypeNo.modelName(type) + " where " + queryStatement,args);
            return new DbSetIterator(handle);
        } catch (ServiceConstructException | SQLException | ClassNotFoundException e) {
            throw new OperationFailedException("执行query操作出现异常", e);
        }
    }

    /**
     * 获取数据库中与主键对应的相应记录
     * @param primary 主键，有模型的@Primary注释指定，或者为默认的id，当前仅支持单主键搜索。
     * @param <E> 逐渐的类型
     * @return 返回的数据，若没有查到，则返回null.
     * @throws OperationFailedException 服务构建异常.
     */
    public <E> T get(E primary) throws OperationFailedException {
        try{
            Tuple<String, Field> p = TypeNo.modelPrimaryColumn(type);
            SqlCursorHandle handle = getDbContextBase().executeQuery("select * from " + TypeNo.modelName(type) + " where " + p.first + " = ? ", primary);
            Iterator<T> iterator = new DbSetIterator(handle);
            if(iterator.hasNext()){
                return iterator.next();
            } else {
                return null;
            }
        } catch (ServiceConstructException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            throw new OperationFailedException("执行select操作出现异常", e);
        }
    }

    /**
     * 根据主键的值更新相应的记录
     * @param element 模型
     * @throws OperationFailedException 服务构建异常.
     */
    public void update(T element) throws OperationFailedException {
        Tuple<String, Field> p = TypeNo.modelPrimaryColumn(type);
        try {
            Tuple<String, ArrayList<Object>> d = TypeNo.getUpdateStates(element);
            d.second.add(TypeNo.getValue(element,p.second));
            getDbContextBase().executeNoQueryArray("update " + TypeNo.modelName(type) + " set " + d.first + " where " + p.first + " = ? ", d.second.toArray());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException  | SQLException | ClassNotFoundException e) {
            throw new OperationFailedException("执行update操作出现异常", e);
        } catch (ServiceConstructException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库中第一条记录
     * @return 记录
     * @throws OperationFailedException 服务构建异常.
     */
    public T first() throws OperationFailedException {
        try {
            Tuple<String, Field> p = TypeNo.modelPrimaryColumn(type);
            SqlCursorHandle handle = getDbContextBase().executeQuery("select * from " + TypeNo.modelName(type) + " order by " + p.first + " limit 1");
            Iterator<T> iterator = new DbSetIterator(handle);
            if(iterator.hasNext()){
                return iterator.next();
            } else {
                return null;
            }
        } catch (ServiceConstructException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new OperationFailedException("执行select操作出现异常", e);
        }

    }

    /**
     * 获取数据库中最后一条记录
     * @return 最后一条元素
     */
    public T last() throws OperationFailedException {
        try {
            Tuple<String, Field> p = TypeNo.modelPrimaryColumn(type);
            SqlCursorHandle handle = getDbContextBase().executeQuery("select * from " + TypeNo.modelName(type) + " order by " + p.first + " desc limit 1");
            Iterator<T> iterator = new DbSetIterator(handle);
            if(iterator.hasNext()){
                return iterator.next();
            } else {
                return null;
            }
        } catch (ServiceConstructException | SQLException | ClassNotFoundException e) {
            throw new OperationFailedException("执行select操作出现异常", e);
        }

    }

    /**
     * 添加一条记录
     * @param element 添加的记录
     * @throws OperationFailedException 服务构建异常
     */
    public void add(T element) throws OperationFailedException {
        Tuple<String, Field> p = TypeNo.modelPrimaryColumn(type);
        try {
            Tuple<String, ArrayList<Object>> d  = TypeNo.getUpdateStates(element);
            getDbContextBase().executeNoQueryArray("insert into " + TypeNo.modelName(type) + " set " + d.first, d.second.toArray());
            T last = last();
            // 覆盖主键的值。
            TypeNo.setValue(element, p.second, TypeNo.getValue(last, p.second));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ServiceConstructException | SQLException | ClassNotFoundException e) {
            throw new OperationFailedException("执行insert出现异常", e);
        }
    }

    /**
     * 若数据库中没有主键与element匹配，则添加一条记录
     * @param element 记录
     * @return 是否成功添加一条记录
     * @throws OperationFailedException 插入异常
     */
    public boolean safeAdd(T element) throws OperationFailedException {
        try {
            Tuple<String, Field> p = TypeNo.modelPrimaryColumn(type);
            Object primary = TypeNo.getValue(element, p.second);
            if(get(primary) == null){
                add(element);
                T last = last();
                // 覆盖主键的值。
                TypeNo.setValue(element, p.second, TypeNo.getValue(last, p.second));
                return true;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new OperationFailedException("执行insert操作时出现异常", e);
        }
        return false;
    }

    /**
     * 更新或者插入一条记录
     * @param element 待更新或者插入的记录
     * @return true表示刚刚执行的插入的操作，false表示刚刚执行的是更新的操作。
     * @throws OperationFailedException 操作异常。
     */
    public boolean insertOrUpdate(T element) throws OperationFailedException {
        try {
            Tuple<String, Field> p = TypeNo.modelPrimaryColumn(type);
            Object primary = TypeNo.getValue(element, p.second);
            if (get(primary) == null){
                add(element);
                T last = last();
                // 覆盖主键的值。
                TypeNo.setValue(element, p.second, TypeNo.getValue(last, p.second));
                return true;
            } else {
                update(element);
                return false;
            }
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new OperationFailedException("在进行insert或者update数据的操作时出现异常", e);
        }
    }

    @Override
    public Iterator<T> iterator() {
        try {
            return all().iterator();
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return new DbSetIterator(null);
        }
    }

}
