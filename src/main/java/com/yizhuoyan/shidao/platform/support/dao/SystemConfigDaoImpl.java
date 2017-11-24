/**
 * shidao
 * SysConfigDao.java
 * 2015年10月31日
 */
package com.yizhuoyan.shidao.platform.support.dao;

import com.yizhuoyan.shidao.common.dao.support.SingleTableDaoSupport;
import com.yizhuoyan.shidao.platform.dao.SystemConfigDao;
import com.yizhuoyan.shidao.platform.entity.SystemConfigModel;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author root@yizhuoyan.com
 */
@Repository
public class SystemConfigDaoImpl extends SingleTableDaoSupport<SystemConfigModel> implements SystemConfigDao{

public SystemConfigDaoImpl(){
  super("sys_config", "id,name,value,remark,status");
}

public void obj2row(PreparedStatement ps, SystemConfigModel m) throws Exception{
  int i = 1;
  ps.setString(i++, m.getId());
  ps.setString(i++, m.getName());
  ps.setString(i++, m.getValue());
  ps.setString(i++, m.getRemark());
  ps.setInt(i++, m.getStatus());
}


public SystemConfigModel row2obj(ResultSet rs) throws Exception{
  SystemConfigModel m = new SystemConfigModel();
  int i = 1;
  m.setId(rs.getString(i++));
  m.setName(rs.getString(i++));
  m.setValue(rs.getString(i++));
  m.setRemark(rs.getString(i++));
  m.setStatus(rs.getInt(i++));
  return m;
}


}
