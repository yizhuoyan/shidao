/*
 * shidao
 * SysRoleModel.java
 * 2015年10月30日
 */
package com.yizhuoyan.shidao.entity;

import com.yizhuoyan.common.util.KeyValueMap;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author root@yizhuoyan.com
 */
@Data
public class SystemRoleEntity implements Serializable {

    private static final long serialVersionUID = 6361545273496684832L;

    /**
     * 唯一标识符
     */
    private String id;
    /**
     * 名字
     */
    private String name;
    /**
     * 代号
     */
    private String code;
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
    /**
     * 角色下功能模块
     */
    private List<SystemFunctionalityEntity> functionalitys;


    public Map simpleToJSON() {
        KeyValueMap m = KeyValueMap.of(5);
        m.put("id", id);
        m.put("code", code);
        m.put("name", name);
        m.put("status", status);
        return m;
    }

    public Map toJSON() {
        KeyValueMap m = KeyValueMap.of(5);
        m.put("id", id);
        m.put("code", code);
        m.put("name", name);
        m.put("status", status);
        m.put("remark", remark);
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
