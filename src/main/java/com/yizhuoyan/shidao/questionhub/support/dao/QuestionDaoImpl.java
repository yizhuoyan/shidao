package com.yizhuoyan.shidao.questionhub.support.dao;

import com.yizhuoyan.shidao.common.dao.support.JDBCUtil;
import com.yizhuoyan.shidao.common.dao.support.SingleTableDaoSupport;
import com.yizhuoyan.shidao.questionhub.dao.QuestionDao;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public class QuestionDaoImpl extends SingleTableDaoSupport<QuestionModel> implements QuestionDao {

    public QuestionDaoImpl() {
        super("qst_question", "id,content,difficult,id_user_creater,kind,time_create,time_last_modify,answer,answer_explain,options,id_question_belong,children_Amount",true);
    }


    @Override
    protected QuestionModel row2obj(ResultSet rs) throws Exception {
        QuestionModel q=new QuestionModel();
        int i=1;
        q.setAnswer(rs.getString(i++));
        q.setAnswerExplain(rs.getString(i++));
        q.setBelongQuestionId(rs.getString(i++));
        q.setChildrenAmount(rs.getInt(i++));
        q.setContent(rs.getString(i++));
        q.setCreateTime(JDBCUtil.toInstant(rs.getTimestamp(i++)));
        q.setCreateUserId(rs.getString(i++));
        q.setDifficult(rs.getInt(i++));
        q.setId(rs.getString(i++));
        q.setKindId(rs.getString(i++));
        q.setOptions(rs.getString(i++));
        q.setUpdateTime(JDBCUtil.toInstant(rs.getTimestamp(i++)));
        return q;
    }

    @Override
    protected void obj2row(PreparedStatement ps, QuestionModel e) throws Exception {
        int i=1;
        ps.setString(i++,e.getAnswer());
        ps.setString(i++,e.getAnswerExplain());
        ps.setString(i++,e.getBelongQuestionId());
        ps.setInt(i++,e.getChildrenAmount());
        ps.setString(i++,e.getContent());
        ps.setTimestamp(i++,JDBCUtil.toSqlTimestamp(e.getCreateTime()));
        ps.setString(i++,e.getCreateUserId());
        ps.setInt(i++,e.getDifficult());
        ps.setString(i++,e.getId());
        ps.setString(i++,e.getKindId());
        ps.setString(i++,e.getOptions());
        ps.setTimestamp(i++,JDBCUtil.toSqlTimestamp(e.getUpdateTime()));

    }
}
