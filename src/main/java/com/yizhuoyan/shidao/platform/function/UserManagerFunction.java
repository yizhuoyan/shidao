package com.yizhuoyan.shidao.platform.function;

import com.yizhuoyan.common.dto.PaginationQueryResult;
import com.yizhuoyan.shidao.platform.po.SysUserPo;
import com.yizhuoyan.shidao.entity.SystemFunctionalityEntity;
import com.yizhuoyan.shidao.entity.SystemRoleEntity;
import com.yizhuoyan.shidao.entity.SystemUserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理员功能
 *
 * @author ben
 */

@Transactional
public interface UserManagerFunction {

   SystemUserEntity addUser(SysUserPo po) throws Exception;

   SystemUserEntity modUser(String id, SysUserPo po) throws Exception;
   void deleteUser(String id)throws  Exception;

   SystemUserEntity checkUserDetail(String id) throws Exception;

   List<SystemRoleEntity> listRoleOfUser(String userId) throws Exception;

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
 * @param roleId
 * @throws Exception
 */
void revokeRole(String userId, String roleId) throws Exception;

   List<SystemFunctionalityEntity> glanceOwnFunctionalitys(String userId) throws Exception;

void resetUserPassword(String userId) throws Exception;

void lockUser(String userId) throws Exception;

void unlockUser(String userId) throws Exception;

   PaginationQueryResult<SystemUserEntity> queryUser(String key, int pageNo, int pageSize) throws Exception;
}