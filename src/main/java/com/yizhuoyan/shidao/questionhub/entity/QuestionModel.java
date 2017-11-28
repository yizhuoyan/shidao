package com.yizhuoyan.shidao.questionhub.entity;

import com.yizhuoyan.shidao.common.util.KeyValueMap;
import com.yizhuoyan.shidao.platform.entity.SystemUserModel;

import java.time.Instant;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public class QuestionModel {
    private String id;
    private String content;
    private int difficult;
    private String createUserId;
    private SystemUserModel createUser;
    private String questionKindId;
    private QuestionKindModel questionkind;
    private Instant createTime;
    private Instant updateTime;
    private String answer;
    private String answerExplain;
    private String options;
    private String compositeQuestionId;
    private QuestionModel compositeQuestion;
    private int childrenAmount;

    public Map toJSONMap(){
        KeyValueMap map=new KeyValueMap(12);
        map.put("id",this.id);
        map.put("content",this.content);
        map.put("difficult",this.difficult);
        map.put("kind",KeyValueMap.of(2)
                .put("id",this.questionkind.getId())
                .put("name",this.questionkind.getName())
        );

        map.put("createUser",KeyValueMap.of(2)
                .put("id",this.createUser.getId())
                .put("name",this.createUser.getName())
        );

        map.put("createTime",this.createTime.getEpochSecond()*1000);

        if(this.updateTime!=null) {
            map.put("updateTime", this.updateTime.getEpochSecond() * 1000);
        }

        map.put("answer",this.answer);
        if(this.answerExplain!=null) {
            map.put("answerExplain", this.answerExplain);
        }
        if(this.options!=null) {
            map.put("options", this.options);
        }
        if(this.compositeQuestion!=null) {
            map.put("compositeQuestion", KeyValueMap.of(2)
                    .put("id", this.compositeQuestion.getId())
                    .put("content", this.compositeQuestion.getContent())
            );
        }

        map.put("childrenAmount",this.childrenAmount);

        return map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public SystemUserModel getCreateUser() {
        return createUser;
    }

    public void setCreateUser(SystemUserModel createUser) {
        this.createUser = createUser;
    }

    public String getQuestionKindId() {
        return questionKindId;
    }

    public void setQuestionKindId(String questionKindId) {
        this.questionKindId = questionKindId;
    }

    public QuestionKindModel getQuestionkind() {
        return questionkind;
    }

    public void setQuestionkind(QuestionKindModel questionkind) {
        this.questionkind = questionkind;
    }

    public String getCompositeQuestionId() {
        return compositeQuestionId;
    }

    public void setCompositeQuestionId(String compositeQuestionId) {
        this.compositeQuestionId = compositeQuestionId;
    }

    public QuestionModel getCompositeQuestion() {
        return compositeQuestion;
    }

    public void setCompositeQuestion(QuestionModel compositeQuestion) {
        this.compositeQuestion = compositeQuestion;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }


    public int getChildrenAmount() {
        return childrenAmount;
    }

    public void setChildrenAmount(int childrenAmount) {
        this.childrenAmount = childrenAmount;
    }

}
