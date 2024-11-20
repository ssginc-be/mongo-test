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
public class MongoDBSearchByNameLike { // 1번
    public static void main(String[] args) {
        // 0. 로그 숨기기
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.WARNING);

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("quiz");
        MongoCollection<Document> collection = database.getCollection("item");

        // 조건 검색 - name에 "Smart"가 포함된 상품
        String name = "Smart";
        Document query = new Document("category", "Electronics").append("name", new Document("$regex", name));
        for (Document item : collection.find(query)) {
            System.out.println("검색된 데이터: " + item.toJson());
        }
        mongoClient.close();
    }
}
