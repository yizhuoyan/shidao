package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.common.exception.PlatformException;
import com.yizhuoyan.shidao.entity.QuestionDo;

/**
 * Created by ben on 11/29/17.
 */
public class ChoiceMultiQuestionParser extends ChoiceQuestionAbstractParser{
    @Override
    protected void validateQuestion(QuestionDo q) throws Exception {
        //至少有2个正确答案,至少4个选项
        String[] options=q.getOptions().split("\n");
        if(options.length<4){
            throw  new PlatformException("选项个数少于4个");
        }
        String answer=q.getAnswer();
        if(answer.length()<2){
            throw  new PlatformException("至少有2个正确答案");
        }
    }
}
