package com.qriosity.mongotest.mongo.example;

// 로그 숨기기

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Queue-ri
 */
public class MemberUpdateTest2 {
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
        // update할 json(document) 조건(filter)을 생성
        Document filter = new Document();
        filter.append("id", "apple");

        // 수정할 내용 지정
        // Map - Bson - Document 순으로 상속 구조임
        Bson pw = Updates.set("pw", "MemberUpdateTest2");
        Bson name = Updates.set("name", "Queue-ri");

        List<Bson> bsonList = new ArrayList<Bson>();
        bsonList.add(pw);
        bsonList.add(name);
        Bson all = Updates.combine(bsonList);

        // 4. 전송 및 결과처리
        collection.updateMany(filter, all);
        System.out.println("* * * 수정 완료");
        client.close();
    }
}
