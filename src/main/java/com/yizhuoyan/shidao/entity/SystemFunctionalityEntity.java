/**
 * shidao
 * SysFunctionalityModel.java
 * 2015年10月30日
 */
package com.yizhuoyan.shidao.entity;

import com.yizhuoyan.common.util.KeyValueMap;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author root@yizhuoyan.com
 *
 */
@Data
public class SystemFunctionalityEntity {
    /**
     * 状态常量
     */
    public static final int STATUS_ACTIVATE = 1, STATUS_DEACTIVATE = 0;
    /**
     * 系统功能类型
     * 目录
     * 菜单
     * 按钮或链接
     */
    public static final int KIND_DIRECTORY = 0, KIND_MENU = 1, KIND_BUTTON_AND_LINK = 2;

    private static final long serialVersionUID = 8390482014375996154L;
    /**
     * 唯一标识符
     */
    private String id;
    /**
     * 代号
     */
    private String code;
    /**
     * 名字
     */
    private String name;
    /**
     * 路径
     */
    private String url;

    /**
     * 父模块
     */
    private String parentId;
    private SystemFunctionalityEntity parent;

    /**
     * 排序号
     */
    private String orderCode;
    /**
     * 状态
     */
    private int status;
    /**
     * 所有直接子模块
     */
    private List<SystemFunctionalityEntity> children;
    /**
     * 备注
     */
    private String remark;
    /**
     * 功能界面种类
     */
    private int kind;

    private String createUserId;
    private SystemUserEntity createUser;
    private Instant createTime;

    /**
     * 拥有角色
     */
    private List<SystemRoleEntity> roles;


    public Map toJSON() {

        KeyValueMap m = new KeyValueMap(12);

        m.put("id", id);
        m.put("code", code);
        m.put("name", name);
        m.put("kind", kind);
        m.put("status", status);

        m.put("url", url);
        m.put("orderCode", orderCode);
        m.put("remark", remark);
        m.put("parentId", parentId);
        if (this.parent != null) {
            m.put("parent", KeyValueMap.of(2)
                    .put("id", parent.id)
                    .put("name", parent.name)
                    .put("code", parent.code)
            );
        }
        m.put("createTime", createTime);

        if (createUser != null) {
            m.put("createUser", KeyValueMap.of(2)
                    .put("id", createUser.getId())
                    .put("name", createUser.getName())
            );
        }
        if (this.roles != null) {
            int size = this.roles.size();
            Object[] roles = new Object[size];
            SystemRoleEntity r;
            while (size-- > 0) {
                r = this.roles.get(size);
                roles[size] = KeyValueMap.of(2)
                        .put("id", r.getId())
                        .put("name", r.getName());
            }
            m.put("roles", roles);
        }
        return m;
    }

}
