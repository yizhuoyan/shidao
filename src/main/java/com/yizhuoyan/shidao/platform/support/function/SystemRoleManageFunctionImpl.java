/*
 * shidao
 * SupperManagerFunctionImpl.java
 * 2015年10月31日	
 */
package com.yizhuoyan.shidao.platform.support.function;

import com.yizhuoyan.shidao.common.dao.support.SelectLikePo;
import com.yizhuoyan.shidao.common.util.KeyValueMap;
import com.yizhuoyan.shidao.common.validatation.ParameterObjectValidator;
import com.yizhuoyan.shidao.platform.po.SysRolePo;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityModel;
import com.yizhuoyan.shidao.platform.entity.SystemRoleModel;
import com.yizhuoyan.shidao.platform.entity.SystemUserModel;
import com.yizhuoyan.shidao.platform.function.SystemRoleManageFunction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.yizhuoyan.shidao.common.util.AssertThrowUtil.*;
import static com.yizhuoyan.shidao.common.validatation.ParameterValidator.$;
import static com.yizhuoyan.shidao.common.util.PlatformUtil.*;

/**
 * @author root@yizhuoyan.com
 */
@Service
public class SystemRoleManageFunctionImpl extends AbstractFunctionSupport implements SystemRoleManageFunction {


    @Override
    public SystemRoleModel addRole(SysRolePo po) throws Exception {
        ParameterObjectValidator.throwIfFail(po);
        // 代号不能重复
        String code = po.getCode();
        assertFalse("already-exist", roleDao.exist("code", code), code);
        SystemRoleModel model = new SystemRoleModel();
        model.setId(uuid());
        model.setCode(code);
        model.setName(po.getName());
        model.setRemark(po.getRemark());

        this.roleDao.insert(model);
        return model;
    }


    @Override
    public SystemRoleModel checkRoleDetail(String id) throws Exception {
        id = $("id", id);
        SystemRoleModel model = this.roleDao.select("id", id);
        return model;
    }


    @Override
    public SystemRoleModel modifyRole(String id, SysRolePo po) throws Exception {
        id = $("id", id);
        ParameterObjectValidator.throwIfFail(po);
        // 旧数据
        SystemRoleModel old = this.roleDao.select("id", id);
        assertNotNull("not-exist.id", old, id);

        KeyValueMap needUpdateMap = new KeyValueMap(4);
        // 1.代号
        String newCode = po.getCode();
        if (!old.getCode().equals(newCode)) {
            //不能重复
            assertFalse("already-exist.code", functionalityDao.exist("code", newCode), newCode);
            old.setCode(newCode);
            needUpdateMap.put("code", newCode);
        }
        //2.名称
        String newName = po.getName();
        if (!po.getName().equals(newName)) {
            old.setName(newName);
            needUpdateMap.put("name", newName);
        }
        //3.备注
        String newRemark = po.getRemark();

        if (!Objects.equals(newRemark, po.getRemark())) {

            old.setRemark(newRemark);
            needUpdateMap.put("remark", newRemark);
        }

        this.roleDao.update(id, needUpdateMap);
        return old;
    }


    @Override
    public List<SystemRoleModel> listRole(String key)
            throws Exception {
        key = trim(key);
        List<SystemRoleModel> list = this.roleDao.selectsByLike(
                SelectLikePo.of("code,name,remark",key)
                        .setOrderBy("code"));
        return list;
    }


    @Override
    public List<SystemFunctionalityModel> listSystemFunctionalityOfRole(String roleId)
            throws Exception {
        roleId=$("roleId",roleId);
        List<SystemFunctionalityModel> list = this.functionalityDao.selectByRoleId(roleId);
        return list;
    }


    @Override
    public void grantSystemFunctionalitysToRole(String roleId, String... functionalityIds)
            throws Exception {
        roleId=$("roleId",roleId);
        // 先清空
        this.roleDao.disjoinOnFunctionality(roleId);
        // 再添加
        for (String mId : functionalityIds) {
            mId = trim(mId);
            if (mId != null) {
                this.roleDao.joinOnFunctionality(roleId, mId);
            }
        }
    }

    @Override
    public List<SystemUserModel> listUserOfRole(String roleId)
            throws Exception {
        roleId=$("roleId",roleId);
        List<SystemUserModel> list = this.userDao.selectByRoleId(roleId);
        return list;
    }

    @Override
    public void deleteSystemRole(String id) throws Exception {
        id = $("id", id);
        SystemRoleModel r = roleDao.select("id", id);
        assertNotNull("not-exist.id", r,id);
        //删除角色和功能关联关系
        roleDao.disjoinOnFunctionality(id);
        //删除角色和用户关联关系
        roleDao.disjoinOnUser(id);
        //删除角色
        roleDao.delete("id", id);
    }
}
