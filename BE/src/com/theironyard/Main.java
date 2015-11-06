package com.theironyard;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import spark.Session;
import spark.Spark;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    //changes

    // creates tables
    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users " +
                "(id IDENTITY, username VARCHAR, password VARCHAR, gender VARCHAR, location VARCHAR, age INT, stereotype_json VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS stereotypes " +
                "(id IDENTITY, stereotype_name VARCHAR, attribute_key VARCHAR, attribute_value VARCHAR)");
    }

    // adds user to database
    public static void insertUser(Connection conn, String username, String password, String gender, String location, int age, String stereotypeName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?, ?, ?, ?)");
        JsonSerializer serializer = new JsonSerializer();
        Stereotype stereotype = setStereotype(conn, stereotypeName);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, gender);
        stmt.setString(4, location);
        stmt.setInt(5, age);
        stmt.setString(6, serializer.serialize(stereotype));
        stmt.execute();
    }

    // pulls user from database
    public static User selectUser(Connection conn, String username) throws SQLException {
        User user = null;
        JsonParser parser = new JsonParser();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
        stmt.setString(1, username);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            user = new User();
            user.username = results.getString("username");
            user.password = results.getString("password");
            user.gender = results.getString("gender");
            user.location = results.getString("location");
            user.age = results.getInt("age");
            user.stereotype = parser.parse(results.getString("stereotype_json"), Stereotype.class);
        }
        return user;
    }

    // pulls ALL users from database
    public static ArrayList<User> selectUsers(Connection conn) throws SQLException {
        JsonParser parser = new JsonParser();
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            User user = new User();
            user.username = results.getString("username");
            user.password = results.getString("password");
            user.gender = results.getString("gender");
            user.location = results.getString("location");
            user.age = results.getInt("age");
            user.stereotype = parser.parse(results.getString("stereotype_json"), Stereotype.class);
            users.add(user);
        }
        return users;
    }

    // probably don't need this method anymore
    public static Stereotype selectStereotype(Connection conn, String stereotypeName) throws SQLException {
        Stereotype stereotype = new Stereotype();
        stereotype.typeName = stereotypeName;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM stereotypes WHERE stereotype_name = ?");
        stmt.setString(1, stereotypeName);
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            if (results.getString("attribute_key").equals("Music")) {
                stereotype.music = results.getString("attribute_value");
            }
            if (results.getString("attribute_key").equals("Food")) {
                stereotype.food = results.getString("attribute_value");
            }
            if (results.getString("attribute_key").equals("Drink")) {
                stereotype.drink = results.getString("attribute_value");
            }
            if (results.getString("attribute_key").equals("Hobby")) {
                stereotype.hobby = results.getString("attribute_value");
            }
            if (results.getString("attribute_key").equals("Style")) {
                stereotype.style = results.getString("attribute_value");
            }
            if (results.getString("attribute_key").equals("HangoutSpot")) {
                stereotype.hangout = results.getString("attribute_value");
            }
        }
        return stereotype;
    }

    // randomly generates stereotype fields based on stereotype selection
    public static Stereotype setStereotype(Connection conn, String stereotypeName) throws SQLException {
        Stereotype stereotype = new Stereotype();
        stereotype.typeName = stereotypeName;
        String[] attributes = {"Music", "Food", "Drink", "Hobby", "Style", "HangoutSpot"};
        for (String item : attributes) {
            PreparedStatement stmt = conn.prepareStatement("SELECT attribute_value FROM stereotypes " +
                    "WHERE stereotype_name = ? AND attribute_key = ? ORDER BY RAND() LIMIT 1");
            stmt.setString(1, stereotypeName);
            stmt.setString(2, item);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                if (item.equals("Music")) {
                    stereotype.music = results.getString("attribute_value");
                }
                if (item.equals("Food")) {
                    stereotype.food = results.getString("attribute_value");
                }
                if (item.equals("Drink")) {
                    stereotype.drink = results.getString("attribute_value");
                }
                if (item.equals("Hobby")) {
                    stereotype.hobby = results.getString("attribute_value");
                }
                if (item.equals("Style")) {
                    stereotype.style = results.getString("attribute_value");
                }
                if (item.equals("HangoutSpot")) {
                    stereotype.hangout = results.getString("attribute_value");
                }
            }
        }
        return stereotype;
    }

    public static void removeUser (Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE * FROM users WHERE username = ?");
        stmt.setString(1, username);
        stmt.execute();
    }

    public static void main(String[] args) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);

        if (selectUsers(conn).size() == 0) {
            createStereotypes(conn);
            insertUser(conn, "Alex", "password", "Male", "Charleston, SC", 25, "Programmer");
        }

        insertUser(conn, "Alex", "password", "Male", "Charleston, SC", 25, "Hippie");
        insertUser(conn, "Steve", "password", "Male", "Cleveland, OH", 30, "Crossfit");
        insertUser(conn, "Shelby", "password", "Female", "Atlanta, GA", 20, "Skater");
        insertUser(conn, "Lindsey", "password", "Female", "Los Angeles, CA", 40, "Hipster");

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
                    String username = request.queryParams("username");
                    String password = request.queryParams("password");
                    String gender = request.queryParams("gender");
                    String location = request.queryParams("location");
                    int age = Integer.valueOf(request.queryParams("age"));
                    String stereotypeName = request.queryParams("stereotypeName");

                    User temp = selectUser(conn, username);

                    if (temp == null) {
                        temp = new User();
                        temp.username = username;
                        temp.password = password;
                        temp.gender = gender;
                        temp.location = location;
                        temp.age = age;
                        temp.stereotype = setStereotype(conn, stereotypeName);
                        insertUser(conn, username, password, gender, location, age, stereotypeName);
                    }
                    else if (username.equals(temp.username) && password.equals(temp.password)) {
                        response.redirect("/logged-in");
                    }

                    Session session = request.session();
                    session.attribute("username", username);

                    return "";
                })
        );
        Spark.post(
                "/delete-user",
                ((request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");

                    removeUser(conn, username);
                    response.redirect("/");

                    return "";
                })
        );
        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    return "";
                })
        );
    }

    public static void createStereotype(Connection conn, String name, String typeKey, String typeValue) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO stereotypes VALUES (NULL, ?, ?, ?)");
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
        createStereotype(conn, "Crossfit", "Music", "80s Rock");
        createStereotype(conn, "Crossfit", "Food", "Steak");
        createStereotype(conn, "Crossfit", "Drink", "Bud Select");
        createStereotype(conn, "Crossfit", "Hobby", "American Ninja Warrior");
        createStereotype(conn, "Crossfit", "Style", "Nike");
        createStereotype(conn, "Crossfit", "HangoutSpot", "GNC");
        createStereotype(conn, "Crossfit", "Music", "NickelBack");
        createStereotype(conn, "Crossfit", "Food", "Energy Bars");
        createStereotype(conn, "Crossfit", "Drink", "Gatorade");
        createStereotype(conn, "Crossfit", "Hobby", "Flexing");
        createStereotype(conn, "Crossfit", "Style", "Tank Tops");
        createStereotype(conn, "Crossfit", "HangoutSpot", "Warehouses");

        createStereotype(conn, "Skater", "Music", "Blink-182");
        createStereotype(conn, "Skater", "Food", "Pavement");
        createStereotype(conn, "Skater", "Drink", "Monster Energy Drink");
        createStereotype(conn, "Skater", "Hobby", "Umm Skating");
        createStereotype(conn, "Skater", "Style", "Vans");
        createStereotype(conn, "Skater", "HangoutSpot", "Mall Parking Lot");
        createStereotype(conn, "Skater", "Music", "Metal");
        createStereotype(conn, "Skater", "Food", "Gas Station Hot Dogs");
        createStereotype(conn, "Skater", "Drink", "Milwaukee's Best");
        createStereotype(conn, "Skater", "Hobby", "Loitering");
        createStereotype(conn, "Skater", "Style", "Skinny Jeans");
        createStereotype(conn, "Skater", "HangoutSpot", "Tha Streets");
        createStereotype(conn, "Skater", "Music", "Tyler the Creator");
        createStereotype(conn, "Skater", "Food", "Skittles");
        createStereotype(conn, "Skater", "Drink", "Soda Pop");
        createStereotype(conn, "Skater", "Hobby", "Running from Security Guards");
        createStereotype(conn, "Skater", "Style", "Chain Link Wallet");
        createStereotype(conn, "Skater", "HangoutSpot", "Spencer's");

        createStereotype(conn, "Frat Star / Sorostitute", "Music", "Dave Matthews Band");
        createStereotype(conn, "Frat Star / Sorostitute", "Food", "Bar Food");
        createStereotype(conn, "Frat Star / Sorostitute", "Drink", "Bud Light");
        createStereotype(conn, "Frat Star / Sorostitute", "Hobby", "Beer Pong");
        createStereotype(conn, "Frat Star / Sorostitute", "Style", "Polo");
        createStereotype(conn, "Frat Star / Sorostitute", "HangoutSpot", "The Frat House");
        createStereotype(conn, "Frat Star / Sorostitute", "Music", "Nicki Minaj");
        createStereotype(conn, "Frat Star / Sorostitute", "Food", "Ramen Noodles");
        createStereotype(conn, "Frat Star / Sorostitute", "Drink", "Aristocrat/Evan Williams");
        createStereotype(conn, "Frat Star / Sorostitute", "Hobby", "Tailgating");
        createStereotype(conn, "Frat Star / Sorostitute", "Style", "North Face");
        createStereotype(conn, "Frat Star / Sorostitute", "HangoutSpot", "Pool");
        createStereotype(conn, "Frat Star / Sorostitute", "Music", "Widespread Panic");
        createStereotype(conn, "Frat Star / Sorostitute", "Food", "Chik fil a");
        createStereotype(conn, "Frat Star / Sorostitute", "Drink", "Fireball");
        createStereotype(conn, "Frat Star / Sorostitute", "Hobby", "Shots");
        createStereotype(conn, "Frat Star / Sorostitute", "Style", "Duck Boots");
        createStereotype(conn, "Frat Star / Sorostitute", "HangoutSpot", "The Frat House");

        createStereotype(conn, "Hipster", "Music", "You've probably never heard of them");
        createStereotype(conn, "Hipster", "Food", "Local Organic");
        createStereotype(conn, "Hipster", "Drink", "PBR");
        createStereotype(conn, "Hipster", "Hobby", "Being Ironic");
        createStereotype(conn, "Hipster", "Style", "Flannel Shirts");
        createStereotype(conn, "Hipster", "HangoutSpot", "Rec Room");
        createStereotype(conn, "Hipster", "Music", "You've probably never heard of them");
        createStereotype(conn, "Hipster", "Food", "Local Organic");
        createStereotype(conn, "Hipster", "Drink", "Coffee");
        createStereotype(conn, "Hipster", "Hobby", "Being Ironic");
        createStereotype(conn, "Hipster", "Style", "Big Glasses");
        createStereotype(conn, "Hipster", "HangoutSpot", "Coffee Shop");
        createStereotype(conn, "Hipster", "Music", "You've probably never heard of them");
        createStereotype(conn, "Hipster", "Food", "Local Organic");
        createStereotype(conn, "Hipster", "Drink", "Craft Beer");
        createStereotype(conn, "Hipster", "Hobby", "Complain");
        createStereotype(conn, "Hipster", "Style", "Thrift Store Clothes");
        createStereotype(conn, "Hipster", "HangoutSpot", "Farmer's Market");

        createStereotype(conn, "Programmer", "Music", "Star-Wars Theme Song");
        createStereotype(conn, "Programmer", "Food", "Doritos");
        createStereotype(conn, "Programmer", "Drink", "Mountain Dew Code Red");
        createStereotype(conn, "Programmer", "Hobby", "Coding");
        createStereotype(conn, "Programmer", "Style", "PJs");
        createStereotype(conn, "Programmer", "HangoutSpot", "The Couch");
        createStereotype(conn, "Programmer", "Music", "Electronica");
        createStereotype(conn, "Programmer", "Food", "Bagel Bites");
        createStereotype(conn, "Programmer", "Drink", "Monster Energy Drink");
        createStereotype(conn, "Programmer", "Hobby", "Gaming");
        createStereotype(conn, "Programmer", "Style", "Nacho Cheese Stain on T-Shirt");
        createStereotype(conn, "Programmer", "HangoutSpot", "Mom's Basement");
        createStereotype(conn, "Programmer", "Music", "8bit Soundtracks");
        createStereotype(conn, "Programmer", "Food", "Nachos");
        createStereotype(conn, "Programmer", "Drink", "Sunny D");
        createStereotype(conn, "Programmer", "Hobby", "Not Bathing");
        createStereotype(conn, "Programmer", "Style", "Captain America T-Shirt");
        createStereotype(conn, "Programmer", "HangoutSpot", "Dark Spaces");

        createStereotype(conn, "Hippie", "Music", "Phish");
        createStereotype(conn, "Hippie", "Food", "Edibles");
        createStereotype(conn, "Hippie", "Drink", "Kombucha");
        createStereotype(conn, "Hippie", "Hobby", "Drum Circles");
        createStereotype(conn, "Hippie", "Style", "Tie-Dye");
        createStereotype(conn, "Hippie", "HangoutSpot", "The Lot");
        createStereotype(conn, "Hippie", "Music", "String Cheese Incident");
        createStereotype(conn, "Hippie", "Food", "Grilled Cheese");
        createStereotype(conn, "Hippie", "Drink", "Herbal Tea");
        createStereotype(conn, "Hippie", "Hobby", "Fire-Dancing");
        createStereotype(conn, "Hippie", "Style", "Hemp");
        createStereotype(conn, "Hippie", "HangoutSpot", "Music Festivals");
        createStereotype(conn, "Hippie", "Music", "Grateful Dead");
        createStereotype(conn, "Hippie", "Food", "Trail Mix");
        createStereotype(conn, "Hippie", "Drink", "Rainwater");
        createStereotype(conn, "Hippie", "Hobby", "Taking Drugs");
        createStereotype(conn, "Hippie", "Style", "Naked");
        createStereotype(conn, "Hippie", "HangoutSpot", "Mother Earth");
    }
}
