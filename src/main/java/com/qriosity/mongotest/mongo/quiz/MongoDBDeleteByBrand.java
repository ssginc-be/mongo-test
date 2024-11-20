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
public class MongoDBDeleteByBrand {
    public static void main(String[] args) {
        // 0. 로그 숨기기
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.WARNING);

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("quiz");
        MongoCollection<Document> collection = database.getCollection("item");

        // Clothing 카테고리에서 brand가 "Nike"인 상품 삭제
        String brand = "Nike";
        Document query = new Document("category", "Clothing").append("brand", brand);
        collection.deleteOne(query);
        System.out.println("데이터 삭제 완료.");
        mongoClient.close();
    }
}
