/**
 * shidao
 * SysFunctionalityDao1.java
 * 2016年1月15日
 */
package com.yizhuoyan.shidao.platform.dao;

import com.yizhuoyan.shidao.common.dao.CRUDDao;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityDo;

import java.util.List;

/**
 * @author root@yizhuoyan.com
 */
public interface SystemFunctionalityDao extends CRUDDao<SystemFunctionalityDo>{

void updateCodeCascade(String code, String newCode) throws Exception;

List<SystemFunctionalityDo> selectByUserId(String userId) throws Exception;

    /**
     * 查找包括自己在内的所有后代
     * @param code
     * @return
     * @throws Exception
     */
    List<SystemFunctionalityDo> selectDescendantByCode(String code) throws Exception;

List<SystemFunctionalityDo> selectByStatusAndUserId(int status, String userId) throws Exception;

List<SystemFunctionalityDo> selectByRoleId(String roleId) throws Exception;

    void disjoinOnRole(String functionalityId) throws Exception;

}