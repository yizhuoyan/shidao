package com.yizhuoyan.shidao.entity;

import com.yizhuoyan.common.util.KeyValueMap;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

/**
 * 系统用户实体
 *
 * @author ben
 */
@Data
public class SystemUserEntity {
    private static final long serialVersionUID = 1390116807248341188L;
    /**
     * 正常
     */
    public static final int STATUS_NORMAL = 0, STATUS_LOCKED = 1, STATUS_DELETED = 2;
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
    private String avatar;

    /**
     * 注册时间
     */
    private Instant createTime;
    /**
     * 最后一次登录时间
     */
    private Instant lastLoginTime;
    /**
     * 最后一次修改密码时间
     */
    private Instant lastModifyPasswordTime;
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
    private String createUserId;
    private SystemUserEntity createUser;

    public Map toJSON() {
        KeyValueMap m = KeyValueMap.of(10);
        m.put("id", this.id);
        m.put("account", account);
        m.put("name", name);
        m.put("status", status);
        m.put("avatar", avatar);

        m.put("remark", remark);
        m.put("createTime", createTime);
        m.put("lastLoginTime", lastLoginTime);
        m.put("lastModifyPasswordTime", lastModifyPasswordTime);
        if (createUser != null) {
            m.put("createUser", KeyValueMap.of(2)
                    .put("id", createUser.getId())
                    .put("name", createUser.getName())
            );
        }
        return m;
    }


    public boolean isLocked() {
        return this.status == STATUS_LOCKED;
    }

}
