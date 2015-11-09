package com.theironyard;

import jodd.json.JsonParser;
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
                "(id IDENTITY, username VARCHAR, password VARCHAR, gender VARCHAR, location VARCHAR, age INT, stereotype_json VARCHAR, pic_url VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS stereotypes " +
                "(id IDENTITY, stereotype_name VARCHAR, gender VARCHAR, attribute_key VARCHAR, attribute_value VARCHAR)");
    }

    // adds user to database
    public static void insertUser(Connection conn, String username, String password, String gender, String location, int age, String stereotypeName, String picURL) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)");
        JsonSerializer serializer = new JsonSerializer();
        Stereotype stereotype = setStereotype(conn, stereotypeName, gender);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, gender);
        stmt.setString(4, location);
        stmt.setInt(5, age);
        stmt.setString(6, serializer.serialize(stereotype));
        stmt.setString(7, picURL);
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
    public static Stereotype setStereotype(Connection conn, String stereotypeName, String gender) throws SQLException {
        Stereotype stereotype = new Stereotype();
        stereotype.typeName = stereotypeName;
        String[] attributes = {"Music", "Food", "Drink", "Hobby", "Style", "HangoutSpot"};
        for (String item : attributes) {
            PreparedStatement stmt = conn.prepareStatement("SELECT attribute_value FROM stereotypes " +
                    "WHERE stereotype_name = ? AND gender = ? AND attribute_key = ? ORDER BY RAND() LIMIT 1");
            stmt.setString(1, stereotypeName);
            stmt.setString(2, gender);
            stmt.setString(3, item);
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
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE username = ?");
        stmt.setString(1, username);
        stmt.execute();
    }

    public static void main(String[] args) throws SQLException {

	    Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);

        if (selectUsers(conn).size() == 0) {
            createStereotypes(conn);
            insertUser(conn, "Alex", "password", "Male", "Charleston, SC", 25, "Hippie", "");
            insertUser(conn, "Steve", "password", "Male", "Cleveland, OH", 30, "Crossfit", "");
            insertUser(conn, "Jack", "password", "Male", "New York, NY", 67, "Frat Star / Sorrity Sis", "");
            insertUser(conn, "Frank", "password", "Male", "Birmingham, AL", 32, "Programmer", "");
            insertUser(conn, "Walter", "password", "Male", "Dallas, TX", 18, "Skater", "");
            insertUser(conn, "Michael", "password", "Male", "Denver, CO", 40, "Hiptser", "");

            insertUser(conn, "Shelby", "password", "Female", "Atlanta, GA", 20, "Skater", "");
            insertUser(conn, "Lindsey", "password", "Female", "Los Angeles, CA", 40, "Hipster", "");
            insertUser(conn, "Catherine", "password", "Female", "Ithaca, NY", 50, "Crossfit", "");
            insertUser(conn, "Emily", "password", "Female", "San Diego, CA", 37, "Hippie", "");
            insertUser(conn, "Anne", "password", "Female", "Miami, FL", 98, "Programmer", "");
            insertUser(conn, "Lisa", "password", "Female", "Reading, PA", 29, "Frat Star / Sorority Sis", "");
        }


        Spark.externalStaticFileLocation("FE");
        Spark.init();

        Spark.get(
                "/get-users",
                ((request, response) -> {
                    JsonSerializer serializer = new JsonSerializer();
                    String json = serializer.serialize(selectUsers(conn));
                    return json;
                })
        );
        Spark.get(
                "/get-user",
                ((request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");

                    User temp = selectUser(conn, username);

                    JsonSerializer serializer = new JsonSerializer();
                    String json = serializer.serialize(temp);

                    return json;
                })
        );
        Spark.post(
                "/login",
                ((request, response) -> {
                    String username = request.queryParams("username");
                    String password = request.queryParams("password");

                    User temp = selectUser(conn, username);

                    if (temp == null || !password.equals(temp.password)) {
                        Spark.halt(403);
                    }
                    else if (username.equals(temp.username) && password.equals(temp.password)) {
                        System.out.println("Login Success");
                    }

                    Session session = request.session();
                    session.attribute("username", username);

                    return "";
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
                    String picURL = request.queryParams("picURL");


                    insertUser(conn, username, password, gender, location, age, stereotypeName, picURL);

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

    public static void createStereotype(Connection conn, String name, String gender, String typeKey, String typeValue) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO stereotypes VALUES (NULL, ?, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, gender);
        stmt.setString(3, typeKey);
        stmt.setString(4, typeValue);
        stmt.execute();
    }

    public static void createStereotypes(Connection conn) throws SQLException {
        createStereotype(conn, "Crossfit", "Male", "Music", "EDM");
        createStereotype(conn, "Crossfit", "Male", "Food", "Creatine");
        createStereotype(conn, "Crossfit", "Male", "Drink", "Protein Shake");
        createStereotype(conn, "Crossfit", "Male", "Hobby", "Working Out");
        createStereotype(conn, "Crossfit", "Male", "Style", "Under Armour");
        createStereotype(conn, "Crossfit", "Male", "HangoutSpot", "Gym");
        createStereotype(conn, "Crossfit", "Male", "Music", "80s Rock");
        createStereotype(conn, "Crossfit", "Male", "Food", "Steak");
        createStereotype(conn, "Crossfit", "Male", "Drink", "Bud Select");
        createStereotype(conn, "Crossfit", "Male", "Hobby", "American Ninja Warrior");
        createStereotype(conn, "Crossfit", "Male", "Style", "Nike");
        createStereotype(conn, "Crossfit", "Male", "HangoutSpot", "GNC");
        createStereotype(conn, "Crossfit", "Male", "Music", "NickelBack");
        createStereotype(conn, "Crossfit", "Male", "Food", "Energy Bars");
        createStereotype(conn, "Crossfit", "Male", "Drink", "Gatorade");
        createStereotype(conn, "Crossfit", "Male", "Hobby", "Flexing");
        createStereotype(conn, "Crossfit", "Male", "Style", "Tank Tops");
        createStereotype(conn, "Crossfit", "Male", "HangoutSpot", "Warehouses");

        createStereotype(conn, "Crossfit", "Female", "Music", "EDM");
        createStereotype(conn, "Crossfit", "Female", "Food", "Creatine");
        createStereotype(conn, "Crossfit", "Female", "Drink", "Protein Shake");
        createStereotype(conn, "Crossfit", "Female", "Hobby", "Working Out");
        createStereotype(conn, "Crossfit", "Female", "Style", "Under Armour");
        createStereotype(conn, "Crossfit", "Female", "HangoutSpot", "Gym");
        createStereotype(conn, "Crossfit", "Female", "Music", "80s Rock");
        createStereotype(conn, "Crossfit", "Female", "Food", "Steak");
        createStereotype(conn, "Crossfit", "Female", "Drink", "Bud Select");
        createStereotype(conn, "Crossfit", "Female", "Hobby", "American Ninja Warrior");
        createStereotype(conn, "Crossfit", "Female", "Style", "Nike");
        createStereotype(conn, "Crossfit", "Female", "HangoutSpot", "GNC");
        createStereotype(conn, "Crossfit", "Female", "Music", "Evanescence");
        createStereotype(conn, "Crossfit", "Female", "Food", "Energy Bars");
        createStereotype(conn, "Crossfit", "Female", "Drink", "Gatorade");
        createStereotype(conn, "Crossfit", "Female", "Hobby", "Talking about Crossfit");
        createStereotype(conn, "Crossfit", "Female", "Style", "Lululemon");
        createStereotype(conn, "Crossfit", "Female", "HangoutSpot", "Warehouses");

        createStereotype(conn, "Skater", "Male", "Music", "Blink-182");
        createStereotype(conn, "Skater", "Male", "Food", "Pavement");
        createStereotype(conn, "Skater", "Male", "Drink", "Monster Energy Drink");
        createStereotype(conn, "Skater", "Male", "Hobby", "Umm Skating");
        createStereotype(conn, "Skater", "Male", "Style", "Vans");
        createStereotype(conn, "Skater", "Male", "HangoutSpot", "Mall Parking Lot");
        createStereotype(conn, "Skater", "Male", "Music", "Metal");
        createStereotype(conn, "Skater", "Male", "Food", "Gas Station Hot Dogs");
        createStereotype(conn, "Skater", "Male", "Drink", "Milwaukee's Best");
        createStereotype(conn, "Skater", "Male", "Hobby", "Loitering");
        createStereotype(conn, "Skater", "Male", "Style", "Skinny Jeans");
        createStereotype(conn, "Skater", "Male", "HangoutSpot", "Tha Streets");
        createStereotype(conn, "Skater", "Male", "Music", "Sublime");
        createStereotype(conn, "Skater", "Male", "Food", "Skittles");
        createStereotype(conn, "Skater", "Male", "Drink", "Soda Pop");
        createStereotype(conn, "Skater", "Male", "Hobby", "Running from Security Guards");
        createStereotype(conn, "Skater", "Male", "Style", "Chain Link Wallet");
        createStereotype(conn, "Skater", "Male", "HangoutSpot", "Spencer's");

        createStereotype(conn, "Skater", "Female", "Music", "Avril Lavigne");
        createStereotype(conn, "Skater", "Female", "Food", "Pavement");
        createStereotype(conn, "Skater", "Female", "Drink", "Monster Energy Drink");
        createStereotype(conn, "Skater", "Female", "Hobby", "Umm Skating");
        createStereotype(conn, "Skater", "Female", "Style", "Vans");
        createStereotype(conn, "Skater", "Female", "HangoutSpot", "Mall Parking Lot");
        createStereotype(conn, "Skater", "Female", "Music", "Metal");
        createStereotype(conn, "Skater", "Female", "Food", "Gas Station Hot Dogs");
        createStereotype(conn, "Skater", "Female", "Drink", "Milwaukee's Best");
        createStereotype(conn, "Skater", "Female", "Hobby", "Loitering");
        createStereotype(conn, "Skater", "Female", "Style", "Nose Ring");
        createStereotype(conn, "Skater", "Female", "HangoutSpot", "Tha Streets");
        createStereotype(conn, "Skater", "Female", "Music", "Sum 41");
        createStereotype(conn, "Skater", "Female", "Food", "Candy");
        createStereotype(conn, "Skater", "Female", "Drink", "Soda Pop");
        createStereotype(conn, "Skater", "Female", "Hobby", "Running from Security Guards");
        createStereotype(conn, "Skater", "Female", "Style", "Excessive Piercings");
        createStereotype(conn, "Skater", "Female", "HangoutSpot", "Spencer's");

        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Music", "Dave Matthews Band");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Food", "Bar Food");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Drink", "Bud Light");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Hobby", "Beer Pong");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Style", "Polo");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "HangoutSpot", "The Frat House");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Music", "Country");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Food", "Ramen Noodles");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Drink", "Evan Williams");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Hobby", "Tailgating");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Style", "North Face");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "HangoutSpot", "Pool");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Music", "Widespread Panic");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Food", "Chik fil a");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Drink", "Fireball");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Hobby", "Shots");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "Style", "Duck Boots");
        createStereotype(conn, "Frat Star / Sorority Sis", "Male",  "HangoutSpot", "The Frat House");

        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Music", "Dave Matthews Band");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Food", "Bar Food");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Drink", "Michelob Ultra");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Hobby", "Shopping");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Style", "Frat T's");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "HangoutSpot", "The Frat House");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Music", "Nicki Minaj");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Food", "Ramen Noodles");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Drink", "Aristocrat");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Hobby", "Tailgating");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Style", "North Face");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "HangoutSpot", "Pool");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Music", "Pop");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Food", "Chik fil a");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Drink", "Fireball");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Hobby", "Shots");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "Style", "Active Wear");
        createStereotype(conn, "Frat Star / Sorority Sis", "Female",  "HangoutSpot", "The Frat House");

        createStereotype(conn, "Hipster", "Male", "Music", "You've probably never heard of them");
        createStereotype(conn, "Hipster", "Male", "Food", "Local Organic");
        createStereotype(conn, "Hipster", "Male", "Drink", "PBR");
        createStereotype(conn, "Hipster", "Male", "Hobby", "Being Ironic");
        createStereotype(conn, "Hipster", "Male", "Style", "Flannel Shirts");
        createStereotype(conn, "Hipster", "Male", "HangoutSpot", "Rec Room");
        createStereotype(conn, "Hipster", "Male", "Music", "You've probably never heard of them");
        createStereotype(conn, "Hipster", "Male", "Food", "Local Organic");
        createStereotype(conn, "Hipster", "Male", "Drink", "Coffee");
        createStereotype(conn, "Hipster", "Male", "Hobby", "Being Ironic");
        createStereotype(conn, "Hipster", "Male", "Style", "Big Glasses");
        createStereotype(conn, "Hipster", "Male", "HangoutSpot", "Coffee Shop");
        createStereotype(conn, "Hipster", "Male", "Music", "You've probably never heard of them");
        createStereotype(conn, "Hipster", "Male", "Food", "Local Organic");
        createStereotype(conn, "Hipster", "Male", "Drink", "Craft Beer");
        createStereotype(conn, "Hipster", "Male", "Hobby", "Complain");
        createStereotype(conn, "Hipster", "Male", "Style", "Thrift Store Clothes");
        createStereotype(conn, "Hipster", "Male", "HangoutSpot", "Farmer's Market");

        createStereotype(conn, "Hipster", "Female", "Music", "You've probably never heard of them");
        createStereotype(conn, "Hipster", "Female", "Food", "Local Organic");
        createStereotype(conn, "Hipster", "Female", "Drink", "PBR");
        createStereotype(conn, "Hipster", "Female", "Hobby", "Being Ironic");
        createStereotype(conn, "Hipster", "Female", "Style", "Flannel Shirts");
        createStereotype(conn, "Hipster", "Female", "HangoutSpot", "Rec Room");
        createStereotype(conn, "Hipster", "Female", "Music", "You've probably never heard of them");
        createStereotype(conn, "Hipster", "Female", "Food", "Local Organic");
        createStereotype(conn, "Hipster", "Female", "Drink", "Coffee");
        createStereotype(conn, "Hipster", "Female", "Hobby", "Being Ironic");
        createStereotype(conn, "Hipster", "Female", "Style", "Big Glasses");
        createStereotype(conn, "Hipster", "Female", "HangoutSpot", "Coffee Shop");
        createStereotype(conn, "Hipster", "Female", "Music", "You've probably never heard of them");
        createStereotype(conn, "Hipster", "Female", "Food", "Local Organic");
        createStereotype(conn, "Hipster", "Female", "Drink", "Craft Beer");
        createStereotype(conn, "Hipster", "Female", "Hobby", "Complain");
        createStereotype(conn, "Hipster", "Female", "Style", "Thrift Store Clothes");
        createStereotype(conn, "Hipster", "Female", "HangoutSpot", "Farmer's Market");

        createStereotype(conn, "Programmer", "Male", "Music", "Star-Wars Theme Song");
        createStereotype(conn, "Programmer", "Male", "Food", "Doritos");
        createStereotype(conn, "Programmer", "Male", "Drink", "Mountain Dew Code Red");
        createStereotype(conn, "Programmer", "Male", "Hobby", "Coding");
        createStereotype(conn, "Programmer", "Male", "Style", "PJs");
        createStereotype(conn, "Programmer", "Male", "HangoutSpot", "The Couch");
        createStereotype(conn, "Programmer", "Male", "Music", "Electronica");
        createStereotype(conn, "Programmer", "Male", "Food", "Bagel Bites");
        createStereotype(conn, "Programmer", "Male", "Drink", "Monster Energy Drink");
        createStereotype(conn, "Programmer", "Male", "Hobby", "Gaming");
        createStereotype(conn, "Programmer", "Male", "Style", "Nacho Cheese Stain on T-Shirt");
        createStereotype(conn, "Programmer", "Male", "HangoutSpot", "Mom's Basement");
        createStereotype(conn, "Programmer", "Male", "Music", "8bit Soundtracks");
        createStereotype(conn, "Programmer", "Male", "Food", "Nachos");
        createStereotype(conn, "Programmer", "Male", "Drink", "Sunny D");
        createStereotype(conn, "Programmer", "Male", "Hobby", "Not Bathing");
        createStereotype(conn, "Programmer", "Male", "Style", "Captain America T-Shirt");
        createStereotype(conn, "Programmer", "Male", "HangoutSpot", "Dark Spaces");

        createStereotype(conn, "Programmer", "Female", "Music", "Star-Wars Theme Song");
        createStereotype(conn, "Programmer", "Female", "Food", "Doritos");
        createStereotype(conn, "Programmer", "Female", "Drink", "Mountain Dew Code Red");
        createStereotype(conn, "Programmer", "Female", "Hobby", "Coding");
        createStereotype(conn, "Programmer", "Female", "Style", "PJs");
        createStereotype(conn, "Programmer", "Female", "HangoutSpot", "The Couch");
        createStereotype(conn, "Programmer", "Female", "Music", "Electronica");
        createStereotype(conn, "Programmer", "Female", "Food", "Bagel Bites");
        createStereotype(conn, "Programmer", "Female", "Drink", "Monster Energy Drink");
        createStereotype(conn, "Programmer", "Female", "Hobby", "Gaming");
        createStereotype(conn, "Programmer", "Female", "Style", "Nacho Cheese Stain on T-Shirt");
        createStereotype(conn, "Programmer", "Female", "HangoutSpot", "Mom's Basement");
        createStereotype(conn, "Programmer", "Female", "Music", "8bit Soundtracks");
        createStereotype(conn, "Programmer", "Female", "Food", "Nachos");
        createStereotype(conn, "Programmer", "Female", "Drink", "Sunny D");
        createStereotype(conn, "Programmer", "Female", "Hobby", "Not Bathing");
        createStereotype(conn, "Programmer", "Female", "Style", "Captain America T-Shirt");
        createStereotype(conn, "Programmer", "Female", "HangoutSpot", "Dark Spaces");

        createStereotype(conn, "Hippie", "Male", "Music", "Phish");
        createStereotype(conn, "Hippie", "Male", "Food", "Edibles");
        createStereotype(conn, "Hippie", "Male", "Drink", "Kombucha");
        createStereotype(conn, "Hippie", "Male", "Hobby", "Drum Circles");
        createStereotype(conn, "Hippie", "Male", "Style", "Tie-Dye");
        createStereotype(conn, "Hippie", "Male", "HangoutSpot", "The Lot");
        createStereotype(conn, "Hippie", "Male", "Music", "String Cheese Incident");
        createStereotype(conn, "Hippie", "Male", "Food", "Grilled Cheese");
        createStereotype(conn, "Hippie", "Male", "Drink", "Herbal Tea");
        createStereotype(conn, "Hippie", "Male", "Hobby", "Fire-Dancing");
        createStereotype(conn, "Hippie", "Male", "Style", "Hemp");
        createStereotype(conn, "Hippie", "Male", "HangoutSpot", "Music Festivals");
        createStereotype(conn, "Hippie", "Male", "Music", "Grateful Dead");
        createStereotype(conn, "Hippie", "Male", "Food", "Trail Mix");
        createStereotype(conn, "Hippie", "Male", "Drink", "Rainwater");
        createStereotype(conn, "Hippie", "Male", "Hobby", "Taking Drugs");
        createStereotype(conn, "Hippie", "Male", "Style", "Naked");
        createStereotype(conn, "Hippie", "Male", "HangoutSpot", "Mother Earth");

        createStereotype(conn, "Hippie", "Female", "Music", "Phish");
        createStereotype(conn, "Hippie", "Female", "Food", "Edibles");
        createStereotype(conn, "Hippie", "Female", "Drink", "Kombucha");
        createStereotype(conn, "Hippie", "Female", "Hobby", "Hoola-Hooping");
        createStereotype(conn, "Hippie", "Female", "Style", "Tie-Dye");
        createStereotype(conn, "Hippie", "Female", "HangoutSpot", "The Woods");
        createStereotype(conn, "Hippie", "Female", "Music", "String Cheese Incident");
        createStereotype(conn, "Hippie", "Female", "Food", "Grilled Cheese");
        createStereotype(conn, "Hippie", "Female", "Drink", "Herbal Tea");
        createStereotype(conn, "Hippie", "Female", "Hobby", "Fire-Dancing");
        createStereotype(conn, "Hippie", "Female", "Style", "Hemp");
        createStereotype(conn, "Hippie", "Female", "HangoutSpot", "Music Festivals");
        createStereotype(conn, "Hippie", "Female", "Music", "Grateful Dead");
        createStereotype(conn, "Hippie", "Female", "Food", "Trail Mix");
        createStereotype(conn, "Hippie", "Female", "Drink", "Rainwater");
        createStereotype(conn, "Hippie", "Female", "Hobby", "Taking Drugs");
        createStereotype(conn, "Hippie", "Female", "Style", "Naked");
        createStereotype(conn, "Hippie", "Female", "HangoutSpot", "Mother Earth");
    }
}
