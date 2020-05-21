package ext.declare;

import ext.sql.SqlCursorHandle;
import ext.util.StateConverter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class DbContextBase {
    protected String driver;
    protected String url;
    protected String user;
    protected String passport;

    public final SqlCursorHandle executeQuery(String statement, Object ... args){
        Connection conn = null;
        PreparedStatement state = null;
        try {
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
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated()
    public final void executeQuery(IReadResultSet reader, String statement, Object ...args){
        Connection conn = null;
        PreparedStatement state = null;
        try {
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
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public final void executeNoQuery(String statement, String ... args){
        Connection conn = null;
        PreparedStatement state = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, passport);
            state = conn.prepareStatement(statement);
            for (int i = 0; i < args.length; ++i){
                state.setString(i, args[i]);
            }
            state.execute();
            state.close();
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
