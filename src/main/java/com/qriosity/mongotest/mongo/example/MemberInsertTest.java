package com.qriosity.mongotest.mongo.example;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @author Queue-ri
 */
public class MemberInsertTest {
    public static void main(String[] args) {
        // 1. MongoClient 생성
        MongoClient client = new MongoClient("localhost", 27017);

        // 2. db 연결 및 collection 연결
        MongoDatabase db = client.getDatabase("shop");
        MongoCollection<Document> collection = db.getCollection("member");
        System.out.println("* * * 연결 성공");
        
        // 3. 전송할 js 생성
        Document doc = new Document();
        doc.append("id", "apple");
        doc.append("pw", "1234");
        doc.append("name", "apple");
        doc.append("tel", "011-123-4567");

        // 4. 전송 및 결과처리
        collection.insertOne(doc);
        System.out.println("* * * 추가 완료");
        client.close();
    }
}
