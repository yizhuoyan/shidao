package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.common.exception.PlatformException;
import com.yizhuoyan.shidao.questionhub.entity.QuestionDo;

/**
 * Created by ben on 11/29/17.
 */
public class ChoiceSingleQuestionParser extends ChoiceQuestionAbstractParser {
    @Override
    protected void validateQuestion(QuestionDo q) throws Exception {
        //仅有1个正确答案,至少4个选项
        String[] options=q.getOptions().split("\n");
        if(options.length<4){
            throw  new PlatformException("选项个数少于4个");
        }
        String answer=q.getAnswer();
        if(answer.length()==1){
            throw  new PlatformException("仅有1个正确答案");
        }
    }
}
