package com.yizhuoyan.shidao.questionhub.support.dao;

import com.yizhuoyan.common.dao.support.SingleTableDaoSupport;
import com.yizhuoyan.shidao.questionhub.dao.QuestionKindDao;
import com.yizhuoyan.shidao.entity.QuestionKindDo;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Repository
public class QuestionKindDaoImpl extends SingleTableDaoSupport<QuestionKindDo> implements QuestionKindDao {

    public QuestionKindDaoImpl() {
        super("qst_question_kind", "id,name,parser_class_name,instruction,remark");
    }

    @Override
    protected QuestionKindDo row2obj(ResultSet rs) throws Exception {
        QuestionKindDo m=new QuestionKindDo();
        int i=1;
        m.setId(rs.getString(i++));
        m.setName(rs.getString(i++));
        m.setParserClassName(rs.getString(i++));
        m.setInstruction(rs.getString(i++));
        m.setRemark(rs.getString(i++));
        return m;
    }


    @Override
    protected void obj2row(PreparedStatement ps, QuestionKindDo e) throws Exception {
        int i=1;
        ps.setString(i++,e.getId());
        ps.setString(i++,e.getName());
        ps.setString(i++,e.getParserClassName());
        ps.setString(i++,e.getInstruction());
        ps.setString(i++,e.getRemark());

    }
}
