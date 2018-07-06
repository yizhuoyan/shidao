package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.entity.QuestionDo;

import java.util.regex.Pattern;

/**
 * Created by ben on 11/29/17.
 */
public interface QuestionParser {
    Pattern PATTERN_AT_LEST_3_LINES = Pattern.compile("(?:\r?\n){3,}");
    Pattern PATTERN_AT_LEST_2_LINES = Pattern.compile("(?:\r?\n){2,}");

    QuestionDo parse(String content)throws  Exception;

    default boolean startWithExplain(String s){
        if(s.startsWith("解析:")||s.startsWith("解析：")){
            return true;
        }
        return false;
    }
    default boolean startWithAnswer(String s){
        if(s.startsWith("答案:")||s.startsWith("答案：")){
            return true;
        }
        return false;
    }
}
