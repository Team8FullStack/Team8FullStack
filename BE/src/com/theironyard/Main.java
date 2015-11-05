package com.theironyard;

import jodd.json.JsonSerializer;
import spark.Session;
import spark.Spark;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    // creates tables
    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users " +
                "(id IDENTITY, username VARCHAR, password VARCHAR, gender BOOLEAN, location VARCHAR, age INT, stereotype_name VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS stereotypes " +
                "(id IDENTITY, stereo_name VARCHAR, like_type VARCHAR, like_value VARCHAR)");
    }

    // adds user to database
    public static void insertUser(Connection conn, String username, String password, boolean gender, String location, int age, String stereotypeName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setBoolean(3, gender);
        stmt.setString(4, location);
        stmt.setInt(5, age);
        stmt.setObject(6, stereotypeName);
        stmt.execute();
    }

    // pulls user from database
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

    // pulls ALL users from database
    public static ArrayList<User> selectUsers(Connection conn) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            User user = new User();
            user.username = results.getString("username");
            user.password = results.getString("password");
            user.gender = results.getBoolean("gender");
            user.location = results.getString("location");
            user.age = results.getInt("age");
            user.stereotype = selectStereotype(conn, results.getInt("stereotype_id"));
            users.add(user);
        }
        return users;
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

    public static void createStereotype(Connection conn, String name, String typeKey, String typeValue) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO stereotypes VALUES (NULL, ?, ?, ?");
        stmt.setString(1, name);
        stmt.setString(2, typeKey);
        stmt.setString(3, typeValue);
        stmt.execute();
    }

    public static void createStereotypes(Connection conn) throws SQLException {
        createStereotype(conn, "Crossfit", "Music", "EDM");
        createStereotype(conn, "Crossfit", "Food", "Creatine");
        createStereotype(conn, "Crossfit", "Drink", "Protein Shake");
        createStereotype(conn, "Crossfit", "Hobby", "Working Out");
        createStereotype(conn, "Crossfit", "Style", "Under Armour");
        createStereotype(conn, "Crossfit", "HangoutSpot", "Gym");
        createStereotype(conn, "Skater", "Music", "Blink-182");
        createStereotype(conn, "Skater", "Food", "Pavement");
        createStereotype(conn, "Skater", "Drink", "Monster Energy Drink");
        createStereotype(conn, "Skater", "Hobby", "Umm Skating");
        createStereotype(conn, "Skater", "Style", "Vans");
        createStereotype(conn, "Skater", "HangoutSpot", "Mall Parking Lot");
        createStereotype(conn, "Frat Star / Sorostitute", "Music", "Dave Matthews Band");
        createStereotype(conn, "Frat Star / Sorostitute", "Food", "Bar Food");
        createStereotype(conn, "Frat Star / Sorostitute", "Drink", "Bud Light");
        createStereotype(conn, "Frat Star / Sorostitute", "Hobby", "Beer Pong");
        createStereotype(conn, "Frat Star / Sorostitute", "Style", "Polo");
        createStereotype(conn, "Frat Star / Sorostitute", "HangoutSpot", "The Frat House");
        createStereotype(conn, "Hipster", "Music", "You've probably never heard of them");
        createStereotype(conn, "Hipster", "Food", "Local Organic");
        createStereotype(conn, "Hipster", "Drink", "PBR");
        createStereotype(conn, "Hipster", "Hobby", "Being Ironic");
        createStereotype(conn, "Hipster", "Style", "Flannel Shirts");
        createStereotype(conn, "Hipster", "HangoutSpot", "Rec Room");
        createStereotype(conn, "Programmer", "Music", "Star-Wars Theme Song");
        createStereotype(conn, "Programmer", "Food", "Doritos");
        createStereotype(conn, "Programmer", "Drink", "Mountain Dew Code Red");
        createStereotype(conn, "Programmer", "Hobby", "Coding");
        createStereotype(conn, "Programmer", "Style", "PJs");
        createStereotype(conn, "Programmer", "HangoutSpot", "The Couch");
        createStereotype(conn, "Hippie", "Music", "Phish");
        createStereotype(conn, "Hippie", "Food", "Drugs");
        createStereotype(conn, "Hippie", "Drink", "Kombucha");
        createStereotype(conn, "Hippie", "Hobby", "Drum Circles");
        createStereotype(conn, "Hippie", "Style", "Tie-Dye");
        createStereotype(conn, "Hippie", "HangoutSpot", "Open Fields");
    }

    public static void main(String[] args) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);

        if (selectUsers(conn).size() == 0) {
            createStereotypes(conn);
            insertUser(conn, "Alex", "password", true, "Charleston, SC", 25, "Programmer");
        }

        Spark.externalStaticFileLocation("FE");
        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    JsonSerializer serializer = new JsonSerializer();
                    String json = serializer.serialize(selectUsers(conn));
                    return json;
                })
        );
        Spark.post(
                "/create-user",
                ((request, response) -> {

                })
        );
    }
}
