/**
 * shidao
 * SupperManagerFunction.java
 * 2015年10月31日
 */
package com.yizhuoyan.shidao.platform.function;

import com.yizhuoyan.shidao.platform.po.SystemFunctionalityPo;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityModel;
import com.yizhuoyan.shidao.platform.entity.SystemRoleModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 超级管理员功能
 *
 * @author root@yizhuoyan.com
 */
@Transactional
public interface SystemFuncationalityFunction {




SystemFunctionalityModel addSystemFunctionality(SystemFunctionalityPo po) throws Exception;

void deleteSystemFunctionality(String id) throws Exception;

SystemFunctionalityModel modifySystemFunctionality(String id, SystemFunctionalityPo po) throws Exception;

void enableSystemFunctionality(String id) throws Exception;

void disableSystemFunctionality(String id) throws Exception;

SystemFunctionalityModel checkSysFunctionalityDetail(String id) throws Exception;

/**
 * 模糊查询功能模块,包括已停用的
 *
 * @param key
 * @return
 * @throws Exception
 */
List<SystemFunctionalityModel> listSystemFunctionality(String key) throws Exception;

List<SystemRoleModel> listRoleOfSystemFunctionality(String functionalityId) throws Exception;




}
