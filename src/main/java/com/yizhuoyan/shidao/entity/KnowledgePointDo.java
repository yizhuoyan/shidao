package com.yizhuoyan.shidao.entity;

import com.yizhuoyan.common.util.KeyValueMap;
import lombok.Data;

import java.util.Map;

/**
 *知识点
 */
@Data
public class KnowledgePointDo {
    private String id;
    private String code;
    private String name;
    private String remark;
    private int childrenAmount;
    private String parentId;
    //下一个可用的子节点代号
    private int nextChildCode;

    private KnowledgePointDo parent;



    public Map toJsonMap(){
        KeyValueMap map=new KeyValueMap(4);
        map.put("id",this.id);
        map.put("code",this.code);
        map.put("name",this.name);


        if(this.remark!=null) {
            map.put("remark", this.remark);
        }

        map.put("childrenAmount", this.childrenAmount);
        map.put("parentId",this.parentId);
        if(this.parent!=null){
            map.put("parent",KeyValueMap.of(3)
                    .put("id",parent.getId())
                    .put("code",parent.getCode())
                    .put("name",parent.getName())
            );
        }
        return  map;
    }


}
