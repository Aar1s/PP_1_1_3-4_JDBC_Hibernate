package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class UserDaoJDBCImpl implements UserDao {
    static int userCount = 0;
    public UserDaoJDBCImpl() throws SQLException, ClassNotFoundException {

    }

    public void createUsersTable() throws SQLException, ClassNotFoundException {
        Connection connection = Util.getMySQLConnection();
        try (connection) {
            Statement statement = connection.createStatement();
            String createTableSQLQuery = "create table Users(ID int, NAME varchar(40), LAST_NAME varchar(40), AGE int)";
            statement.execute(createTableSQLQuery);
        } catch (SQLSyntaxErrorException ignored) {
        }


    }

    public void dropUsersTable() throws SQLException, ClassNotFoundException {
        Connection connection = Util.getMySQLConnection();
        try (connection) {
            Statement statement = connection.createStatement();
            String dropTableSQLQuery = "drop table Users";
            statement.execute(dropTableSQLQuery);
        } catch (SQLSyntaxErrorException ignored) {
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException, ClassNotFoundException {
        Connection connection = Util.getMySQLConnection();
        Statement statement = connection.createStatement();
        userCount++;
        String saveUserQuery = "Insert into Users (ID, NAME, LAST_NAME, AGE)" +
                " values (" + userCount + ", '" + name + "', '" +
                lastName + "', " + age + ")";
        statement.execute(saveUserQuery);
        System.out.println("User с именем " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) throws SQLException, ClassNotFoundException {
        Connection connection = Util.getMySQLConnection();
        try (connection) {
            Statement statement = connection.createStatement();
            String removeUserByIDQuery = "DELETE FROM Customers WHERE ID=" + id;
            statement.execute(removeUserByIDQuery);
        } catch (SQLSyntaxErrorException ignored) {
        }


    }

    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        List<User> userList = new ArrayList<>();
        User user = new User();
        Connection connection = Util.getMySQLConnection();
        Statement statement = connection.createStatement();
        String sql = "Select ID, NAME, LAST_NAME, AGE from Users";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            user.setId((long) rs.getInt("ID"));
            user.setName(rs.getString(2));
            user.setLastName(rs.getString(3));
            user.setAge((byte) rs.getInt(4));
            userList.add(user);
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException, ClassNotFoundException {
        Connection connection = Util.getMySQLConnection();
        Statement statement = connection.createStatement();
        String truncateQuery = "truncate table users";
        statement.execute(truncateQuery);
    }
}
