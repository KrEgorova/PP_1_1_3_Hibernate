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
    private static SessionFactory sessionFactory;
    private static final String url = "jdbc:mysql://localhost:3306/sys";
    private static final String userName = "root";
    private static final String password = "javaIT-2022";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String dialect = "org.hibernate.dialect.MySQLDialect";
    private static final String currentSessionContextClass = "thread";
    private static final String hbm2ddl = "update";
    private static final String warning = "Не получилось устанвить подключение!!!!!";

    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, userName, password);
                Class.forName(driver);
                return connection;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(warning);
        }
        return connection;
    }


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, driver);
                settings.put(Environment.URL, url);
                settings.put(Environment.USER, userName);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, dialect);
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, currentSessionContextClass);
                settings.put(Environment.HBM2DDL_AUTO, hbm2ddl);

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println(warning);
            }
        }
        return sessionFactory;
    }


    public static void close() {
        try {
            sessionFactory.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
