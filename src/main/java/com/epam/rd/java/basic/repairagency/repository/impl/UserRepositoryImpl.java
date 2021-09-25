package com.epam.rd.java.basic.repairagency.repository.impl;

import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.entity.sorting.UserSortingParameter;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.factory.anotation.Repository;
import com.epam.rd.java.basic.repairagency.repository.UserRepository;
import com.epam.rd.java.basic.repairagency.util.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository(UserRepository.class)
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    private UserRepositoryImpl() {
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO user (first_name, last_name, email, phone_number, password)" +
                " VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM user";
    }

    private String getSelectWithRoleNameQuery() {
        return "SELECT u.*, r.name as role_name FROM user u, role r ";
    }

    private String getSelectCountQuery() {
        return "SELECT COUNT(*) FROM user";
    }

    private String getUpdateUserRoleQuery() {
        return "UPDATE user SET role_id = ? WHERE id =?;";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE user SET first_name = ?, last_name = ?, email = ?, phone_number = ?, password = ? WHERE id =?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM user WHERE id = ?;";
    }

    @Override
    protected List<User> parseEntitiesFromResultSet(ResultSet rs) throws SQLException {
        List<User> result = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setPassword(rs.getString("password"));
            user.setCreatedAt(rs.getTimestamp("create_time").toLocalDateTime());
            user.setRole(UserRole.getById(rs.getLong("role_id")));
            result.add(user);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User user) throws SQLException {
        int paramIndex = 0;
        statement.setString(++paramIndex, user.getFirstName());
        statement.setString(++paramIndex, user.getLastName());
        statement.setString(++paramIndex, user.getEmail());
        statement.setString(++paramIndex, user.getPhoneNumber());
        statement.setString(++paramIndex, user.getPassword());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User user) throws SQLException {
        int paramIndex = 0;
        statement.setString(++paramIndex, user.getFirstName());
        statement.setString(++paramIndex, user.getLastName());
        statement.setString(++paramIndex, user.getEmail());
        statement.setString(++paramIndex, user.getPhoneNumber());
        statement.setString(++paramIndex, user.getPassword());
        statement.setLong(++paramIndex, user.getId());
    }

    private void prepareStatementForUpdateUserRole(PreparedStatement statement, long userId, UserRole role) throws SQLException {
        int paramIndex = 0;
        statement.setLong(++paramIndex, role.getId());
        statement.setLong(++paramIndex, userId);
    }

    @Override
    protected void insertDefaultDependencies(Connection connection, User user) throws SQLException {
        user.setRole(UserRole.CUSTOMER);
    }

    @Override
    public void setRoleToUser(Connection connection, User user, UserRole role) throws SQLException {
        setRoleToUser(connection, user.getId(), role);
    }

    @Override
    public void setRoleToUser(Connection connection, long userId, UserRole role) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getUpdateUserRoleQuery());
            prepareStatementForUpdateUserRole(statement, userId, role);
            statement.executeUpdate();
        } finally {
            DBUtil.close(statement);
        }
    }

    @Override
    public User findByEmailAndPassword(Connection connection, String email, String password) throws NotFoundException, SQLException {
        String sql = getSelectQuery();
        sql += " WHERE email = ? AND password = ?";
        try {
            return findByQueryWithParameters(connection, sql, email, password);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with email '" + email + "' and password wasn't found");
        }
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE role_id != " + UserRole.ADMIN.getId() + " ORDER BY create_time DESC";
        return findAllByQueryWithoutParameters(connection, sql);
    }

    @Override
    public List<User> findAllByRole(Connection connection, UserRole role) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE role_id = " + role.getId() + " ORDER BY create_time DESC";
        return findAllByQueryWithoutParameters(connection, sql);
    }

    @Override
    public List<User> findAllExcludeRoles(Connection connection, UserRole[] roles, int offset, int amount,
                                          UserSortingParameter sortingParam, SortingType sortingType
    ) throws SQLException, NotFoundException {
        String query = getSelectWithRoleNameQuery();
        query += " WHERE u.role_id = r.id";
        if (roles.length > 0) {
            String rolesAsString = getRolesAsString(roles);
            query += " AND role_id NOT IN " + rolesAsString;
        }
        query += " ORDER BY " + sortingParam.getColumnName();
        query += " " + sortingType.getType();
        query += " LIMIT " + offset + ", " + amount;
        return findAllByQueryWithoutParameters(connection, query);
    }

    private String getRolesAsString(UserRole[] roles) {
        return Arrays.stream(roles).map(role -> String.valueOf(role.getId())).collect(Collectors.joining(", ", "(", ")"));
    }

    @Override
    public int findCountOfUsersExcludeRoles(Connection connection, UserRole... roles) throws SQLException {
        String sql = getSelectCountQuery();
        if (roles.length > 0) {
            String rolesAsString = getRolesAsString(roles);
            sql += " WHERE role_id NOT IN " + rolesAsString;
        }
        return findIntValueByQueryWithoutParameters(connection, sql);
    }

    @Override
    public List<User> findAllByRole(Connection connection, UserRole role, int offset, int amount,
                                    UserSortingParameter sortingParam, SortingType sortingType) throws SQLException, NotFoundException {
        String query = getSelectQuery();
        query += " WHERE role_id = " + role.getId();
        query += " ORDER BY " + sortingParam.getColumnName();
        query += " " + sortingType.getType();
        query += " LIMIT " + offset + ", " + amount;
        return findAllByQueryWithoutParameters(connection, query);
    }

    @Override
    public int findCountOfUsersByRole(Connection connection, UserRole role) throws SQLException {
        String sql = getSelectCountQuery();
        sql += " WHERE role_id = ?";
        return findIntValueByQueryWithParameters(connection, sql, role.getId());
    }
}
