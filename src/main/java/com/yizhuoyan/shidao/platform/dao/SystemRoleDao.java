/**
 * shidao
 * SysRoleDao.java
 * 2016年1月15日
 */
package com.yizhuoyan.shidao.platform.dao;

import com.yizhuoyan.common.dao.CRUDDao;
import com.yizhuoyan.shidao.entity.SystemRoleEntity;

import java.util.List;


/**
 * @author root@yizhuoyan.com
 */
public interface SystemRoleDao extends CRUDDao<SystemRoleEntity> {

    List<SystemRoleEntity> selectByUserId(String userId) throws Exception;

    List<SystemRoleEntity> selectByFunctionalityId(String functionalityId) throws Exception;

    List<SystemRoleEntity> selectByUserIdAndFunctionalityId(String userId, String functionalityId) throws Exception;


void joinOnFunctionality(String roleId, String functionalityId) throws Exception;

void disjoinOnFunctionality(String roleId, String functionalityId) throws Exception;

void disjoinOnFunctionality(String roleId) throws Exception;

void disjoinOnUser(String roleId)throws  Exception;


}