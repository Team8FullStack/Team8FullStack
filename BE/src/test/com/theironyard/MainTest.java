package com.theironyard;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Created by alhanger on 11/5/15.
 */
public class MainTest {
    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test");
        Main.createTables(conn);
        Main.createStereotypes(conn);
        return conn;
    }

    public void endConnection(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("DROP TABLE users");
        stmt.execute("DROP TABLE stereotypes");
        conn.close();
    }

    @Test
    public void testUsers() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Alex", "password", "Male", "Charleston, SC", 25, "Programmer");
        User user = Main.selectUser(conn, "Alex");
        endConnection(conn);

        assertTrue(user != null);
    }

    @Test
    public void testStereotype() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Alex", "password", "Male", "Charleston, SC", 25, "Programmer");
        User user = Main.selectUser(conn, "Alex");
        user.stereotype = Main.setStereotype(conn, "Programmer", "Male");

        Stereotype temp = Main.selectStereotype(conn, user.stereotype.typeName);

        System.out.println(temp);
    }

    @Test
    public void testSetStereotype() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Alex", "password", "Male", "Charleston, SC", 25, "Programmer");
        Stereotype stereotype = Main.setStereotype(conn, "Hippie", "Male");
        endConnection(conn);

        System.out.println(stereotype);

    }
}