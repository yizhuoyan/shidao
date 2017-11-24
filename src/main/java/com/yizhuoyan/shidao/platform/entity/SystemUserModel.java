package com.yizhuoyan.shidao.platform.entity;

import com.yizhuoyan.shidao.common.util.KeyValueMap;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 系统用户实体
 *
 * @author ben
 */
public class SystemUserModel  {
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

    private static final long serialVersionUID = 1390116807248341188L;
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


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastModPassTime() {
        return this.lastModPassTime;
    }

    public void setLastModPassTime(Date lastModPassTime) {
        this.lastModPassTime = lastModPassTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isLock() {
        return this.status == STATUS_LOCKED;
    }

}
