/**
 * shidao
 * SysUserDao.java
 * 2016年1月15日
 */
package com.yizhuoyan.shidao.platform.dao;

import com.yizhuoyan.shidao.common.dao.CRUDDao;
import com.yizhuoyan.shidao.platform.entity.SystemUserModel;

import java.sql.Connection;
import java.util.List;

/**
 * @author root@yizhuoyan.com
 */
public interface SystemUserDao extends CRUDDao<SystemUserModel> {

List<SystemUserModel> selectByRoleId(String roleId) throws Exception;

void joinOnRole(String userId, String roleId) throws Exception;

void disjoinOnRole(String userId, String roleId) throws Exception;

void disjoinOnRole(String userId) throws Exception;

Connection getConnection()throws  Exception;
}