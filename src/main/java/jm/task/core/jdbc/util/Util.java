package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;

import java.util.Properties;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "javaIT-2022");
                Class.forName("com.mysql.cj.jdbc.Driver");
                return connection;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Не получилось устанвить подключение!!!!!");
        }
        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/sys?autoReconnect=true&useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "javaIT-2022");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println("Не получилось устанвить подключение!!!!!");
            }
        }
        return sessionFactory;
    }
}
