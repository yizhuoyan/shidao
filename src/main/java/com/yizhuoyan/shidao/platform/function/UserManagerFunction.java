package com.yizhuoyan.shidao.platform.function;

import com.yizhuoyan.shidao.common.dto.PaginationQueryResult;
import com.yizhuoyan.shidao.platform.po.SysUserPo;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityModel;
import com.yizhuoyan.shidao.platform.entity.SystemRoleModel;
import com.yizhuoyan.shidao.platform.entity.SystemUserModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理员功能
 *
 * @author ben
 */

@Transactional
public interface UserManagerFunction {

SystemUserModel addUser(SysUserPo po) throws Exception;

SystemUserModel modUser(String id, SysUserPo po) throws Exception;
   void deleteUser(String id)throws  Exception;
SystemUserModel checkUserDetail(String id) throws Exception;

List<SystemRoleModel> listRoleOfUser(String userId) throws Exception;

/**
 * 添加用户所属角色
 *
 * @param userId  用户id
 * @param roleIds 角色id
 * @throws Exception
 */
void grantRoles(String userId, String... roleIds) throws Exception;

/**
 * 删除用户所属角色
 *
 * @param userId
 * @param roleIds
 * @throws Exception
 */
void revokeRole(String userId, String roleId) throws Exception;

List<SystemFunctionalityModel> glanceOwnFunctionalitys(String userId) throws Exception;

void resetUserPassword(String userId) throws Exception;

void lockUser(String userId) throws Exception;

void unlockUser(String userId) throws Exception;

PaginationQueryResult<SystemUserModel> queryUser(String key, int pageNo, int pageSize) throws Exception;
}