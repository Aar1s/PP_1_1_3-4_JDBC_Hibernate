package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("James", "Clement", (byte) 39);
        userDaoJDBC.saveUser("Nick", "Mason", (byte) 43);
        userDaoJDBC.saveUser("Claire", "Tonti", (byte) 39);
        userDaoJDBC.removeUserById(3);
        List<User> userList = userDaoJDBC.getAllUsers();
        System.out.println(userList.toString());
        userDaoJDBC.dropUsersTable();
    }
}
