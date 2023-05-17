package com.example.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "STUDENT";
//    private static final String USERNAME = "system";
//    private static final String PASSWORD = "1234";

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() //conectarea cu baza de date
    {
        try
        {
            this.connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connected to Oracle database");
            System.out.println("");
        }
        catch (SQLException e)
        {
            System.out.println("You have this error:");
            e.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

    public static DatabaseConnection getInstance() throws SQLException
    {
        if (instance == null)
        {
            instance = new DatabaseConnection();
        }
        else if (instance.getConnection().isClosed())
        {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
