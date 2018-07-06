package com.yizhuoyan.shidao.usercommon.support.function;

import com.yizhuoyan.common.exception.ParameterException;
import com.yizhuoyan.common.util.KeyValueMap;
import com.yizhuoyan.common.util.PlatformUtil;
import com.yizhuoyan.shidao.platform.dao.SystemFunctionalityDao;
import com.yizhuoyan.shidao.platform.dao.SystemUserDao;
import com.yizhuoyan.shidao.platform.support.function.AbstractFunctionSupport;
import com.yizhuoyan.shidao.usercommon.dto.UserContext;
import com.yizhuoyan.shidao.entity.SystemFunctionalityEntity;
import com.yizhuoyan.shidao.entity.SystemUserEntity;
import com.yizhuoyan.shidao.usercommon.function.UserCommonFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.yizhuoyan.common.util.AssertThrowUtil.*;
import static com.yizhuoyan.common.util.validatation.ParameterValidator.$;
import static com.yizhuoyan.common.util.validatation.ParameterValidator.checkEquals;

@Service
public class UserCommonFunctionImpl extends AbstractFunctionSupport implements
        UserCommonFunction {
    @Autowired
    protected SystemFunctionalityDao functionalityDao;
    @Autowired
    protected SystemUserDao userDao;

    public UserContext login(String account, String password) throws Exception {

        account = $("account",account);
        password = $( "password",password);

        SystemUserEntity u = this.userDao.select("account", account);
        assertNotNull("not-exist.account", u, account);
        assertEquals("incorrect.password", password, u.getPassword());
        assertFalse("login-locked.account", u.isLocked(), account);
        // 根据用户加载所有拥有的功能模块
        List<SystemFunctionalityEntity> functionalitys = functionalityDao.selectByStatusAndUserId(SystemFunctionalityEntity.STATUS_ACTIVATE, u
                .getId());
        // 无任何功能
        assertNotEmpty("login-noFunctionalitys.account", functionalitys,account);

        KeyValueMap updateMap = KeyValueMap.of(4);

        // 创建用户上下文
        UserContext uc = new UserContext();
        // 设置用户信息
        uc.setUserId(u.getId());
        uc.setAccount(u.getAccount());
        uc.setAvatar(u.getAvatar());
        uc.setCreateTime(u.getCreateTime());
        uc.setFirstLogin(u.getLastLoginTime() == null);
        uc.setLastModifyPasswordTime(u.getLastModifyPasswordTime());
        uc.setName(u.getName());
        uc.setRemark(u.getRemark());
        uc.setLastLoginIp(u.getLoginIp());
        uc.setLocked(u.getStatus() == SystemUserEntity.STATUS_LOCKED);
        // 设置用户功能模块
        uc.setFunctionalitysMap(PlatformUtil.list2map(functionalitys, SystemFunctionalityEntity::getId));
        // 设置用户菜单
        uc.setMenuFunctionalitys(functionalitys
                .stream()
                //过滤掉按钮和链接即可
                .filter(f -> f.getKind() != SystemFunctionalityEntity.KIND_BUTTON_AND_LINK)
                .collect(Collectors.toList()));
        // 更新最后登录时间
        updateMap.put("lastLoginTime", new Date());

        userDao.update(u.getId(), updateMap);

        return uc;
    }


    public void modifyPassword(String userId, String oldPassword,
                               String newPassword, String newPasswordConfirm) throws Exception {

        List<String> errors = new ArrayList<>();
        userId = $("userId",userId,errors);
        oldPassword = $("oldPassword",oldPassword, errors);
        newPassword = $("newPassword",newPassword, errors);
        newPasswordConfirm = $("newPasswordConfirm",newPasswordConfirm,errors);
        // 两次新密码必须一致
        checkEquals(newPasswordConfirm, newPassword, errors);

        if(errors.size()>0){
            throw new ParameterException(errors);
        }

        //获取账户
        SystemUserEntity user = this.userDao.select("id", userId);
        // 账户必须存在
        assertNotNull("not-exist.userId", user,userId);
        // 旧密码必须正确
        assertEquals("incorrect.oldPassword", user.getPassword(), oldPassword);
        // 新旧密码不能一致
        assertNotEquals("must-not-equals.newPassword.oldPassword", newPassword, user.getPassword());

        this.userDao.update(userId, "password", newPassword);
    }

    public void firstLoginModifyPassword(String id, String newPassword, String newPasswordConfirm) throws Exception {

        id = $("id", id);
        newPassword = $("newPassword", newPassword);
        // 两次新密码必须一致
        assertEquals("must-equals.newPassword.newPasswordConfirm", newPasswordConfirm, newPassword);
        //获取账户
        SystemUserEntity user = this.userDao.select("id", id);
        // 账户必须存在
        assertNotNull("not-exist.id", user,id);
        // 新旧密码不能一致
        assertNotEquals("must-not-equals.newPassword.oldPassword", newPassword, user.getPassword());
        this.userDao.update(id, "password", newPassword);
    }
}
