package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Postman", "Pechkin", (byte) 50);
        userService.saveUser("Dyadya", "Fedor", (byte) 15);
        userService.saveUser("Cat", "Matroskin", (byte) 20);
        userService.saveUser("Dog", "Sharik", (byte) 22);
        System.out.println(userService.getAllUsers().toString());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.close();
    }
}
