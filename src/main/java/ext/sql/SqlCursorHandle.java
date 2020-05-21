package ext.sql;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlCursorHandle implements AutoCloseable {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet set;

    public SqlCursorHandle(Connection connection, PreparedStatement statement, ResultSet set){
        this.connection = connection;
        this.statement = statement;
        this.set = set;
    }

    public Connection getConnection() {
        return connection;
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    public ResultSet getSet() {
        return set;
    }

    @Override
    public void close() throws IOException {
        try {
            set.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
