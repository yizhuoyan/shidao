package com.yizhuoyan.shidao.platform.dto;

import com.yizhuoyan.shidao.common.util.KeyValueMap;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityDo;
import com.yizhuoyan.shidao.platform.entity.SystemRoleDo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 用户上下文
 *
 * @author Administrator
 */
public class UserContext {
    //用户账户信息
    private String userId;
    private String name;
    private String account;
    private boolean firstLogin;
    private String avator;
    private String lastLoginIp;


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
    /**
     * 是否锁定
     */
    private boolean locked;

    public String getCurrentLoginIp() {
        return currentLoginIp;
    }

    public void setCurrentLoginIp(String currentLoginIp) {
        this.currentLoginIp = currentLoginIp;
    }

    private String currentLoginIp;


    //用户拥有角色
    private LinkedHashMap<String, SystemRoleDo> rolesMap;

    //所有功能
    private LinkedHashMap<String, SystemFunctionalityDo> functionalitysMap;
    //菜单功能
    private List<SystemFunctionalityDo> menuFunctionalitys;
    //登陆凭据
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFirstLogin() {
        return this.firstLogin;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public LinkedHashMap<String, SystemRoleDo> getRolesMap() {
        return rolesMap;
    }

    public void setRolesMap(LinkedHashMap<String, SystemRoleDo> rolesMap) {
        this.rolesMap = rolesMap;
    }

    public LinkedHashMap<String, SystemFunctionalityDo> getFunctionalitysMap() {
        return functionalitysMap;
    }

    public void setFunctionalitysMap(LinkedHashMap<String, SystemFunctionalityDo> functionalitysMap) {
        this.functionalitysMap = functionalitysMap;
    }

    public List<SystemFunctionalityDo> getMenuFunctionalitys() {
        return menuFunctionalitys;
    }

    public void setMenuFunctionalitys(List<SystemFunctionalityDo> menuFunctionalitys) {
        this.menuFunctionalitys = menuFunctionalitys;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastModPassTime() {
        return lastModPassTime;
    }

    public void setLastModPassTime(Date lastModPassTime) {
        this.lastModPassTime = lastModPassTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    static public UserContext getCurrentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            UserContext userContext = (UserContext) session.getAttribute(UserContext.class.getName());
            if (userContext != null) {
                return userContext;
            }
        }
        UserContext uc=new UserContext();
        uc.setUserId("t1");
        //throw new PlatformException("not-login-in");
        return uc;
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
        map.put("token", this.token);
        map.put("account", this.account);
        map.put("name", this.name);
        map.put("avator", this.avator);
        if (this.locked) {
            map.put("locked", true);
        }

        if (this.firstLogin) {
            map.put("firstLogin", true);
        } else {
            map.put("lastLoginTime", this.lastLoginTime);
            map.put("lastLoginIp", this.lastLoginIp);
        }

        map.put("lastModPassTime", this.lastModPassTime);
        map.put("createTime", this.createTime);
        map.put("loginIp", this.currentLoginIp);
        return map;
    }
}
