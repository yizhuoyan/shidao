package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.questionhub.entity.QuestionDo;

/**
 * Created by ben on 11/29/17.
 */
public class EssayQuestionParser implements QuestionParser {
    @Override
    public QuestionDo parse(String contentString) throws Exception {
        QuestionDo q = new QuestionDo();
        q.setContent(contentString);

        String[] splitResult = PATTERN_AT_LEST_3_LINES.split(contentString);
        switch (splitResult.length){
            case 3:
                parseExplain(splitResult[2], q);
            case 2:
                parseAnswer(splitResult[1], q);
                parseTitle(splitResult[0], q);
                break;
        }
       return q;
    }

    private void parseTitle(String titleString,QuestionDo q){
        q.setTitle(titleString.trim());
    }
    private void parseAnswer(String answerString,QuestionDo q){
        answerString=answerString.trim();
        if(startWithAnswer(answerString)){
            answerString=answerString.substring(3).trim();
        }
        q.setAnswer(answerString);
    }
    private void parseExplain(String explainString,QuestionDo q){
        explainString=explainString.trim();
        if(startWithExplain(explainString)){
            q.setAnswerExplain(explainString.substring(3).trim());
        }else{
            q.setAnswerExplain(explainString);
        }
    }

}
