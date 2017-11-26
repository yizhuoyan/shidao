package com.yizhuoyan.shidao.questionhub.support.function;

import com.yizhuoyan.shidao.common.dao.support.SelectLikePo;
import com.yizhuoyan.shidao.common.dto.PaginationQueryResult;
import com.yizhuoyan.shidao.questionhub.entity.QuestionKindModel;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;
import com.yizhuoyan.shidao.questionhub.function.QuestionHubFunction;
import com.yizhuoyan.shidao.questionhub.po.QuestionKindPo;
import com.yizhuoyan.shidao.questionhub.po.QuestionPo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yizhuoyan.shidao.common.util.PlatformUtil.trim;
import static com.yizhuoyan.shidao.common.util.PlatformUtil.wrap;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Service
public class QuestionHubFunctionImpl extends AbstractFunctionSupport implements QuestionHubFunction {



    @Override
    public QuestionModel addQuestion(QuestionPo po) throws Exception {
        return null;
    }

    @Override
    public int deleteQuestion(String... ids) throws Exception {
        return 0;
    }

    @Override
    public QuestionModel modifyQuestion(String id, QuestionPo po) throws Exception {
        return null;
    }

    @Override
    public QuestionModel checkQuestion(String id) throws Exception {
        return null;
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
            q.setKind(kindDao.select("id",q.getKindId()));
            q.setCreateUser(userDao.select("id",q.getCreateUserId()));
        }
        PaginationQueryResult<QuestionModel> result=new PaginationQueryResult<>(total,pageData);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        return result;
    }

    @Override
    public QuestionKindModel modifyQuestionKind(String id, QuestionKindPo po) throws Exception {
        return null;
    }

    @Override
    public QuestionKindModel checkQuestionKind(String id) throws Exception {
        return null;
    }

    @Override
    public List<QuestionKindModel> listQuestionKind() throws Exception {
        return null;
    }
}
