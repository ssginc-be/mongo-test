package com.qriosity.mongotest.mongo.user_insert;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Queue-ri
 */
public class NaverInsertMain {
    public static void main(String[] args) {
        // 0. 로그 숨기기
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.WARNING);

        MongoDBCafeInsert.insertCafeMember();
        MongoDBBlogInsert.insertBlogMember();
        MongoDBLineInsert.insertLineMember();
    }
}
