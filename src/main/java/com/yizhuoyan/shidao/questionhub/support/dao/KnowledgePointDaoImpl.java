package com.yizhuoyan.shidao.questionhub.support.dao;

import com.yizhuoyan.shidao.common.dao.support.SingleTableDaoSupport;
import com.yizhuoyan.shidao.common.dao.support.Sql;
import com.yizhuoyan.shidao.questionhub.dao.KnowledgePointDao;
import com.yizhuoyan.shidao.questionhub.entity.KnowledgePointDo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Repository
public class KnowledgePointDaoImpl extends SingleTableDaoSupport<KnowledgePointDo> implements KnowledgePointDao {

    public KnowledgePointDaoImpl() {
        super("qst_knowledge_point", "id,code,name,remark,children_Amount,parent_id,next_child_code",true);
    }

    @Override
    protected KnowledgePointDo row2obj(ResultSet rs) throws Exception {
        KnowledgePointDo m=new KnowledgePointDo();
        int i=1;
        m.setChildrenAmount(rs.getInt(i++));
        m.setCode(rs.getString(i++));
        m.setId(rs.getString(i++));
        m.setName(rs.getString(i++));
        m.setNextChildCode(rs.getInt(i++));
        m.setParentId(rs.getString(i++));
        m.setRemark(rs.getString(i++));
        return m;
    }

    @Override
    protected void obj2row(PreparedStatement ps, KnowledgePointDo e) throws Exception {

        int i=1;
        ps.setInt(i++,e.getChildrenAmount());
        ps.setString(i++,e.getCode());
        ps.setString(i++,e.getId());
        ps.setString(i++,e.getName());
        ps.setInt(i++,e.getNextChildCode());
        ps.setString(i++,e.getParentId());
        ps.setString(i++,e.getRemark());
    }

    @Override
    public void increasingChildAmount(String id) throws Exception {
        this.executeUpdateSql(Sql.updateTable(this.tableName)
        .set("children_Amount=children_Amount+1")
                .where("id=?"),id);
    }
    @Override
    public void decreasingChildAmount(String id)throws Exception{
        this.executeUpdateSql(Sql.updateTable(this.tableName)
                .set("children_Amount=children_Amount-1")
                .where("id=?"),id);
    }
    @Override
    public List<KnowledgePointDo> selectDescendantByCode(String code) throws Exception {

        Sql sql=Sql.select(this.generateSelectColumns())
                .from(this.tableName)
                .where("code like ?")
                .orderBy("code desc");
        return executesQuerySql(sql,this::row2obj,code+"/%");
    }

    @Override
    public int disjoinOnQuestion(String id) throws Exception{
        return executeDelete("rel_question_knowledge_point(knowledge_point_id)",id);
    }

    @Override
    public void updateCodeCascadeByCode(String code, String newCode) throws Exception {
        Sql sql = Sql.updateTable(this.tableName)
                .set("code=CONCAT(?,SUBSTRING(code,LENGTH(?)+1))")
                .where("code like ?");
        executeUpdateSql(sql, newCode, code, code+"%");
    }

    @Override
    public String selectMaxRootCode() throws Exception {
        Connection connection=this.getConnection();
        String nextCode=null;
        Sql sql= Sql.select("max(code)").from(this.tableName)
                .where("parent_id is null");
        try(PreparedStatement ps=connection.prepareStatement(sql.toString())){
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                nextCode=rs.getString(1);
            }
        }
        return nextCode;
    }

    @Override
    public boolean existName(String parentId,String name) throws Exception {
        int count=0;
        if(parentId==null){
            count=this.executeCountSql(Sql.selectCount("1").from(this.tableName)
                    .where("parent_id is null and name=?"),name);
        }else {
            count = this.executeCountSql(Sql.selectCount("1").from(this.tableName)
                    .where("parent_id=? and name=?"), parentId, name);
        }
        return count>0;
    }

    @Override
    public int selectNextChildCode(String id) throws Exception {
        Connection connection=this.getConnection();
        int nextCode=-1;
        Sql sql= Sql.select("next_child_code").from(this.tableName)
                .where("id=? for update");
        try(PreparedStatement ps=connection.prepareStatement(sql.toString())){
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                nextCode=rs.getInt(1);
            }
        }
        //update
        this.executeUpdateSql(Sql.updateTable(this.tableName)
                .set("next_child_code=next_child_code+1")
                .where("id=?"),id);
        return nextCode;
    }
}
