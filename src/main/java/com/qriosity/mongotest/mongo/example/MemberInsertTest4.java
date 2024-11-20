package com.qriosity.mongotest.mongo.example;

// 로그 숨기기

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
public class MemberInsertTest4 { // 여러개 추가
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
        List<Document> docList = new ArrayList<Document>();
        for (int i = 0; i < 100; ++i) {
            Document doc = new Document();
            doc.append("id", "user_MemberInsertTest4_" + i);
            doc.append("pw", "1234");
            doc.append("name", "user_MemberInsertTest4_" + i);
            doc.append("tel", "010-9999-9999");
            docList.add(doc);
        }

        // 4. 전송 및 결과처리
        collection.insertMany(docList);
        System.out.println("* * * 추가 완료");
        client.close();
    }
}
