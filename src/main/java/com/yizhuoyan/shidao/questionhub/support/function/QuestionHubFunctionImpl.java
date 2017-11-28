package com.yizhuoyan.shidao.questionhub.support.function;

import com.yizhuoyan.shidao.common.dao.support.SelectLikePo;
import com.yizhuoyan.shidao.common.dto.PaginationQueryResult;
import com.yizhuoyan.shidao.common.util.KeyValueMap;
import com.yizhuoyan.shidao.common.validatation.ParameterObjectValidator;
import com.yizhuoyan.shidao.questionhub.entity.QuestionKindModel;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;
import com.yizhuoyan.shidao.questionhub.function.QuestionHubFunction;
import com.yizhuoyan.shidao.questionhub.po.QuestionKindPo;
import com.yizhuoyan.shidao.questionhub.po.QuestionPo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.yizhuoyan.shidao.common.util.PlatformUtil.trim;
import static com.yizhuoyan.shidao.common.util.PlatformUtil.uuid12;
import static com.yizhuoyan.shidao.common.util.AssertThrowUtil.*;
import static com.yizhuoyan.shidao.common.validatation.ParameterValidator.$;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Service
public class QuestionHubFunctionImpl extends AbstractFunctionSupport implements QuestionHubFunction {



    @Override
    public QuestionModel addQuestion(QuestionPo po) throws Exception {
        ParameterObjectValidator.throwIfFail(po);
        String kindId=po.getKindId();
        //validate kind
        assertTrue("not-exist.kindId",kindDao.exist("id",kindId),kindId);
        //validate creator user
        String createUserId=po.getCreateUserId();
        assertTrue("not-exist.createUserId",userDao.exist("id",createUserId),createUserId);

        QuestionModel m=new QuestionModel();
        m.setId(uuid12());
        m.setAnswer(po.getAnswer());
        m.setAnswerExplain(po.getAnswerExplain());
        m.setCompositeQuestionId(po.getCompositeQuestionId());
        m.setContent(po.getContent());
        m.setCreateTime(Instant.now());
        m.setCreateUserId(createUserId);
        m.setDifficult(po.getDifficult());
        m.setQuestionKindId(kindId);
        m.setOptions(po.getOptions());
        m.setUpdateTime(m.getCreateTime());

        questionDao.insert(m);
        return m;
    }

    @Override
    public int deleteQuestion(String... ids) throws Exception {
        for(String id:ids){
            //delete the foreign relation

            //delete the question
            questionDao.delete("id",id);
        }
        return ids.length;
    }

    @Override
    public QuestionModel modifyQuestion(String id, QuestionPo po) throws Exception {
        return null;
    }

    @Override
    public QuestionModel checkQuestion(String id) throws Exception {
        id=$("id",id);
        QuestionModel q=questionDao.select("id",id);
        assertNotNull("not-exist.id",q,id);
        q.setCreateUser(userDao.select("id",q.getCreateUserId()));
        q.setQuestionkind(kindDao.select("id",q.getQuestionKindId()));
        return q;
    }

    @Override
    public PaginationQueryResult<QuestionModel> listQuestion(String kind, String key, int pageSize, int pageNo) throws Exception {
        key=trim(key);
        kind=trim(kind);
        if(pageNo<1){
            pageNo=1;
        }
        if(pageSize<1){
            pageSize=systemConfigService.getDefaultPageSize();
        }
        String whereSql=null;
        if(kind!=null){
            whereSql="kind=?";
        }
        List<QuestionModel> pageData=new ArrayList<>(pageSize);
        int total=questionDao.selectsByLikeOnPagination(pageData, SelectLikePo.of("content",key)
                .setWhereSql(whereSql,kind)
                .setOrderBy("update_time")
                .setPageNo(pageNo)
                .setPageSize(pageSize));

        //查找关联数据
        for (QuestionModel q:pageData){
            q.setQuestionkind(kindDao.select("id",q.getQuestionKindId()));
            q.setCreateUser(userDao.select("id",q.getCreateUserId()));
        }
        PaginationQueryResult<QuestionModel> result=new PaginationQueryResult<>(total,pageData);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        return result;
    }

    @Override
    public QuestionKindModel addQuestionKind(QuestionKindPo po) throws Exception {
        ParameterObjectValidator.throwIfFail(po);
        String id=po.getId();
        assertFalse("already-exist.id",kindDao.exist("id",id),id);

        QuestionKindModel m=new QuestionKindModel();

        m.setId(id);
        m.setName(po.getName());
        m.setRemark(po.getRemark());
        m.setIntroduction(po.getIntroduction());

        kindDao.insert(m);
        return m;
    }


    @Override
    public QuestionKindModel modifyQuestionKind(String id, QuestionKindPo po) throws Exception {
        id=$("id",id);
        ParameterObjectValidator.throwIfFail(po);
        QuestionKindModel old=kindDao.select("id",id);
        assertNotNull("not-exist.id",old,id);

        KeyValueMap needUpdateMap=KeyValueMap.of(2);

        String newName=po.getName();
        if(!newName.equals(old.getName())){
            needUpdateMap.put("name",newName);
            old.setName(newName);
        }
        String newRemark=po.getRemark();
        if(!Objects.equals(newRemark,old.getRemark())) {
            needUpdateMap.put("remark",newRemark);
            old.setRemark(newRemark);
        }

        String newIntroduction=po.getIntroduction();
        if(!Objects.equals(newIntroduction,old.getIntroduction())){
            needUpdateMap.put("remark",newIntroduction);
            old.setIntroduction(newIntroduction);
        }

        kindDao.update(id,needUpdateMap);


        return old;
    }

    @Override
    public QuestionKindModel checkQuestionKind(String id) throws Exception {
        id=$("id",id);
        QuestionKindModel m=kindDao.select("id",id);
        assertNotNull("not-exist.id",m,id);
        return m;
    }

    @Override
    public List<QuestionKindModel> listQuestionKind() throws Exception {
        return kindDao.selects("id");
    }
}
