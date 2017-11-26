package com.yizhuoyan.shidao.questionhub.support.dao;

import com.yizhuoyan.shidao.common.dao.support.SingleTableDaoSupport;
import com.yizhuoyan.shidao.questionhub.dao.KnowledgePointDao;
import com.yizhuoyan.shidao.questionhub.entity.KnowledgePointModel;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Repository
public class KnowledgePointDaoImpl extends SingleTableDaoSupport<KnowledgePointModel> implements KnowledgePointDao {

    public KnowledgePointDaoImpl() {
        super("qst_knowledge_point", "id,code,name,remark,childrenAmount,parent_id");
    }

    @Override
    protected KnowledgePointModel row2obj(ResultSet rs) throws Exception {
        KnowledgePointModel m=new KnowledgePointModel();
        int i=1;
        m.setChildrenAmount(rs.getInt(i++));
        m.setCode(rs.getString(i++));
        m.setId(rs.getString(i++));
        m.setName(rs.getString(i++));
        m.setParentId(rs.getString(i++));
        m.setRemark(rs.getString(i++));
        return m;
    }

    @Override
    protected void obj2row(PreparedStatement ps, KnowledgePointModel e) throws Exception {

        int i=1;
        ps.setInt(i++,e.getChildrenAmount());
        ps.setString(i++,e.getCode());
        ps.setString(i++,e.getId());
        ps.setString(i++,e.getName());
        ps.setString(i++,e.getParentId());
        ps.setString(i++,e.getRemark());
    }
}
