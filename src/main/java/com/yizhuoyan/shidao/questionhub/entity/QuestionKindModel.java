package com.yizhuoyan.shidao.questionhub.entity;

import com.yizhuoyan.shidao.common.util.KeyValueMap;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public class QuestionKindModel {
    private String id;
    private String name;
    private String remark;
    private String introduction;


    public Map toJsonMap(){
        KeyValueMap map=new KeyValueMap(4);
        map.put("id",this.id);
        map.put("name",this.name);
        if(this.remark!=null) {
            map.put("remark", this.remark);
        }
        if(this.introduction!=null) {
            map.put("introduction", this.introduction);
        }
        return  map;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
