package ext.declare;

import ext.sql.SqlCursor;
import ext.sql.SqlCursorHandle;

import java.sql.*;

public abstract class DbContextBase {
    protected DbSettings settings;

    protected DbContextBase(DbSettings settings) throws ClassNotFoundException {
        this.settings = settings;
        // 导入外部类型
        Class.forName(settings.getDriver());
    }

    public final <T> SqlCursor<T> executeQuery(Class<T> type, String statement, Object ... args) throws SQLException {
        return executeQueryArray(type, statement, args);
    }

    public final <T> SqlCursor<T> executeQueryArray(Class<T> type, String statement, Object [] args) throws SQLException {
        SqlCursorHandle handle = new SqlCursorHandle(settings, statement, args);
        handle.executeQuery();

        return handle.cursor(type);
    }

    @Deprecated()
    public final void executeQuery(IReadResultSet reader, String statement, Object ...args) throws SQLException {
        SqlCursorHandle handle = new SqlCursorHandle(settings, statement, args);
        handle.executeQuery();

        reader.read(handle.getSet());
        handle.close();
    }
    public final void executeNoQuery(String statement, Object... args) throws SQLException {
        executeNoQueryArray(statement, args);
    }
    public final void executeNoQueryArray(String statement, Object[] args) throws SQLException {
        SqlCursorHandle handle = new SqlCursorHandle(settings, statement, args);

        System.out.println("NoQuery:" + statement);

        handle.execute();
        handle.close();
    }

}
