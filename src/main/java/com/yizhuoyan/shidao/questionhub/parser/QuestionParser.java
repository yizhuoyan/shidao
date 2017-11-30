package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;

/**
 * Created by ben on 11/29/17.
 */
public interface QuestionParser {
    QuestionModel parse(String content)throws  Exception;

}
