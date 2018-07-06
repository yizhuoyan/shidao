/**
 * shidao
 * SupperManagerFunction.java
 * 2015年10月31日
 */
package com.yizhuoyan.shidao.platform.function;

import com.yizhuoyan.shidao.platform.po.SysRolePo;
import com.yizhuoyan.shidao.entity.SystemFunctionalityEntity;
import com.yizhuoyan.shidao.entity.SystemRoleEntity;
import com.yizhuoyan.shidao.entity.SystemUserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 超级管理员功能
 *
 * @author root@yizhuoyan.com
 */
@Transactional
public interface SystemRoleManageFunction {


    SystemRoleEntity addRole(SysRolePo po) throws Exception;

    SystemRoleEntity modifyRole(String id, SysRolePo po) throws Exception;

    List<SystemFunctionalityEntity> listSystemFunctionalityOfRole(String roleId) throws Exception;

    List<SystemUserEntity> listUserOfRole(String roleId) throws Exception;

void grantSystemFunctionalitysToRole(String roleId, String... functionalityIds) throws Exception;

    SystemRoleEntity checkRoleDetail(String id) throws Exception;

    List<SystemRoleEntity> listRole(String key) throws Exception;
    void deleteSystemRole(String id)throws Exception;




}
