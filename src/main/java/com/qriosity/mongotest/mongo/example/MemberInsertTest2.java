package com.qriosity.mongotest.mongo.example;

// 로그 숨기기
import java.util.logging.Level;
import java.util.logging.Logger;
//

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

/**
 * @author Queue-ri
 */
public class MemberInsertTest2 {
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
        Scanner sc = new Scanner(System.in);
        System.out.println("id>> ");
        String id = sc.next();
        System.out.println("pw>> ");
        String pw = sc.next();
        System.out.println("name>> ");
        String name = sc.next();
        System.out.println("tel>> ");
        String tel = sc.next();
        sc.close();

        Document doc = new Document();
        doc.append("id", id);
        doc.append("pw", pw);
        doc.append("name", name);
        doc.append("tel", tel);

        // 4. 전송 및 결과처리
        collection.insertOne(doc);
        System.out.println("* * * 추가 완료");
        client.close();
    }
}
