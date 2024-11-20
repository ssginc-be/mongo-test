package com.qriosity.mongotest.mongo.example;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import com.mongodb.client.FindIterable;

/**
 * @author Queue-ri
 */
public class ConnectionTest {
    public static void main(String[] args) {
        Bson filter = new Document("tel", "972-957-4245");
        MongoClient mongoClient = new MongoClient(
                new MongoClientURI(
                        "mongodb://localhost:27017/"
                )
        );
        MongoDatabase database = mongoClient.getDatabase("shop");
        MongoCollection<Document> collection = database.getCollection("member");
        FindIterable<Document> result = collection.find(filter);
        System.out.println("* * * 검색 결과");
        System.out.println("- id: " + result.first().getString("id"));
        System.out.println("- pw: " + result.first().getString("pw"));
        System.out.println("- name: " + result.first().getString("name"));
        System.out.println("- tel: " + result.first().getString("tel"));
    }
}
