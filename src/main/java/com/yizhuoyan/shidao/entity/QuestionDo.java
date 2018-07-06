package com.yizhuoyan.shidao.entity;

import com.yizhuoyan.common.util.KeyValueMap;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Data
public class QuestionDo {

    private String id;

    private String content;
    /**

     * 题干
     */
    private String title;
    /**
     * 对应选择题为选项，以换行隔开
     * 对应综合题为小题，以换行隔开
     */
    private String options;
    /**
     * 题目难度
     */
    private int difficult;

    private String createUserId;
    private SystemUserEntity createUser;
    /**
     * 题目类型
     */
    private String questionKindId;
    private QuestionKindDo questionkind;

    private Instant createTime;
    private Instant updateTime;
    /**
     * 题目答案
     */
    private String answer;
    /**
     * 题目答案解析
     */
    private String answerExplain;

    public Map toJSONMap(){
        KeyValueMap map=new KeyValueMap(12);
        map.put("id",this.id);
        map.put("content",this.content);
        map.put("title",this.title);
        map.put("difficult",this.difficult);
        map.put("createUserId",this.createUserId);
        map.put("questionKindId",this.questionKindId);

        if(this.options!=null){
            map.put("options",this.options);
        }
        if(this.questionkind!=null) {
            map.put("kind", KeyValueMap.of(2)
                    .put("id", this.questionkind.getId())
                    .put("name", this.questionkind.getName())
            );
        }
        if(this.createUser!=null) {
            map.put("createUser", KeyValueMap.of(2)
                    .put("id", this.createUser.getId())
                    .put("name", this.createUser.getName())
            );
        }

        map.put("createTime",this.createTime.getEpochSecond()*1000);

        if(this.updateTime!=null) {
            map.put("updateTime", this.updateTime.getEpochSecond() * 1000);
        }

        map.put("answer",this.answer);
        if(this.answerExplain!=null) {
            map.put("answerExplain", this.answerExplain);
        }

        return map;
    }

}
