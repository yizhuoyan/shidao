/**
 * shidao
 * SysConfigDao.java
 * 2015年10月31日
 */
package com.yizhuoyan.shidao.platform.support.dao;

import com.yizhuoyan.common.dao.support.SingleTableDaoSupport;
import com.yizhuoyan.shidao.platform.dao.SystemConfigDao;
import com.yizhuoyan.shidao.entity.SystemConfigEntity;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author root@yizhuoyan.com
 */
@Repository
public class SystemConfigDaoImpl extends SingleTableDaoSupport<SystemConfigEntity> implements SystemConfigDao {

public SystemConfigDaoImpl(){
    super("sys_config", "id,name,value,remark,status,createTime,createUser_id");
}

    public void obj2row(PreparedStatement ps, SystemConfigEntity m) throws Exception {
  int i = 1;
  ps.setString(i++, m.getId());
  ps.setString(i++, m.getName());
  ps.setString(i++, m.getValue());
  ps.setString(i++, m.getRemark());
  ps.setInt(i++, m.getStatus());
        ps.setTimestamp(i++, toTimestamp(m.getCreateTime()));
        ps.setString(i++, m.getCreateUserId());
}


    public SystemConfigEntity row2obj(ResultSet rs) throws Exception {
        SystemConfigEntity m = new SystemConfigEntity();
  int i = 1;
  m.setId(rs.getString(i++));
  m.setName(rs.getString(i++));
  m.setValue(rs.getString(i++));
  m.setRemark(rs.getString(i++));
  m.setStatus(rs.getInt(i++));
        m.setCreateTime(toInstant(rs.getTimestamp(i++)));
        m.setCreateUserId(rs.getString(i++));
  return m;
}


}
