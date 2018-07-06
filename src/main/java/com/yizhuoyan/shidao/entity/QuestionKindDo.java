package com.yizhuoyan.shidao.entity;

import com.yizhuoyan.common.util.KeyValueMap;
import lombok.Data;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Data
public class QuestionKindDo {
    private String id;
    //类型名称
    private String name;
    //备注
    private String remark;
    //填写说明
    private String instruction;
    //解析类名
    private String parserClassName;


    public Map toJsonMap(){
        KeyValueMap map=new KeyValueMap(4);
        map.put("id",this.id);
        map.put("name",this.name);
        map.put("parserClassName",this.parserClassName);
        if(this.remark!=null) {
            map.put("remark", this.remark);
        }
        if(this.instruction!=null) {
            map.put("instruction", this.instruction);
        }
        return  map;
    }



}
