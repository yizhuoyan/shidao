package com.yizhuoyan.shidao.entity;

import com.yizhuoyan.common.util.KeyValueMap;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

/**
 * 系统配置项目
 */
@Data
public class SystemConfigEntity {
    /**
     * 唯一标识符
     */
    private String id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 内容
     */
    private String value;
    /**
     * 描述
     */
    private String remark;
    /**
     * 状态
     * 1=正常
     * 0=停止使用
     */
    private int status;

    private String createUserId;
    private SystemUserEntity createUser;
    private Instant createTime;


    public Map toJSON() {
        KeyValueMap m = new KeyValueMap(7);

        m.put("id", this.id);
        m.put("name", this.name);
        m.put("value", this.value);
        m.put("status", this.status);
        m.put("remark", this.remark);
        m.put("createTime", this.createTime);
        if (createUser != null) {
            m.put("createUser", KeyValueMap.of(2)
                    .put("id", createUser.getId())
                    .put("name", createUser.getName())
            );
        }
        return m;
    }

}
