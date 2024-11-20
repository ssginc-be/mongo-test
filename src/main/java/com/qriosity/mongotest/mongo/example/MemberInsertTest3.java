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
public class MemberInsertTest3 { // 여러개 추가
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
        Document doc = new Document();
        doc.append("id", "apple2");
        doc.append("pw", "1234");
        doc.append("name", "apple2");
        doc.append("tel", "011-123-4567");

        Document doc2 = new Document();
        doc2.append("id", "apple3");
        doc2.append("pw", "1234");
        doc2.append("name", "apple3");
        doc2.append("tel", "011-123-4567");

        List<Document> docList = new ArrayList<Document>();
        docList.add(doc);
        docList.add(doc2);

        // 4. 전송 및 결과처리
        collection.insertMany(docList);
        System.out.println("* * * 추가 완료");
        client.close();
    }
}
