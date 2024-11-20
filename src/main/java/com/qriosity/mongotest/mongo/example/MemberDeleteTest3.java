package com.qriosity.mongotest.mongo.example;

// 로그 숨기기

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Queue-ri
 */
public class MemberDeleteTest3 {
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
        
        // 3. 전송할 js 생성
        // delete할 json(document) 조건(filter)을 생성
        Document filter = new Document();
        filter.append("pw", "1234");

        // 4. 전송 및 결과처리
        collection.deleteMany(filter);
        System.out.println("* * * 삭제 완료");
        client.close();
    }
}
