package com.qriosity.mongotest.mongo.example;

import com.mongodb.MongoClient;
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
public class MemberSelectTest2 { // 여러개 검색
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
        // --> 조건 없음

        // 4. 전송 및 결과처리
        List<Document> result = collection.find().into(new ArrayList<>());
        for (Document doc : result) {
            System.out.println("id: " + doc.get("id"));
            System.out.println("pw: " + doc.get("pw"));
            System.out.println("name: " + doc.get("name"));
            System.out.println("tel: " + doc.get("tel"));
            System.out.println("-----------------------");
        }
        
        client.close();
    }
}
