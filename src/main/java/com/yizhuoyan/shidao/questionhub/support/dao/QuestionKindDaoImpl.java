package com.yizhuoyan.shidao.questionhub.support.dao;

import com.yizhuoyan.shidao.common.dao.support.SingleTableDaoSupport;
import com.yizhuoyan.shidao.questionhub.dao.QuestionKindDao;
import com.yizhuoyan.shidao.questionhub.entity.QuestionKindModel;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Repository
public class QuestionKindDaoImpl extends SingleTableDaoSupport<QuestionKindModel> implements QuestionKindDao {

    public QuestionKindDaoImpl() {
        super("qst_question_kind", "id,name,parse_class_name,introduction,remark");
    }

    @Override
    protected QuestionKindModel row2obj(ResultSet rs) throws Exception {
        QuestionKindModel m=new QuestionKindModel();
        int i=1;
        m.setId(rs.getString(i++));
        m.setName(rs.getString(i++));
        m.setParserClassName(rs.getString(i++));
        m.setIntroduction(rs.getString(i++));
        m.setRemark(rs.getString(i++));
        return m;
    }


    @Override
    protected void obj2row(PreparedStatement ps, QuestionKindModel e) throws Exception {
        int i=1;
        ps.setString(i++,e.getId());
        ps.setString(i++,e.getName());
        ps.setString(i++,e.getParserClassName());
        ps.setString(i++,e.getIntroduction());
        ps.setString(i++,e.getRemark());

    }
}
