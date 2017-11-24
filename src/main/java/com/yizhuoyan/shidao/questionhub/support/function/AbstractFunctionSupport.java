package com.yizhuoyan.shidao.questionhub.support.function;

import com.yizhuoyan.shidao.questionhub.dao.KnowledgePointDao;
import com.yizhuoyan.shidao.questionhub.dao.QuestionDao;
import com.yizhuoyan.shidao.questionhub.dao.QuestionKindDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public abstract class AbstractFunctionSupport {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionKindDao kindDao;
    @Autowired
    private KnowledgePointDao knowledgePointDao;

}
