package com.yizhuoyan.shidao.questionhub.parser;

import com.yizhuoyan.shidao.common.exception.PlatformException;
import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ben on 11/29/17.
 */
public class ChoiceAnyQuestionParser implements QuestionParser {
    /**
     * 格式如下：
     * xxxxx
     *
     *
     * ×选项1
     * 解析：
     * 选项2
     * 解析：
     *
     * @param contentString
     * @return
     * @throws Exception
     */
    @Override
    public QuestionModel parse(String contentString) throws Exception {
        String[] titleAndOptions=contentString.split("\r?n{2,}");

        if(titleAndOptions.length<2){
            throw  new PlatformException("question-content-format-error");
        }
        String title=titleAndOptions[0];

        String optionsString=titleAndOptions[1];
        //parse the option and answer and answer key
        String[] options=optionsString.split("\r?\n");
        for (int i = 0; i <options.length ; i++) {
            options[i]=cleanOption(options[i]);
        }
        QuestionModel q=new QuestionModel();
        q.setContent(title);
        q.setSupplyContent(PlatformUtil.join(options,"\n"));
        return q;
    }

    private String cleanOption(String option)throws Exception{

        throw  new PlatformException("question-content-format-error");
    }
}
