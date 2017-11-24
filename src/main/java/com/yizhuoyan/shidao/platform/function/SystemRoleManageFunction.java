/**
 * shidao
 * SupperManagerFunction.java
 * 2015年10月31日
 */
package com.yizhuoyan.shidao.platform.function;

import com.yizhuoyan.shidao.platform.po.SysRolePo;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityModel;
import com.yizhuoyan.shidao.platform.entity.SystemRoleModel;
import com.yizhuoyan.shidao.platform.entity.SystemUserModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 超级管理员功能
 *
 * @author root@yizhuoyan.com
 */
@Transactional
public interface SystemRoleManageFunction {



SystemRoleModel addRole(SysRolePo po) throws Exception;

SystemRoleModel modifyRole(String id, SysRolePo po) throws Exception;

List<SystemFunctionalityModel> listSystemFunctionalityOfRole(String roleId) throws Exception;

List<SystemUserModel> listUserOfRole(String roleId) throws Exception;

void grantSystemFunctionalitysToRole(String roleId, String... functionalityIds) throws Exception;

SystemRoleModel checkRoleDetail(String id) throws Exception;

List<SystemRoleModel> listRole(String key) throws Exception;
    void deleteSystemRole(String id)throws Exception;




}
