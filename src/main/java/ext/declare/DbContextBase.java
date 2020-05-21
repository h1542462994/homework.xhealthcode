package ext.declare;

import ext.sql.SqlCursorHandle;
import ext.util.StateConverter;

import java.sql.*;

public abstract class DbContextBase {
    protected String driver;
    protected String url;
    protected String user;
    protected String passport;


    public final SqlCursorHandle executeQuery(String statement, Object ... args) throws SQLException, ClassNotFoundException {
        return executeQueryArray(statement, args);
    }

    public final SqlCursorHandle executeQueryArray(String statement, Object [] args) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement state = null;
        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, passport);
        state = conn.prepareStatement(statement);
        for (int i = 0; i < args.length; ++i){
            Object arg = args[i];
            int index = i + 1;
            state.setObject(index, arg);
        }
        ResultSet resultSet = state.executeQuery();
        return new SqlCursorHandle(conn, state, resultSet);
    }

    @Deprecated()
    public final void executeQuery(IReadResultSet reader, String statement, Object ...args) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement state = null;
        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, passport);
        state = conn.prepareStatement(statement);
        for (int i = 0; i < args.length; ++i){
            Object arg = args[i];
            int index = i + 1;
            state.setObject(index, arg);
        }

        ResultSet resultSet = state.executeQuery();
        reader.read(resultSet);
        resultSet.close();
        state.close();
        conn.close();
    }
    public final void executeNoQuery(String statement, Object... args) throws SQLException, ClassNotFoundException {
        executeNoQueryArray(statement, args);
    }
    public final void executeNoQueryArray(String statement, Object[] args) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement state = null;
        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, passport);
        state = conn.prepareStatement(statement);
        for (int i = 0; i < args.length; ++i){
            Object arg = args[i];
            int index = i + 1;
            state.setObject(index, arg);
        }
        state.execute();
        state.close();
        conn.close();
    }
}
