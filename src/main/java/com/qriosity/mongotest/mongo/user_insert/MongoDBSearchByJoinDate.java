package com.qriosity.mongotest.mongo.user_insert;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Queue-ri
 */
public class MongoDBSearchByJoinDate {
    public static void main(String[] args) {
        // 0. 로그 숨기기
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.WARNING);

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("naver");
        MongoCollection<Document> collection = database.getCollection("members");
        // 라인 서비스에서 가입일이 특정 날짜 이후인 회원 정보 검색
        String joinDate = "2021-12-01"; // 특정 날짜 이후 가입한 회원
        Document query = new Document("service", "Line").append("joinDate", new Document("$gt", joinDate));
        for (Document member : collection.find(query)) {
            System.out.println("검색된 회원 정보: " + member.toJson());
        }
        mongoClient.close();
    }
}
