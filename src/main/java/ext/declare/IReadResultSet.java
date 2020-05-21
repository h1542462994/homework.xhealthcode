package ext.declare;

import java.sql.ResultSet;
import java.sql.SQLException;

@Deprecated
public interface IReadResultSet {
    void read(ResultSet set) throws SQLException;
}
