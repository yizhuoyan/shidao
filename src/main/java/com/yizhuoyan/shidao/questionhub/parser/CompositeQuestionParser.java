package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.common.exception.PlatformException;
import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.questionhub.entity.QuestionDo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ben on 11/29/17.
 */
public class CompositeQuestionParser  implements QuestionParser {
    private static final Pattern PATTERN_QUESTION_TITLE= Pattern.compile("^[1-9]?[ 　、.:：)）]?(.+)");


    /**
     * 题干题干题干题干
     题干题干题干题干
     <空一行>
     <空一行>
     1.小题1题干
     <空一行>
     答案：答案内容
     <空一行>
     解析：
     解析内容
     <空一行>
     2.小题2题干
     <空一行>
     <空一行>
     答案：答案内容
     <空一行>
     解析：
     解析内容
     <空一行>
     ....
     * @param contentString
     * @return
     * @throws Exception
     */
    @Override
    public QuestionDo parse(String contentString) throws Exception {
        QuestionDo q=new QuestionDo();
        q.setContent(contentString);

        String[] splitResult= PATTERN_AT_LEST_3_LINES.split(contentString,2);
        parseTitle(splitResult[0],q);

        parseQuestions(splitResult[1],q);

        return q;
    }
    private void parseTitle(String titleString,QuestionDo q){
        q.setTitle(titleString.trim());
    }
    private void parseQuestions(String questionsString,QuestionDo q){

        String[] splitResult= PATTERN_AT_LEST_2_LINES.split(questionsString);
        List<String> questionTitles=new ArrayList<>(4);
        List<String> questionAnswers=new ArrayList<>(4);
        List<String> questionAnswerExplains=new ArrayList<>(4);
        for(String split:splitResult){
            if(startWithExplain(split)){
                questionAnswerExplains.add(parseExplain(split));
            }else if(startWithAnswer(split)) {
                questionAnswers.add(parseAnswer(split));
            }else {
                questionTitles.add(parseTitle(split));
            }
        }
        q.setOptions(PlatformUtil.join(questionTitles,"\n"));
        q.setAnswer(PlatformUtil.join(questionAnswers,"\n"));
        q.setAnswerExplain(PlatformUtil.join(questionAnswerExplains,"\n\n"));


    }
    private String parseTitle(String titleString){
        titleString=titleString.trim();

        Matcher matcherResult=PATTERN_QUESTION_TITLE.matcher(titleString);
        if(matcherResult.matches()){
            return matcherResult.group(1);
        }
        throw new PlatformException("question-parse-option-error");
    }
    private String parseAnswer(String answerString){
        if(startWithAnswer(answerString)){
            return answerString.substring(3).trim();
        }
        return answerString.trim();
    }
    private String parseExplain(String explainString){
        if(startWithExplain(explainString)){
            return explainString.substring(3).trim();
        }else{
            return explainString.trim();
        }
    }

}
