/**
 * shidao
 * SysRoleDao.java
 * 2016年1月15日
 */
package com.yizhuoyan.shidao.platform.dao;

import com.yizhuoyan.shidao.common.dao.CRUDDao;
import com.yizhuoyan.shidao.platform.entity.SystemRoleDo;

import java.util.List;


/**
 * @author root@yizhuoyan.com
 */
public interface SystemRoleDao extends CRUDDao<SystemRoleDo>{

List<SystemRoleDo> selectByUserId(String userId) throws Exception;

List<SystemRoleDo> selectByFunctionalityId(String functionalityId) throws Exception;

List<SystemRoleDo> selectByUserIdAndFunctionalityId(String userId, String functionalityId) throws Exception;


void joinOnFunctionality(String roleId, String functionalityId) throws Exception;

void disjoinOnFunctionality(String roleId, String functionalityId) throws Exception;

void disjoinOnFunctionality(String roleId) throws Exception;

void disjoinOnUser(String roleId)throws  Exception;


}