package com.yizhuoyan.shidao.usercommon.dto;

import com.yizhuoyan.common.util.KeyValueMap;
import com.yizhuoyan.shidao.entity.SystemFunctionalityEntity;
import com.yizhuoyan.shidao.entity.SystemRoleEntity;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 用户上下文
 *
 * @author Administrator
 */
@Data
public class UserContext {
    //用户账户信息
    private String userId;
    private String name;
    private String account;
    private boolean firstLogin;
    private String avatar;
    private String lastLoginIp;


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
    /**
     * 是否锁定
     */
    private boolean locked;


    private String currentLoginIp;


    //用户拥有角色
    private LinkedHashMap<String, SystemRoleEntity> rolesMap;
    //所有功能
    private LinkedHashMap<String, SystemFunctionalityEntity> functionalitysMap;
    //菜单功能
    private List<SystemFunctionalityEntity> menuFunctionalitys;
    //登陆凭据
    private String token;

    static public UserContext getCurrentUser(HttpServletRequest req) {

        return (UserContext) req.getSession().getAttribute(UserContext.class.getName());
    }

    static public UserContext getCurrentUser() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getCurrentUser(req);
    }

    static public void saveCurrentUser(UserContext uc) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        req.getSession().setAttribute(UserContext.class.getName(), uc);
    }

    public Object toJson() {
        KeyValueMap map = new KeyValueMap(12);
        map.put("token", token);
        map.put("account", account);
        map.put("name", name);
        map.put("avatar", avatar);
        if (locked) {
            map.put("locked", true);
        }

        if (firstLogin) {
            map.put("firstLogin", true);
        } else {
            map.put("lastLoginTime", lastLoginTime);
            map.put("lastLoginIp", lastLoginIp);
        }

        map.put("lastModifyPasswordTime", lastModifyPasswordTime);
        map.put("createTime", createTime);
        map.put("loginIp", currentLoginIp);
        return map;
    }
}
