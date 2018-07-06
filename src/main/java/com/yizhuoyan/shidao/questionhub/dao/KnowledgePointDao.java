package com.yizhuoyan.shidao.questionhub.dao;

import com.yizhuoyan.common.dao.CRUDDao;
import com.yizhuoyan.shidao.entity.KnowledgePointDo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public interface KnowledgePointDao extends CRUDDao<KnowledgePointDo> {

    int selectNextChildCode(String id)throws Exception;
    String selectMaxRootCode()throws Exception;
    boolean existName(String parentId, String name)throws  Exception;
    void increasingChildAmount(String id)throws Exception;
    void decreasingChildAmount(String id)throws Exception;
    void updateCodeCascadeByCode(String code, String newCode)throws Exception;
    List<KnowledgePointDo> selectDescendantByCode(String code)throws Exception;
    int disjoinOnQuestion(String id)throws Exception;
}
