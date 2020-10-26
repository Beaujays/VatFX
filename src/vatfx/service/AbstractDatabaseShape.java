package vatfx.service;

import vatfx.database.MySQLJDBCUtil;

import java.sql.*;

// <E> means a class
public abstract class AbstractDatabaseShape<E> {
    @FunctionalInterface
    // <R> means this interface
    interface UsesStatement<R> {
        R apply(PreparedStatement statement) throws SQLException;
    }

    <R> R useStatement(String sql, UsesStatement<R> operator) {
        try (
                // Makes connection with database
                Connection connection = MySQLJDBCUtil.getConnection();
                // Prepare statement
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            // Execute statement
            R result = operator.apply(statement);

            // Returns executed statement result
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Receives result set from database and puts it in an object/class
    abstract E recordToEntity(ResultSet resultSet) throws SQLException;
}
