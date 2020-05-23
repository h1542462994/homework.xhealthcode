package ext.sql;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.ServiceContainerBase;
import ext.Tuple;
import ext.declare.DbContextBase;
import ext.util.ReflectTool;

import java.lang.reflect.Field;
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
     */
    private DbContextBase getDbContextBase() throws ServiceConstructException {
        ServiceContainerBase serviceContainerBase = ServiceContainerBase.assertGet();
        return (DbContextBase) serviceContainerBase.getService(DbContextBase.class);
     }


    /**
     * 获取数据库中当前模型的所有数据
     * @return 所有数据的可迭代对象
     * @throws OperationFailedException 服务构建异常.
     */
    public SqlCursor<T> all() throws OperationFailedException {
        try {
            return getDbContextBase().executeQuery(type, EntitySqlCreator.all(type));
        }  catch (ServiceConstructException | SQLException e) {
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
    public SqlCursor<T> query(String queryStatement, Object ... args) throws OperationFailedException {
        try {
            return getDbContextBase().executeQueryArray(type, EntitySqlCreator.query(type, queryStatement), args);
        } catch (ServiceConstructException | SQLException e) {
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
            Iterator<T> iterator = getDbContextBase().executeQuery(type, EntitySqlCreator.get(type), primary);

            if(iterator.hasNext()){
                return iterator.next();
            } else {
                return null;
            }
        } catch ( ServiceConstructException | SQLException e) {
            throw new OperationFailedException("执行select操作出现异常", e);
        }
    }

    /**
     * 根据主键的值更新相应的记录
     * @param element 模型
     * @throws OperationFailedException 服务构建异常.
     */
    public void update(T element) throws OperationFailedException {
        try {
            Tuple<String, ArrayList<Object>> d = EntitySqlCreator.update(element);
            assert d != null;
            getDbContextBase().executeNoQueryArray(d.first, d.second.toArray());
        } catch ( SQLException | ServiceConstructException e) {
            throw new OperationFailedException("执行update操作出现异常", e);
        }
    }

    /**
     * 获取数据库中第一条记录
     * @return 记录
     * @throws OperationFailedException 服务构建异常.
     */
    public T first() throws OperationFailedException {
        try {
            Iterator<T> iterator = getDbContextBase().executeQuery(type, EntitySqlCreator.first(type));
            if(iterator.hasNext()){
                return iterator.next();
            } else {
                return null;
            }
        } catch (ServiceConstructException | SQLException e) {
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
            Iterator<T> iterator = getDbContextBase().executeQuery(type , EntitySqlCreator.last(type));
            if(iterator.hasNext()){
                return iterator.next();
            } else {
                return null;
            }
        } catch (ServiceConstructException | SQLException e) {
            throw new OperationFailedException("执行select操作出现异常", e);
        }

    }

    /**
     * 添加一条记录
     * @param element 添加的记录
     * @throws OperationFailedException 服务构建异常
     */
    public void add(T element) throws OperationFailedException {
        try {
            Field primary = ReflectTool.getPrimaryRename(element.getClass());
            Tuple<String, ArrayList<Object>> d = EntitySqlCreator.add(element);
            getDbContextBase().executeNoQueryArray(d.first, d.second.toArray());
            T last = last();
            // 覆盖主键的值。
            ReflectTool.setValue(element, primary, ReflectTool.getValue(last, primary));
        } catch ( IllegalAccessException | ServiceConstructException | SQLException | NoSuchFieldException e) {
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
            Field p = ReflectTool.getPrimaryRename(type);
            Object primary = ReflectTool.getValue(element, p);
            if(get(primary) == null){
                add(element);
                T last = last();
                // 覆盖主键的值。
                ReflectTool.setValue(element, p, ReflectTool.getValue(last, p));
                return true;
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
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
            Field p = ReflectTool.getPrimaryRename(type);
            Object primary = ReflectTool.getValue(element, p);
            if (get(primary) == null){
                add(element);
                T last = last();
                // 覆盖主键的值。
                ReflectTool.setValue(element, p, ReflectTool.getValue(last, p));
                return true;
            } else {
                update(element);
                return false;
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new OperationFailedException("在进行insert或者update数据的操作时出现异常", e);
        }
    }

    @Override
    public Iterator<T> iterator() {
        try {
            return all().iterator();
        } catch (OperationFailedException e){
            return new SqlCursor<>(type, null);
        }
    }

}
