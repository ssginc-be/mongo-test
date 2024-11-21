package com.qriosity.mongotest.mongo.msa;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Queue-ri
 */
public class DistributedDatabaseExample {
    // MySQL JDBC URL
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/naver";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "Abcd123!";

    // MongoDB Connection URI
    //private static final String MONGO_URI = "mongodb://localhost:27017";

    public static void main(String[] args) {
        // 1. mysql 연결
        // 2. 회원가입 후 자동증가한 id를 읽어옴
        // 3. 2번에서 얻은 id를 이용해서 mongodb에 주문정보 넣음
        //    3-1. mongodb 연결
        //    3-2. document 만들어 전송
        // 4. 회원정보는 mysql, 회원주문정보는 mongodb에서 검색하여 분산

        // 0. 로그 숨기기
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.WARNING);

        try {
            // Step 1: Connect to MySQL
            Connection mysqlConnection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            System.out.println("Connected to MySQL.");

            // Step 2: Insert data into MySQL
            String insertUserSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
            PreparedStatement pstmt = mysqlConnection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "John Doe");
            pstmt.setString(2, "john.doe@example.com");
            pstmt.executeUpdate();

            // Get the generated user ID
            ResultSet rs = pstmt.getGeneratedKeys();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt(1);
            }
            System.out.println("Inserted user with ID: " + userId);

            // Step 3: Connect to MongoDB
            com.mongodb.MongoClient mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("naver2");
            MongoCollection<Document> ordersCollection = mongoDatabase.getCollection("orders");
            System.out.println("Connected to MongoDB.");

            // Step 4: Insert data into MongoDB
            Document order = new Document("user_id", userId)
                    .append("order_date", "2024-11-20")
                    .append("items", Arrays.asList( // new ArrayList 해도 됨 (asList는 읽기전용 목적으로 사용)
                            new Document("product", "item1").append("quantity", 2),
                            new Document("product", "item2").append("quantity", 1)
                    ));
            ordersCollection.insertOne(order);
            System.out.println("Inserted order for user ID: " + userId);

            // Step 5: Retrieve data from MySQL
            String selectUserSQL = "SELECT * FROM users WHERE id = ?";
            PreparedStatement selectPstmt = mysqlConnection.prepareStatement(selectUserSQL);
            selectPstmt.setInt(1, userId);
            ResultSet userResult = selectPstmt.executeQuery();
            if (userResult.next()) {
                System.out.println("User Info: ");
                System.out.println("ID: " + userResult.getInt("id"));
                System.out.println("Name: " + userResult.getString("name"));
                System.out.println("Email: " + userResult.getString("email"));
            }
            // Step 6: Retrieve data from MongoDB
            Document userOrder = ordersCollection.find(Filters.eq("user_id", userId)).first();
            if (userOrder != null) {
                System.out.println("Order Info: ");
                System.out.println(userOrder.toJson());
            }

            // Close connections
            mysqlConnection.close();
            mongoClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

