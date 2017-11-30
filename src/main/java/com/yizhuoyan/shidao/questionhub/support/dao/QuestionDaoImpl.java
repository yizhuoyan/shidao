package com.yizhuoyan.shidao.questionhub.support.dao;

import com.yizhuoyan.shidao.common.dao.support.JDBCUtil;
import com.yizhuoyan.shidao.common.dao.support.SingleTableDaoSupport;
import com.yizhuoyan.shidao.questionhub.dao.QuestionDao;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Repository
public class QuestionDaoImpl extends SingleTableDaoSupport<QuestionModel> implements QuestionDao {

    public QuestionDaoImpl() {
        super("qst_question", "id,content,supply_content,difficult,create_user_id,question_kind_id,create_time,update_time,answer,answer_explain",true);
    }




    @Override
    protected void obj2row(PreparedStatement ps, QuestionModel e) throws Exception {
        int i=1;
        ps.setString(i++,e.getAnswer());
        ps.setString(i++,e.getAnswerExplain());
        ps.setString(i++,e.getContent());
        ps.setTimestamp(i++,JDBCUtil.toSqlTimestamp(e.getCreateTime()));
        ps.setString(i++,e.getCreateUserId());
        ps.setInt(i++,e.getDifficult());
        ps.setString(i++,e.getId());
        ps.setString(i++,e.getQuestionKindId());
        ps.setString(i++,e.getSupplyContent());
        ps.setTimestamp(i++,JDBCUtil.toSqlTimestamp(e.getUpdateTime()));
    }
    @Override
    protected QuestionModel row2obj(ResultSet rs) throws Exception {
        QuestionModel q=new QuestionModel();
        int i=1;
        q.setAnswer(rs.getString(i++));
        q.setAnswerExplain(rs.getString(i++));
        q.setContent(rs.getString(i++));
        q.setCreateTime(JDBCUtil.toInstant(rs.getTimestamp(i++)));
        q.setCreateUserId(rs.getString(i++));
        q.setDifficult(rs.getInt(i++));
        q.setId(rs.getString(i++));
        q.setQuestionKindId(rs.getString(i++));
        q.setSupplyContent(rs.getString(i++));
        q.setUpdateTime(JDBCUtil.toInstant(rs.getTimestamp(i++)));
        return q;
    }
}
