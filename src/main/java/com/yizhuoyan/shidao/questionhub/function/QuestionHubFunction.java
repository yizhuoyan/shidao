package com.yizhuoyan.shidao.questionhub.function;

import com.yizhuoyan.shidao.common.dto.PaginationQueryResult;
import com.yizhuoyan.shidao.questionhub.entity.QuestionKindModel;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;
import com.yizhuoyan.shidao.questionhub.po.QuestionKindPo;
import com.yizhuoyan.shidao.questionhub.po.QuestionPo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public interface QuestionHubFunction {

    QuestionModel addQuestion(QuestionPo po)throws  Exception;
    int deleteQuestion(String... ids)throws  Exception;
    QuestionModel modifyQuestion(String id,QuestionPo po)throws  Exception;
    QuestionModel checkQuestion(String id)throws  Exception;
    PaginationQueryResult<QuestionModel> listQuestion(String kind,String key,int pageSize,int pageNo)throws  Exception;

    QuestionKindModel modifyQuestionKind(String id,QuestionKindPo po)throws  Exception;
    QuestionKindModel checkQuestionKind(String id)throws  Exception;
    List<QuestionKindModel> listQuestionKind()throws  Exception;





}
