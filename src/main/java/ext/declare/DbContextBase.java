package ext.declare;

import ext.exception.OperationFailedException;
import ext.sql.SqlCursor;
import ext.sql.SqlCursorHandle;

import java.sql.*;

/**
 * 提供数据库连接和查询必须的服务
 */
public abstract class DbContextBase {
    protected final DbSettings settings;

    protected DbContextBase(DbSettings settings) throws ClassNotFoundException {
        this.settings = settings;
        // 导入外部类型
        Class.forName(settings.getDriver());
    }


    public final <T> SqlCursor<T> executeQuery(Class<T> type, String statement, Object ... args) throws OperationFailedException {
        return executeQueryArray(type, statement, args);
    }

    public final <T> SqlCursor<T> executeQueryArray(Class<T> type, String statement, Object [] args) throws OperationFailedException {
        try {
            SqlCursorHandle handle = new SqlCursorHandle(settings, statement, args);
            handle.executeQuery();

            return handle.cursor(type);
        } catch (SQLException e) {
            throw new OperationFailedException("执行查询操作出现异常", e);
        }
    }

    public final void executeNoQuery(String statement, Object... args) throws OperationFailedException {
        executeNoQueryArray(statement, args);
    }
    public final void executeNoQueryArray(String statement, Object[] args) throws OperationFailedException {
        try {
            SqlCursorHandle handle = new SqlCursorHandle(settings, statement, args);

            System.out.println("NoQuery:" + statement);

            handle.execute();
            handle.close();
        } catch (SQLException e){
            throw new OperationFailedException("执行SQL语句出现异常", e);
        }

    }

}
