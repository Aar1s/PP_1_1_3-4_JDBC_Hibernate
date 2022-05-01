package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("James", "Clement", (byte) 39);
        userService.saveUser("Nick", "Mason", (byte) 43);
        userService.saveUser("Claire", "Tonti", (byte) 39);
        userService.removeUserById(1);
        List<User> userList = userService.getAllUsers();
        System.out.println(userList.toString());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
