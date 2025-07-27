package com.flipfit.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*
 *@author: Zaid, Kashif
 *@ClassName:DBConnection
 *@Exceptions:
 *@version:1.0
 *@See :
 */
public class DBConnection {
    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit","root","aXath2oo!@#%");
}
}
