package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.common.exception.PlatformException;
import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.questionhub.entity.QuestionDo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yizhuoyan.shidao.common.util.AssertThrowUtil.assertNotBlank;
import static com.yizhuoyan.shidao.common.util.AssertThrowUtil.assertTrue;

/**
 * Created by ben on 11/29/17.
 */
public abstract class ChoiceQuestionAbstractParser implements QuestionParser {
    private static final Pattern PATTERN_OPTION = Pattern.compile("^[a-zA-Z]?[ 　、.:：)）]?(.+)");


    /**
     * 格式如下：
     * 我是题干，我可以换行书写，我是题干，我
     * 可以换行书写（这里写正确选项）
     * <空一行>
     * <空一行>
     * 我是正确的选项1
     * 解析：我是选项1的解析
     * 我是正确的选项2
     * 解析：我是选项2的解析
     * ...
     * <空一行>
     * <空一行>
     * 解析：我是整体解析
     * ...
     *
     * @param contentString
     * @return
     * @throws Exception
     */
    @Override
    public QuestionDo parse(String contentString) throws Exception {
        String[] splitResult = PATTERN_AT_LEST_3_LINES.split(contentString);
        if (splitResult.length < 2) {
            throw new PlatformException("question-content-format-error");
        }
        QuestionDo q = new QuestionDo();


        parseTitleAndAnswer(splitResult[0], q);

        parseOptionAndExplain(splitResult[1], q);

        if (splitResult.length > 2) {
            parseQuestionExplain(splitResult[2], q);
        }
        q.setContent(contentString);

        validateQuestion(q);
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

    protected void parseTitleAndAnswer(String titleString, QuestionDo q) {
        titleString = titleString.trim();
        //find the parentheses
        int ptEnd = lastIndexOf(titleString,')','）');
        assertTrue("question-parse-answer-error", ptEnd != -1);

        int ptBegin = lastIndexOf(titleString,ptEnd,'(','（');
        assertTrue("question-parse-answer-error", ptBegin != -1);

        String answerString=titleString.substring(ptBegin+1,ptEnd);
        assertNotBlank("question-parse-answer-blank",answerString);
        q.setAnswer(parseAnswer(answerString));


        StringBuilder title=new StringBuilder(titleString.substring(0,ptBegin+1));
        title.append(PlatformUtil.repeat("　",4));
        title.append(titleString.substring(ptEnd));
        q.setTitle(title.toString());
    }

    /**
     * ABCD--->1234
     * @param answer
     * @return
     */
    private String parseAnswer(String answer){
        answer=answer.toUpperCase();
        char[] result=new char[answer.length()];
        for(int i=answer.length();i-->0;){
            result[i]=(char)('1'+(answer.charAt(i)-'A'));
        }
        return new String(result);
    }

    /**
     * 我是正确的选项1
     * 解析：我是选项1的解析
     * 我是正确的选项2
     * 解析：我是选项2的解析
     * ...
     * @param optionString
     * @param q
     * @throws Exception
     */
    protected void parseOptionAndExplain(String optionsString, QuestionDo q) throws Exception {
        //parse the option and answer and answer key
        String[] splitResult = optionsString.split("\r?\n");
        List<String> options=new ArrayList<>(8);
        List<String> optionExplains=new ArrayList<>(8);

        for(String split:splitResult){
            //jump the blank line
            if(PlatformUtil.isBlank(split))continue;

            if(startWithExplain(split)){
                optionExplains.add(parseOptionExplain(split));
            }else {
                options.add(parseOption(split));
            }
        }
        q.setAnswerExplain(PlatformUtil.join(optionExplains,"\n"));
        q.setOptions(PlatformUtil.join(options,"\n"));
    }
    /**
     * A、
     * a.
     * A.
     * @param optionString
     * @return
     */
    private String parseOption(String optionString){
        optionString=optionString.trim();

        Matcher matcherResult=PATTERN_OPTION.matcher(optionString);
        if(matcherResult.matches()){
            return matcherResult.group(1);
        }
        throw new PlatformException("question-parse-option-error");
    }
    private String parseOptionExplain(String optionExplainString){
        return optionExplainString.substring(3).trim();
    }

    public static void main(String[] args) throws Throwable {
        String s="D.以上都不对";
        Pattern pattern = Pattern.compile("^[a-zA-Z]?[ 　、.:：)）]?(.+)");
        Matcher matcherResult=pattern.matcher(s);
        if(matcherResult.matches()){
            int groupCount=matcherResult.groupCount();
            System.out.println(matcherResult.group(1));
        }
    }

    /**
     * 整体解析放入第0个
     * @param explain
     * @param q
     * @throws Exception
     */
    private void parseQuestionExplain(String explain, QuestionDo q) throws Exception {
        if(startWithExplain(explain)){
            explain=explain.substring(3).trim();
        }
        q.setAnswerExplain(explain+"\n"+q.getAnswerExplain());
    }

    abstract  protected void validateQuestion(QuestionDo q)throws Exception;
}
