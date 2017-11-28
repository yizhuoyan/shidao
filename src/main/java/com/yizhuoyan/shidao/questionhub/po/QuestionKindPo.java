package com.yizhuoyan.shidao.questionhub.po;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public class QuestionKindPo {
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String remark;
    private String introduction;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
