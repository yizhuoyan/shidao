package com.yizhuoyan.shidao.questionhub.function;

import com.yizhuoyan.common.dto.PaginationQueryResult;
import com.yizhuoyan.shidao.entity.KnowledgePointDo;
import com.yizhuoyan.shidao.entity.QuestionKindDo;
import com.yizhuoyan.shidao.entity.QuestionDo;
import com.yizhuoyan.shidao.questionhub.po.KnowledgePointPo;
import com.yizhuoyan.shidao.questionhub.po.QuestionKindPo;
import com.yizhuoyan.shidao.questionhub.po.QuestionPo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Transactional
public interface QuestionHubFunction {

    QuestionDo addQuestion(QuestionPo po)throws  Exception;
    int deleteQuestion(String... ids)throws  Exception;
    QuestionDo modifyQuestion(String id, QuestionPo po)throws  Exception;
    QuestionDo checkQuestion(String id)throws  Exception;
    PaginationQueryResult<QuestionDo> listQuestion(String kind, String key, int pageSize, int pageNo)throws  Exception;


    QuestionKindDo modifyQuestionKind(String id, QuestionKindPo po)throws  Exception;
    QuestionKindDo addQuestionKind(QuestionKindPo po)throws  Exception;
    QuestionKindDo checkQuestionKind(String id)throws  Exception;
    List<QuestionKindDo> listQuestionKind()throws  Exception;


    KnowledgePointDo modifyKnowledgePoint(String id, KnowledgePointPo po)throws  Exception;
    KnowledgePointDo addKnowledgePoint(KnowledgePointPo po)throws  Exception;
    List<KnowledgePointDo> listKnowledgePoint()throws  Exception;
    KnowledgePointDo checkKnowledgePoint(String id)throws  Exception;
    int deleteKnowledgePoint(String... ids)throws  Exception;



}
