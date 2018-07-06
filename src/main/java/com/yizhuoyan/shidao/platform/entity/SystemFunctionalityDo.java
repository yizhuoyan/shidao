/**
 * shidao
 * SysFunctionalityModel.java
 * 2015年10月30日
 */
package com.yizhuoyan.shidao.platform.entity;

import com.yizhuoyan.shidao.common.dto.Tree;
import com.yizhuoyan.shidao.common.util.KeyValueMap;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author root@yizhuoyan.com
 *
 */
@Data
public class SystemFunctionalityDo implements Tree.Node<String> {
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

    private SystemFunctionalityDo parent;
    /**
     * 父模块ID
     */
    private String parentId;
    /**
     * 排序号
     */
    private String orderCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态
     */
    private int status;
    /**
     * 所有直接子模块
     */
    private List<SystemFunctionalityDo> children;
    /**
     * 备注
     */
    private String remark;
    /**
     * 功能界面种类
     */
    private int kind;

    /**
     * 拥有角色
     */
    private List<SystemRoleDo> roles;


    public Map toJSON() {

        KeyValueMap result = new KeyValueMap(8);

        result.put("id", this.getId());
        result.put("code", this.getCode());
        result.put("name", this.getName());
        result.put("kind", this.getKind());
        result.put("status", this.getStatus());
        result.put("parentId", this.parentId);

        if (this.url != null) {
            result.put("url", this.getUrl());
        }
        result.put("createTime", this.getCreateTime());
        if (this.orderCode != null) {
            result.put("orderCode", this.getOrderCode());
        }
        if (this.remark != null) {
            result.put("remark", this.getRemark());
        }
        if (this.parent != null) {
            result.put("parent", KeyValueMap.of(2)
                    .put("id", parent.getId())
                    .put("name", parent.getName())
                    .put("code", parent.getCode())
            );
        }

        if (this.roles != null) {
            int size = this.roles.size();
            Object[] roles = new Object[size];
            SystemRoleDo r;
            while (size-- > 0) {
                r = this.roles.get(size);
                roles[size] = KeyValueMap.of(2)
                        .put("id", r.getId())
                        .put("name", r.getName());
            }
            result.put("roles", roles);
        }
        return result;
    }

}
