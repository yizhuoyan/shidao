package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.questionhub.entity.QuestionDo;

import static com.yizhuoyan.shidao.common.util.AssertThrowUtil.assertNotBlank;
import static com.yizhuoyan.shidao.common.util.AssertThrowUtil.assertTrue;

/**
 * Created by ben on 11/29/17.
 */
public class FillInBlankQuestionParser implements QuestionParser {

    /**
     * 题干题干题干（正确答案，多个正确答案用/分割）
     <空一行>
     <空一行>
     解析：xxxx
     * @param contentString
     * @return
     * @throws Exception
     */
    @Override
    public QuestionDo parse(String contentString) throws Exception {
        QuestionDo q = new QuestionDo();
        q.setContent(contentString);

        String[] splitResult = PATTERN_AT_LEST_3_LINES.split(contentString);
        switch (splitResult.length){
            case 2:
                parseExplain(splitResult[1], q);
            case 1:
                parseTitleAndAnswer(splitResult[0], q);
                break;
        }
        return q;
    }
    private int lastIndexOf(String s, char... cs) {
        int result = -1;
        int i = 0;
        do {
            if (i >= cs.length) break;
            result = s.lastIndexOf(cs[i++]);
        } while (result == -1);
        return result;
    }

    private int lastIndexOf(String s,int begin,char... cs) {
        int result = -1;
        int i = 0;
        do {
            if (i >= cs.length) break;
            result = s.lastIndexOf(cs[i++],begin);
        } while (result == -1);
        return result;
    }

    private void parseTitleAndAnswer(String titleString,QuestionDo q){
        //find the parentheses
        int ptEnd = lastIndexOf(titleString,')','）');
        assertTrue("question-parse-answer-error", ptEnd != -1);

        int ptBegin = lastIndexOf(titleString,ptEnd,'(','（');
        assertTrue("question-parse-answer-error", ptBegin != -1);

        String answerString=titleString.substring(ptBegin+1,ptEnd);
        assertNotBlank("question-parse-answer-blank",answerString);
        //answerString.split("[;,/ ]");
        q.setAnswer(answerString);


        StringBuilder title=new StringBuilder(titleString.substring(0,ptBegin));
        title.append(PlatformUtil.repeat("__",8));
        title.append(titleString.substring(ptEnd+1));
        q.setTitle(title.toString());
    }


    private void parseExplain(String explainString,QuestionDo q){
        if(startWithExplain(explainString)){
            explainString=explainString.substring(3).trim();
        }
        q.setAnswerExplain(explainString);
    }
}
