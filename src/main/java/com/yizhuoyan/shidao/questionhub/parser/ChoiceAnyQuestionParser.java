package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.common.exception.PlatformException;
import com.yizhuoyan.shidao.entity.QuestionDo;

/**
 * Created by ben on 11/29/17.
 */
public class ChoiceAnyQuestionParser extends  ChoiceQuestionAbstractParser{


    @Override
    protected void validateQuestion(QuestionDo q) throws Exception {
        //至少有一个正确答案,至少4个选项
        String[] options=q.getOptions().split("\n");
        if(options.length<4){
            throw  new PlatformException("答案少于4个");
        }



    }
}
