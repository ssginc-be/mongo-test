package com.qriosity.mongotest.mongo.example;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Queue-ri
 */
public class MemberSelectTest {
    public static void main(String[] args) {
        // 0. 로그 숨기기
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.WARNING);

        // 1. MongoClient 생성
        MongoClient client = new MongoClient("localhost", 27017);

        // 2. db 연결 및 collection 연결
        MongoDatabase db = client.getDatabase("shop");
        MongoCollection<Document> collection = db.getCollection("member");
        System.out.println("* * * 연결 성공");
        
        // 3. 전송할 json 생성 (조건)
        Document filter = new Document();
        filter.put("id", "apple");

        // 4. 전송 및 결과처리
        FindIterable<Document> result = collection.find(filter);
        Document doc = result.first();
        System.out.println("id: " + doc.get("id"));
        System.out.println("pw: " + doc.get("pw"));
        System.out.println("name: " + doc.get("name"));
        System.out.println("tel: " + doc.get("tel"));
        System.out.println("* * * 조회 완료");

        // 리스트로 받아오기
        List<Document> result2 = collection.find(filter).into(new ArrayList<Document>());
        Document doc2 = result2.get(0);
        System.out.println("id: " + doc2.get("id"));
        System.out.println("pw: " + doc2.get("pw"));
        System.out.println("name: " + doc2.get("name"));
        System.out.println("tel: " + doc2.get("tel"));

        client.close();
    }
}
