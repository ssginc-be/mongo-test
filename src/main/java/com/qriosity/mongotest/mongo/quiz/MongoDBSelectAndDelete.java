package com.qriosity.mongotest.mongo.quiz;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Queue-ri
 */
public class MongoDBSelectAndDelete {
    public static void main(String[] args) {
        // 0. 로그 숨기기
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.WARNING);

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("quiz");
        MongoCollection<Document> collection = database.getCollection("item");

        // 회원 이름으로 검색
        String category = "Books"; // 검색할 서비스 이름
        Integer price = 25000; // 검색할 이름
        Document query = new Document("category", category).append("price", new Document("$gte", price));
        Document item = collection.find(query).first();
        if (item != null) {
            System.out.println("검색된 데이터 정보: " + item.toJson());
            // 데이터 삭제
            collection.deleteOne(query);
            System.out.println("데이터 삭제 완료.");
        } else {
            System.out.println("조건을 만족하는 데이터가 없습니다.");
        }
        mongoClient.close();
    }
}
