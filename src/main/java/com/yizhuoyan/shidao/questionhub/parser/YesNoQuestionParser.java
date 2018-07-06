package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.questionhub.entity.QuestionDo;

/**
 * Created by ben on 11/29/17.
 */
public class YesNoQuestionParser implements QuestionParser {

    /**
     * 正确描述
     错误描述1
     错误描述2
     ....


     解析：
     解析内容
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
                parseOptions(splitResult[0], q);
                break;
        }
        return q;
    }
    private void parseOptions(String optionsString,QuestionDo q){
        optionsString=optionsString.trim();
        String[] options=optionsString.split("\r?\n");
        q.setTitle(PlatformUtil.join(options,"\n"));
    }
    private void parseExplain(String explainString,QuestionDo q){
        explainString=explainString.trim();
        if(startWithExplain(explainString)){
            explainString=explainString.substring(3).trim();
        }
        q.setAnswerExplain(explainString);
    }

}
