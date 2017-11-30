package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.common.exception.PlatformException;
import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ben on 11/29/17.
 */
public class ChoiceSingleQuestionParser implements QuestionParser {
    private Pattern optionPattern=Pattern.compile("\\s*[a-zA-Z]\\s*[.  .:)](.+)$");
    @Override
    public QuestionModel parse(String contentString) throws Exception {
        String[] contentAndsupplyContent=contentString.split("\r?n\r?\n");

        if(contentAndsupplyContent.length<2){
            throw  new PlatformException("question-content-format-error");
        }
        String content=contentAndsupplyContent[0];
        String supplyContent=contentAndsupplyContent[1];
        //去掉选项的ＡＢＣＤ
        String[] options=supplyContent.split("\r?\n");
        for (int i = 0; i <options.length ; i++) {
            options[i]=cleanOption(options[i]);
        }
        QuestionModel q=new QuestionModel();
        q.setContent(content);
        q.setSupplyContent(PlatformUtil.join(options,"\n"));
        return q;
    }

    private String cleanOption(String option)throws Exception{
        Matcher matcher = optionPattern.matcher(option);
        if(matcher.matches()){
            return matcher.group(1);
        }
        throw  new PlatformException("question-content-format-error");
    }
}
