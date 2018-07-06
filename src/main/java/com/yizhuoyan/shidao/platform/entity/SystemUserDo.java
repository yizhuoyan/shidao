package com.yizhuoyan.shidao.platform.entity;

import com.yizhuoyan.shidao.common.util.KeyValueMap;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 系统用户实体
 *
 * @author ben
 */
@Data
public class SystemUserDo {
    private static final long serialVersionUID = 1390116807248341188L;
    /**
     * 正常
     */
    public static final int STATUS_NORMAL = 0;
    /**
     * 锁定
     */
    public static final int STATUS_LOCKED = 1;

    /**
     * 已删除
     */
    public static final int STATUS_DELETED = 2;
    /**
     * 唯一标识符
     */
    private String id;
    /**
     * 账户
     */
    private String account;
    /**
     * 名字
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像路径
     */
    private String avator;


    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;
    /**
     * 最后一次修改密码时间
     */
    private Date lastModPassTime;
    /**
     * 备注
     */
    private String remark;

    private String loginIp;


    /**
     * 状态
     * 0=正常
     * 1=锁定
     */
    private int status;


    public Map toJSON() {
        KeyValueMap m = KeyValueMap.of(8);
        m.put("id", this.getId());
        m.put("account", this.getAccount());
        m.put("name", this.getName());
        m.put("status", this.getStatus());
        m.put("avator", this.getAvator());
        if(this.remark!=null) {
            m.put("remark", this.getRemark());
        }
        m.put("createTime", this.getCreateTime());
        if(this.lastLoginTime!=null) {
            m.put("lastLoginTime", this.getLastLoginTime());
        }
        if(this.lastModPassTime!=null) {
            m.put("lastModPasswordTime", this.getLastModPassTime());
        }
        return m;
    }


    public boolean isLock() {
        return this.status == STATUS_LOCKED;
    }

}
