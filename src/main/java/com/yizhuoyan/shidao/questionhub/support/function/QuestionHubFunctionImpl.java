package com.yizhuoyan.shidao.questionhub.support.function;

import com.yizhuoyan.shidao.common.dto.PaginationQueryResult;
import com.yizhuoyan.shidao.questionhub.entity.QuestionKindModel;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;
import com.yizhuoyan.shidao.questionhub.function.QuestionHubFunction;
import com.yizhuoyan.shidao.questionhub.po.QuestionKindPo;
import com.yizhuoyan.shidao.questionhub.po.QuestionPo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
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
        return null;
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
