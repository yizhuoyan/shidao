package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.common.exception.PlatformException;
import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ben on 11/29/17.
 */
public class YesNoQuestionParser implements QuestionParser {
    @Override
    public QuestionModel parse(String content) throws Exception {
        QuestionModel q=new QuestionModel();
        q.setContent(content);
        return q;
    }

}
