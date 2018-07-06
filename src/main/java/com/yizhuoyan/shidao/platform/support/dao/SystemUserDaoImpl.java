package com.yizhuoyan.shidao.platform.support.dao;

import com.yizhuoyan.common.dao.support.JDBCUtil;
import com.yizhuoyan.common.dao.support.SingleTableDaoSupport;
import com.yizhuoyan.common.dao.support.Sql;
import com.yizhuoyan.shidao.platform.dao.SystemUserDao;
import com.yizhuoyan.shidao.entity.SystemUserEntity;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * 用户dao
 *
 * @author Administrator
 */
@Repository
public class SystemUserDaoImpl extends SingleTableDaoSupport<SystemUserEntity> implements SystemUserDao {
public SystemUserDaoImpl(){
    super("sys_user", "id,account,name,password,avatar,createTime,lastModPassTime,lastLoginTime,remark,status,loginIp,createUser_id", true);
}



public void joinOnRole(String userId, String roleId) throws Exception{
  this.executeInsert("rel_user_role(user_id,role_id)", userId, roleId);
}

public void disjoinOnRole(String userId, String roleId) throws Exception{
  this.executeDelete("rel_user_role(user_id,role_id)", userId, roleId);
}

public void disjoinOnRole(String userId) throws Exception{
  this.executeDelete("rel_user_role(user_id)", userId);
}

    public List<SystemUserEntity> selectByRoleId(String roleId) throws Exception {
  Sql sql = Sql.select(generateSelectColumns("u"))
                .from(tableName, " u")
                .join("rel_user_role rel on rel.user_id=u.id")
                .join("sys_role r on r.id= rel.role_id")
                .where("u.status!=? and r.id=?");
        return this.executesQuerySql(sql, this::row2obj, SystemUserEntity.STATUS_DELETED, roleId);
}

    @Override
    public void obj2row(PreparedStatement ps, SystemUserEntity m) throws Exception {
        int i = 1;
        ps.setString(i++, m.getAccount());
        ps.setString(i++, m.getAvatar());
        ps.setTimestamp(i++, JDBCUtil.toTimestamp(m.getCreateTime()));
        ps.setString(i++, m.getCreateUserId());
        ps.setString(i++, m.getId());
        ps.setTimestamp(i++, JDBCUtil.toTimestamp(m.getLastLoginTime()));
        ps.setTimestamp(i++, JDBCUtil.toTimestamp(m.getLastModifyPasswordTime()));
        ps.setString(i++, m.getLoginIp());
        ps.setString(i++, m.getName());
        ps.setString(i++, m.getPassword());
        ps.setString(i++, m.getRemark());
        ps.setInt(i++, m.getStatus());
    }

@Override
protected SystemUserEntity row2obj(ResultSet rs) throws Exception {
    SystemUserEntity m = new SystemUserEntity();
  int i = 1;
  m.setAccount(rs.getString(i++));
    m.setAvatar(rs.getString(i++));
    m.setCreateTime(toInstant(rs.getTimestamp(i++)));
    m.setCreateUserId(rs.getString(i++));
  m.setId(rs.getString(i++));
    m.setLastLoginTime(toInstant(rs.getTimestamp(i++)));
    m.setLastModifyPasswordTime(toInstant(rs.getTimestamp(i++)));
  m.setLoginIp(rs.getString(i++));
  m.setName(rs.getString(i++));
  m.setPassword(rs.getString(i++));
  m.setRemark(rs.getString(i++));
  m.setStatus(rs.getInt(i++));

  return m;
}
}
