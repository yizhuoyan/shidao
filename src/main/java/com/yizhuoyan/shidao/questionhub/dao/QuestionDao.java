package com.yizhuoyan.shidao.questionhub.dao;

import com.yizhuoyan.shidao.common.dao.CRUDDao;
import com.yizhuoyan.shidao.common.dao.PaginationQueryDao;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public interface QuestionDao extends CRUDDao<QuestionModel>,PaginationQueryDao<QuestionModel> {

}
