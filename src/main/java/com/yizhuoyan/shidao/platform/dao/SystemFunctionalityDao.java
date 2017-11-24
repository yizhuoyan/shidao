/**
 * shidao
 * SysFunctionalityDao1.java
 * 2016年1月15日
 */
package com.yizhuoyan.shidao.platform.dao;

import com.yizhuoyan.shidao.common.dao.CRUDDao;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityModel;

import java.util.List;

/**
 * @author root@yizhuoyan.com
 */
public interface SystemFunctionalityDao extends CRUDDao<SystemFunctionalityModel>{

void updateCodeCascade(String code, String newCode) throws Exception;

List<SystemFunctionalityModel> selectByUserId(String userId) throws Exception;

    /**
     * 查找包括自己在内的所有后代
     * @param code
     * @return
     * @throws Exception
     */
    List<SystemFunctionalityModel> selectDescendantByCode(String code) throws Exception;

List<SystemFunctionalityModel> selectByStatusAndUserId(int status, String userId) throws Exception;

List<SystemFunctionalityModel> selectByRoleId(String roleId) throws Exception;

    void disjoinOnRole(String functionalityId) throws Exception;

}