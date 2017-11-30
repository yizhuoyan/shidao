package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;

/**
 * Created by ben on 11/29/17.
 */
public class EssayQuestionParser implements QuestionParser {
    @Override
    public QuestionModel parse(String content) throws Exception {
        QuestionModel q=new QuestionModel();
        q.setContent(content);
        return q;
    }

}
