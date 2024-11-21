package com.qriosity.mongotest.mongo.msa.quiz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Queue-ri
 */
@Getter
@AllArgsConstructor
public class UserInfo {
    private String name;
    private String email;
}
