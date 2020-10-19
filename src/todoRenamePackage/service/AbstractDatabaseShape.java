package todoRenamePackage.service;

import todoRenamePackage.database.MySQLJDBCUtil;

import java.sql.*;

public abstract class AbstractDatabaseShape<E> {
    @FunctionalInterface
    interface UsesStatement<R> {
        R apply(PreparedStatement statement) throws SQLException;
    }

    <R> R useStatement(String sql, UsesStatement<R> operator) {
        try (
                Connection connection = MySQLJDBCUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            //noinspection UnnecessaryLocalVariable
            R result = operator.apply(statement);

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    abstract E recordToEntity(ResultSet resultSet) throws SQLException;

    abstract E recordToEntityGlobe(ResultSet resultSet) throws SQLException;

    abstract E recordToEntityCube(ResultSet resultSet) throws SQLException;
}
