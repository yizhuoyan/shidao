/**
 * shidao
 * SysRoleDao.java
 * 2015年10月30日
 */
package com.yizhuoyan.shidao.platform.support.dao;

import com.yizhuoyan.common.dao.support.SingleTableDaoSupport;
import com.yizhuoyan.common.dao.support.Sql;
import com.yizhuoyan.shidao.platform.dao.SystemFunctionalityDao;
import com.yizhuoyan.shidao.entity.SystemFunctionalityEntity;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


/**
 * 用户功能模块dao
 *
 * @author root@yizhuoyan.com
 */
@Repository
public class SystemFunctionalityDaoImpl extends SingleTableDaoSupport<SystemFunctionalityEntity> implements SystemFunctionalityDao {
    public SystemFunctionalityDaoImpl() {
        super("sys_functionality", "id,code,name,url,orderCode,parent_id,kind,status,remark,createTime,createUser_Id");
    }


    public void updateCodeCascade(String code, String newCode) throws Exception {
        Sql sql = Sql.updateTable(this.tableName)
                .set("code=CONCAT(?,SUBSTRING(code,LENGTH(?)+1))")
                .where("code like ?");
        executeUpdateSql(sql, newCode, code, code + "%");

    }

    @Override
    public void disjoinOnRole(String functionalityId) throws Exception {
        executeDelete("rel_role_functionality(functionality_id)", functionalityId);
    }

    @Override
    public List<SystemFunctionalityEntity> selectDescendantByCode(String code) throws Exception {
        Sql sql = Sql.select(this.generateSelectColumns())
                .from(this.tableName)
                .where("code like ?")
                .orderBy("code desc");
        return executesQuerySql(sql, this::row2obj, code + "/%");
    }

    public List<SystemFunctionalityEntity> selectByUserId(String userId) throws Exception {
        Sql sql = Sql.selectDistinct(this.generateSelectColumns("f"))
                .from("sys_functionality f")
                .join("rel_role_functionality rof on f.id=rof.functionality_id")
                .join("sys_role r on r.id=rof.role_id")
                .join("rel_user_role uor on r.id=uor.role_id")
                .join("sys_user u on u.id=uor.user_id")
                .where("u.id=?")
                .orderBy("f.orderCode");

        return executesQuerySql(sql, this::row2obj, userId);
    }

    public List<SystemFunctionalityEntity> selectByStatusAndUserId(int status, String userId) throws Exception {
        Sql sql = Sql.selectDistinct(this.generateSelectColumns("f"))
                .from("sys_functionality f")
                .join("rel_role_functionality rof on f.id=rof.functionality_id")
                .join("sys_role r on r.id=rof.role_id")
                .join("rel_user_role uor on r.id=uor.role_id")
                .join("sys_user u on u.id=uor.user_id")
                //不是按钮链接
                .where("f.status=? and u.id=?")
                .orderBy("f.orderCode");

        return executesQuerySql(sql, this::row2obj, status, userId);
    }

    public List<SystemFunctionalityEntity> selectByRoleId(String roleId) throws Exception {
        Sql sql = Sql.selectDistinct(this.generateSelectColumns("f"))
                .from("sys_functionality f")
                .join("rel_role_functionality rof on f.id=rof.functionality_id")
                .join("sys_role r on rof.role_id=r.id")
                .where("r.id=?")
                .orderBy("f.orderCode");
        return executesQuerySql(sql, this::row2obj, roleId);
    }

    @Override
    public void obj2row(PreparedStatement ps, SystemFunctionalityEntity m) throws Exception {
        int i = 1;
        ps.setString(i++, m.getId());
        ps.setString(i++, m.getCode());
        ps.setString(i++, m.getName());
        ps.setString(i++, m.getUrl());
        ps.setString(i++, m.getOrderCode());
        ps.setString(i++, m.getParentId());
        ps.setInt(i++, m.getKind());
        ps.setInt(i++, m.getStatus());
        ps.setString(i++, m.getRemark());
        ps.setTimestamp(i, toTimestamp(m.getCreateTime()));
        ps.setString(i++, m.getCreateUserId());
    }

    @Override
    protected SystemFunctionalityEntity row2obj(ResultSet rs) throws Exception {
        SystemFunctionalityEntity m = new SystemFunctionalityEntity();
        int i = 1;
        m.setId(rs.getString(i++));
        m.setCode(rs.getString(i++));
        m.setName(rs.getString(i++));
        m.setUrl(rs.getString(i++));
        m.setOrderCode(rs.getString(i++));
        m.setParentId(rs.getString(i++));
        m.setKind(rs.getInt(i++));
        m.setStatus(rs.getInt(i++));
        m.setRemark(rs.getString(i++));
        m.setCreateTime(toInstant(rs.getTimestamp(i++)));
        m.setCreateUserId(rs.getString(i++));
        return m;
    }

}
