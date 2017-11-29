package com.yizhuoyan.shidao.questionhub.po;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public class QuestionPo {
    private String content;
    private String options;
    private int difficult;
    private String kindId;
    private String answer;
    private String answerExplain;
    private String createUserId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerExplain() {
        return answerExplain;
    }

    public void setAnswerExplain(String answerExplain) {
        this.answerExplain = answerExplain;
    }


    public String getCompositeQuestionId() {
        return compositeQuestionId;
    }

    public void setCompositeQuestionId(String compositeQuestionId) {
        this.compositeQuestionId = compositeQuestionId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
}
