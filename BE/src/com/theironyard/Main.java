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
                "(id IDENTITY, stereotype_name VARCHAR, attribute_key VARCHAR, attribute_value VARCHAR)");
    }

    // adds user to database
    public static void insertUser(Connection conn, String username, String password, boolean gender, String location, int age, String stereotypeName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setBoolean(3, gender);
        stmt.setString(4, location);
        stmt.setInt(5, age);
        stmt.setString(6, stereotypeName);
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
            user.stereotype = selectStereotype(conn, results.getString("stereotype_name"));
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
            user.stereotype = selectStereotype(conn, results.getString("stereotype_name"));
            users.add(user);
        }
        return users;
    }

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
            if (results.getString("attribute_key").equals("Hangout")) {
                stereotype.hangout = results.getString("attribute_value");
            }
        }
        return stereotype;
    }

    // can I use the SQL query from this method in the method above?
    public static void setStereotype(Connection conn, String stereotypeName) throws SQLException {
        Stereotype stereotype = new Stereotype();
        PreparedStatement stmt = conn.prepareStatement("SELECT attribute_value FROM stereotypes " +
                "WHERE stereotype_name = ? AND stereotype_key = ? ORDER BY RAND() LIMIT 1");
        stmt.setString(1, stereotypeName);
        String[] attributes = {"Music", "Food", "Drink", "Hobby", "Style", "Hangout"};
        for (String item : attributes) {
            stmt.setString(2, item);
            ResultSet results = stmt.executeQuery();

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
            if (results.getString("attribute_key").equals("Hangout")) {
                stereotype.hangout = results.getString("attribute_value");
            }
        }
    }

    public static void musicGenerator(Connection conn, String stereotypeName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT attribute_value FROM stereotypes WHERE stereotype_name = ? AND attribute_type = 'Music' ORDER BY RAND() LIMIT 1");
        stmt.setString(1, stereotypeName);
        stmt.execute();
    }

    public static void foodGenerator (Connection conn, String stereotypeName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT attribute_value FROM stereotypes WHERE stereotype_name = ? AND attribute_type = 'Food' ORDER BY RAND() LIMIT 1");
        stmt.setString(1, stereotypeName);
        stmt.execute();
    }

    public static void drinkGenerator (Connection conn, String stereotypeName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT attribute_value FROM stereotypes WHERE stereotype_name = ? AND attribute_type = 'Drink' ORDER BY RAND() LIMIT 1");
        stmt.setString(1, stereotypeName);
        stmt.execute();
    }

    public static void hobbyGenerator (Connection conn, String stereotypeName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT attribute_value FROM stereotypes WHERE stereotype_name = ? AND attribute_type = 'Hobby' ORDER BY RAND() LIMIT 1");
        stmt.setString(1, stereotypeName);
        stmt.execute();
    }

    public static void styleGenerator (Connection conn, String stereotypeName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT attribute_value FROM stereotypes WHERE stereotype_name = ? AND attribute_type = 'Style' ORDER BY RAND() LIMIT 1");
        stmt.setString(1, stereotypeName);
        stmt.execute();
    }

    public static void hangoutGenerator (Connection conn, String stereotypeName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT attribute_value FROM stereotypes WHERE stereotype_name = ? AND attribute_type = 'HangoutSpot' ORDER BY RAND() LIMIT 1");
        stmt.setString(1, stereotypeName);
        stmt.execute();
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
                    Session session = request.session();
                    String username = session.attribute("username");
                    String password = request.queryParams("password");
                    // request gender selection here
                    String location = request.queryParams("location");
                    // request age here
                    String stereotypeName = request.queryParams("stereotypeName");

                    User temp = selectUser(conn, username);



                    if (temp == null) {
                        temp = new User();
                        temp.username = username;

                    }
                })
        );
    }
}
