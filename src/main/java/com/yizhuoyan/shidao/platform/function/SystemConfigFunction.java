/**
 * shidao
 * SupperManagerFunction.java
 * 2015年10月31日
 */
package com.yizhuoyan.shidao.platform.function;

import com.yizhuoyan.shidao.platform.po.SystemConfigPo;
import com.yizhuoyan.shidao.platform.entity.SystemConfigDo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 超级管理员功能
 *
 * @author root@yizhuoyan.com
 */
@Transactional
public interface SystemConfigFunction {


/**
 * 获取系统配置列表
 *
 * @param key 查询关键字
 * @return
 * @throws Exception
 */
List<SystemConfigDo> listSystemConfig(String key) throws Exception;

/**
 * 查看系统配置项目详情
 *
 * @param id
 * @return
 * @throws Exception
 */
SystemConfigDo checkSystemConfigDetail(String id) throws Exception;

/**
 * 新增系统配置项
 *
 * @param po 信息
 * @throws Exception
 */
SystemConfigDo addSystemConfig(SystemConfigPo po) throws Exception;

/**
 * 修改某个系统配置项
 *
 * @param po 修改信息
 * @throws Exception
 */
SystemConfigDo modifySystemConfig(String id, SystemConfigPo po) throws Exception;





}
