package com.yizhuoyan.shidao.questionhub.entity;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public class KnowledgePointModel {
    private String id;
    private String code;
    private String name;
    private String remark;
    private int childrenAmount;
    private String parentId;
    private KnowledgePointModel parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getChildrenAmount() {
        return childrenAmount;
    }

    public void setChildrenAmount(int childrenAmount) {
        this.childrenAmount = childrenAmount;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public KnowledgePointModel getParent() {
        return parent;
    }

    public void setParent(KnowledgePointModel parent) {
        this.parent = parent;
    }
}
