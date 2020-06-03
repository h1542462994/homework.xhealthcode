package ext.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 用于管理{@link java.sql.Connection}连接池的类，并提供高并发SQL查询的能力。
 *
 */
public class ConnectionPool {
    /**
     * @param statement 语句
     * @param args 参数
     * @return 返回的结果集
     */
    public ResultSet fastQuery(PreparedStatement statement, Object ... args){
        //TODO 完善高并发查询
        return null;
    }
}
