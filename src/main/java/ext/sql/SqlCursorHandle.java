package ext.sql;

import ext.declare.DbSettings;

import java.sql.*;

public class SqlCursorHandle implements AutoCloseable {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet set;

    public SqlCursorHandle(DbSettings settings, String statement, Object[] args) throws SQLException {
        Connection conn;
        PreparedStatement state;
        conn = DriverManager.getConnection(settings.getUrl(), settings.getUser(), settings.getPassport());
        state = conn.prepareStatement(statement);
        for (int i = 0; i < args.length; ++i){
            Object arg = args[i];
            int index = i + 1;
            state.setObject(index, arg);
        }

        System.out.println("Query:" + state);

        this.connection = conn;
        this.statement = state;
        this.set = null;
    }

    /**
     * 获取Sql的元素指针
     * @param type 元素的Class
     * @param <T> 元素的类型
     * @return 元素指针
     */
    public <T> SqlCursor<T> cursor(Class<T> type){
        return new SqlCursor<>(type, this);
    }

    public void execute() throws SQLException {
        this.statement.execute();
    }

    public void executeQuery() throws SQLException {
        this.set = this.statement.executeQuery();
    }

    public ResultSet getSet() {
        return set;
    }

    @Override
    public void close() {
        try {
            if(set != null){
                set.close();
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
