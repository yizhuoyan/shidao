package com.yizhuoyan.shidao.questionhub.support.function;

import com.yizhuoyan.shidao.platform.dao.SystemUserDao;
import com.yizhuoyan.shidao.platform.support.service.SystemConfigService;
import com.yizhuoyan.shidao.questionhub.dao.KnowledgePointDao;
import com.yizhuoyan.shidao.questionhub.dao.QuestionDao;
import com.yizhuoyan.shidao.questionhub.dao.QuestionKindDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public abstract class AbstractFunctionSupport {
    @Autowired
    protected QuestionDao questionDao;
    @Autowired
    protected QuestionKindDao kindDao;
    @Autowired
    protected KnowledgePointDao knowledgePointDao;
    @Autowired
    protected SystemConfigService systemConfigService;
    @Autowired
    protected SystemUserDao userDao;

}
