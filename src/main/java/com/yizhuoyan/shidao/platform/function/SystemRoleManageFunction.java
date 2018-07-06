/**
 * shidao
 * SupperManagerFunction.java
 * 2015年10月31日
 */
package com.yizhuoyan.shidao.platform.function;

import com.yizhuoyan.shidao.platform.po.SysRolePo;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityDo;
import com.yizhuoyan.shidao.platform.entity.SystemRoleDo;
import com.yizhuoyan.shidao.platform.entity.SystemUserDo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 超级管理员功能
 *
 * @author root@yizhuoyan.com
 */
@Transactional
public interface SystemRoleManageFunction {



SystemRoleDo addRole(SysRolePo po) throws Exception;

SystemRoleDo modifyRole(String id, SysRolePo po) throws Exception;

List<SystemFunctionalityDo> listSystemFunctionalityOfRole(String roleId) throws Exception;

List<SystemUserDo> listUserOfRole(String roleId) throws Exception;

void grantSystemFunctionalitysToRole(String roleId, String... functionalityIds) throws Exception;

SystemRoleDo checkRoleDetail(String id) throws Exception;

List<SystemRoleDo> listRole(String key) throws Exception;
    void deleteSystemRole(String id)throws Exception;




}
