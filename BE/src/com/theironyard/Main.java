package com.theironyard;

import java.sql.*;

public class Main {

    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users " +
                "(id IDENTITY, username VARCHAR, password VARCHAR, gender BOOLEAN, location VARCHAR, age INT, stereotype_id INT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS stereotypes " +
                "(id IDENTITY, user_id INT, type VARCHAR, food VARCHAR, drink VARCHAR, music VARCHAR, hangout VARCHAR, hobby VARCHAR, style VARCHAR)");
    }

    public static void insertUser(Connection conn, String username, String password, boolean gender, String location, int age, int stereotypeId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setBoolean(3, gender);
        stmt.setString(4, location);
        stmt.setInt(5, age);
        stmt.setObject(6, stereotypeId);
        stmt.execute();
    }

    public static User selectUser(Connection conn, String username) throws SQLException {
        User user = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
        stmt.setString(1, username);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            user = new User();
            user.username = results.getString("username");
            user.password = results.getString("password");
            user.gender = results.getBoolean("gender");
            user.location = results.getString("location");
            user.age = results.getInt("age");
            user.stereotype = selectStereotype(conn, results.getInt("stereotype_id"));
        }
        return user;
    }

    public static Stereotype selectStereotype(Connection conn, int stereotypeId) throws SQLException {
        Stereotype stereotype = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM stereotypes WHERE id = ?");
        stmt.setInt(1, stereotypeId);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            stereotype = new Stereotype();
            stereotype.id = results.getInt("id");
            stereotype.type = results.getString("type");
            stereotype.food = results.getString("food");
            stereotype.drink = results.getString("drink");
            stereotype.music = results.getString("music");
            stereotype.hangout = results.getString("hangout");
            stereotype.hobby = results.getString("hobby");
            stereotype.style = results.getString("style");
        }
        return stereotype;
    }

    public static void main(String[] args) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);
    }
}
