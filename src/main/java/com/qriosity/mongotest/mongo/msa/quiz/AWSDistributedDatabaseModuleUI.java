package com.qriosity.mongotest.mongo.msa.quiz;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.qriosity.mongotest.mongo.msa.quiz.vo.UserInfo;
import org.bson.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Queue-ri
 */

// entry point
public class AWSDistributedDatabaseModuleUI {
    public static void main(String[] args) throws Exception {
        // 0. 로그 숨기기
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.WARNING);

        // 1. insert
        int uid = AWSDistributedDatabaseModule.createUserInfo(
                new UserInfo("Queue-ri", "qriositylog@gmail.com")
        );
        AWSDistributedDatabaseModule.createUserActivity(uid);

        // 2. select
        AWSDistributedDatabaseModule.findUserInfoAndActivityLog(uid);

        // 3. cleanup
        AWSDistributedDatabaseModule.cleanup();
    }
}

class AWSDistributedDatabaseModule {
    // MySQL JDBC URL
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/aws";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "Abcd123!";

    private static Connection mysqlConnection;
    private static MongoClient mongoClient;
    
    public static int createUserInfo(UserInfo info) { // --> MySQL insert
        try {
            // MySQL 연결
            mysqlConnection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            System.out.println("* * * createUserInfo: MySQL 연결 성공");

            // 데이터 삽입
            String insertUserSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
            PreparedStatement pstmt = mysqlConnection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, info.getName());
            pstmt.setString(2, info.getEmail());
            pstmt.executeUpdate();

            // Auto increment로 생성된 ID값 가져오기
            ResultSet rs = pstmt.getGeneratedKeys();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt(1);
            }
            System.out.println("* * * createUserInfo: 생성된 ID값: " + userId);
            return userId;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void createUserActivity(int userId) { // --> MongoDB insert
        // MongoDB 연결
        mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("aws");
        MongoCollection<Document> logCollection = mongoDatabase.getCollection("activity_logs");
        System.out.println("* * * createUserActivity: MongoDB 연결 성공");

        // 데이터 삽입
        Document activityLog = new Document();
        activityLog.append("userId", userId);
        activityLog.append("activity", "Login");
        activityLog.append("timestamp", "2024-11-10T10:15:00");

        Document activityLog2 = new Document();
        activityLog2.append("userId", userId);
        activityLog2.append("activity", "Order");
        activityLog2.append("timestamp", "2024-11-11T11:15:00");

        Document activityLog3 = new Document();
        activityLog3.append("userId", userId);
        activityLog3.append("activity", "Order");
        activityLog3.append("timestamp", "2024-11-21T11:15:00");

        logCollection.insertOne(activityLog);
        logCollection.insertOne(activityLog2);
        logCollection.insertOne(activityLog3);

        System.out.println("* * * createUserActivity: insert 성공");

    }

    public static void findUserInfoAndActivityLog(int userId) throws Exception {
        // MySQL에서 사용자 정보 받아오기
        String selectUserSQL = "SELECT * FROM users WHERE id = ?";
        PreparedStatement selectPstmt = mysqlConnection.prepareStatement(selectUserSQL);
        selectPstmt.setInt(1, userId);
        ResultSet userResult = selectPstmt.executeQuery();
        if (userResult.next()) {
            System.out.println("* * * findUserInfoAndActivityLog: User Info: ");
            System.out.println("ID: " + userResult.getInt("id"));
            System.out.println("Name: " + userResult.getString("name"));
            System.out.println("Email: " + userResult.getString("email"));
        }
        
        // MongoDB에서 사용자 정보 받아오기
        Document filter = new Document("userId", userId);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("aws");
        MongoCollection<Document> logCollection = mongoDatabase.getCollection("activity_logs");
        List<Document> log = logCollection.find(filter).into(new ArrayList<Document>());
        if (log != null) {
            System.out.println("* * * findUserInfoAndActivityLog: Log Info: ");
            for (Document doc : log)
                System.out.println(doc.toJson());
        }
    }

    public static void cleanup() throws Exception {
        // connection 종료
        mysqlConnection.close();
        mongoClient.close();
    }
}