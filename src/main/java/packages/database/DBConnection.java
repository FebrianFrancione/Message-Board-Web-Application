package packages.database;

import java.sql.*;

public class DBConnection {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";   //DO NOT CHANGE THIS

    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/messageboard?user=root"; //CHANGE THIS. "messageboard" is the name of database so change it according to your database. "?user=root" is specific to my machine you may or may not have this in your JDBC url
    // Database credentials
    static final String DB_USER = "root";   //CHANGE THIS
    static final String DB_PASSWORD = "1234"; //CHANGE THIS, could be empty if you haven't set up a password for your DB

    static Connection conn = null;

    public static Connection getConnection() {

        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            return conn;
        } catch (SQLException e){
            throw new RuntimeException("Error connecting to database",e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Error Class Not Found",e);
        }
    }

    public static void closeConnection() throws SQLException{
        //Close connection
        if(conn!=null) conn.close();
    }
}
