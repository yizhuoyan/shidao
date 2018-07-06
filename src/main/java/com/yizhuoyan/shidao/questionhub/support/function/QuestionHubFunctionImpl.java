package com.yizhuoyan.shidao.questionhub.support.function;

import com.yizhuoyan.common.dao.support.SelectLikePo;
import com.yizhuoyan.common.dto.PaginationQueryResult;
import com.yizhuoyan.common.util.KeyValueMap;
import com.yizhuoyan.common.util.validatation.ParameterObjectValidator;
import com.yizhuoyan.shidao.entity.KnowledgePointDo;
import com.yizhuoyan.shidao.entity.QuestionKindDo;
import com.yizhuoyan.shidao.entity.QuestionDo;
import com.yizhuoyan.shidao.questionhub.function.QuestionHubFunction;
import com.yizhuoyan.shidao.questionhub.parser.QuestionParser;
import com.yizhuoyan.shidao.questionhub.po.KnowledgePointPo;
import com.yizhuoyan.shidao.questionhub.po.QuestionKindPo;
import com.yizhuoyan.shidao.questionhub.po.QuestionPo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.yizhuoyan.common.util.AssertThrowUtil.assertFalse;
import static com.yizhuoyan.common.util.AssertThrowUtil.assertNotNull;
import static com.yizhuoyan.common.util.AssertThrowUtil.assertTrue;
import static com.yizhuoyan.common.util.PlatformUtil.trim;
import static com.yizhuoyan.common.util.PlatformUtil.uuid12;
import static com.yizhuoyan.common.util.validatation.ParameterValidator.$;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Service
public class QuestionHubFunctionImpl extends AbstractFunctionSupport implements QuestionHubFunction {


    @Override
    public QuestionDo addQuestion(QuestionPo po) throws Exception {
        ParameterObjectValidator.throwIfFail(po);
        String kindId = po.getKindId();
        QuestionKindDo kind = kindDao.select("id", kindId);
        //validate kind
        assertNotNull("not-exist.kindId", kind, kindId);
        //validate creator user
        String createUserId = po.getCreateUserId();
        assertTrue("not-exist.createUserId", userDao.exist("id", createUserId), createUserId);

        //parse content and supply content
        QuestionParser parser = (QuestionParser) Class.forName(kind.getParserClassName()).newInstance();

        QuestionDo m = parser.parse(po.getContent());
        m.setId(uuid12());
        m.setCreateTime(Instant.now());
        m.setCreateUserId(createUserId);
        m.setDifficult(po.getDifficult());
        m.setQuestionKindId(kindId);
        m.setUpdateTime(m.getCreateTime());

        questionDao.insert(m);
        return m;
    }

    @Override
    public int deleteQuestion(String... ids) throws Exception {
        for (String id : ids) {
            //delete the foreign relation

            //delete the question
            questionDao.delete("id", id);
        }
        return ids.length;
    }

    @Override
    public QuestionDo modifyQuestion(String id, QuestionPo po) throws Exception {
        id = $("id", id);

        QuestionDo old = questionDao.select("id", id);
        assertNotNull("not-exist.id", old, id);

        ParameterObjectValidator.throwIfFail(po);

        QuestionKindDo kind = kindDao.select("id", old.getQuestionKindId());


        //parse content and supply content
        QuestionParser parser = (QuestionParser) Class.forName(kind.getParserClassName()).newInstance();

        QuestionDo newQuestion = parser.parse(po.getContent());

        KeyValueMap needUpdate = new KeyValueMap(3);

        //1content
        needUpdate.put("content", newQuestion.getContent());
        //2difficult
        int newDifficult = po.getDifficult();
        if (newDifficult != old.getDifficult()) {
            needUpdate.put("difficult", newDifficult);
            old.setDifficult(newDifficult);
        }
        //title
        String newTitle = newQuestion.getTitle();
        if (!Objects.equals(newTitle, old.getTitle())) {
            needUpdate.put("title", newTitle);
            old.setTitle(newTitle);
        }
        //options
        String newOptions = newQuestion.getOptions();
        if (!Objects.equals(newOptions, old.getOptions())) {
            needUpdate.put("options", newOptions);
            old.setOptions(newOptions);
        }
        //answer
        String newAnswer = newQuestion.getAnswer();
        if (!Objects.equals(newAnswer, old.getAnswer())) {
            needUpdate.put("answer", newAnswer);
            old.setAnswer(newAnswer);
        }
        //explain
        String newExplain = newQuestion.getAnswerExplain();
        if (!Objects.equals(newExplain, old.getAnswerExplain())) {
            needUpdate.put("answer_explain", newExplain);
            old.setAnswer(newExplain);
        }
        Instant updateTime = Instant.now();
        old.setUpdateTime(updateTime);
        needUpdate.put("update_time", Timestamp.from(updateTime));
        questionDao.update(id, needUpdate);
        return old;
    }

    @Override
    public QuestionDo checkQuestion(String id) throws Exception {
        id = $("id", id);
        QuestionDo q = questionDao.select("id", id);
        assertNotNull("not-exist.id", q, id);
        q.setCreateUser(userDao.select("id", q.getCreateUserId()));
        q.setQuestionkind(kindDao.select("id", q.getQuestionKindId()));
        return q;
    }

    @Override
    public PaginationQueryResult<QuestionDo> listQuestion(String kind, String key, int pageSize, int pageNo) throws Exception {
        key = trim(key);
        kind = trim(kind);
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 1) {
            pageSize = systemConfigService.getDefaultPageSize();
        }
        String whereSql = null;
        if (kind != null) {
            whereSql = "question_kind_id=?";
        }
        List<QuestionDo> pageData = new ArrayList<>(pageSize);
        int total = questionDao.selectsByLikeOnPagination(pageData, SelectLikePo.of("content", key)
                .setWhereSql(whereSql, kind)
                .setOrderBy("update_time")
                .setPageNo(pageNo)
                .setPageSize(pageSize));

        //查找关联数据
        for (QuestionDo q : pageData) {
            q.setQuestionkind(kindDao.select("id", q.getQuestionKindId()));
            q.setCreateUser(userDao.select("id", q.getCreateUserId()));
        }
        PaginationQueryResult<QuestionDo> result = new PaginationQueryResult<>(total, pageData);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        return result;
    }

    @Override
    public QuestionKindDo addQuestionKind(QuestionKindPo po) throws Exception {
        ParameterObjectValidator.throwIfFail(po);
        String id = po.getId();
        assertFalse("already-exist.id", kindDao.exist("id", id), id);

        QuestionKindDo m = new QuestionKindDo();

        m.setId(id);
        m.setName(po.getName());
        m.setRemark(po.getRemark());
        m.setInstruction(po.getInstruction());

        kindDao.insert(m);
        return m;
    }


    @Override
    public QuestionKindDo modifyQuestionKind(String id, QuestionKindPo po) throws Exception {
        id = $("id", id);
        ParameterObjectValidator.throwIfFail(po);
        QuestionKindDo old = kindDao.select("id", id);
        assertNotNull("not-exist.id", old, id);

        KeyValueMap needUpdateMap = KeyValueMap.of(2);

        String newName = po.getName();
        if (!newName.equals(old.getName())) {
            needUpdateMap.put("name", newName);
            old.setName(newName);
        }
        String newRemark = po.getRemark();
        if (!Objects.equals(newRemark, old.getRemark())) {
            needUpdateMap.put("remark", newRemark);
            old.setRemark(newRemark);
        }

        String newInstruction = po.getInstruction();
        if (!Objects.equals(newInstruction, old.getInstruction())) {
            needUpdateMap.put("instruction", newInstruction);
            old.setInstruction(newInstruction);
        }

        kindDao.update(id, needUpdateMap);


        return old;
    }

    @Override
    public QuestionKindDo checkQuestionKind(String id) throws Exception {
        id = $("id", id);
        QuestionKindDo m = kindDao.select("id", id);
        assertNotNull("not-exist.id", m, id);
        return m;
    }

    @Override
    public List<QuestionKindDo> listQuestionKind() throws Exception {
        return kindDao.selects("id");
    }

    @Override
    public int deleteKnowledgePoint(String... ids) throws Exception {
        for(String id:ids) {
            id = $("id", id);
            KnowledgePointDo m = knowledgePointDao.select("id", id);

            assertNotNull("not-exist.id", m, id);

            //查找所有后代(排序，保证从底层后代开始删除)
            List<KnowledgePointDo> descendant = knowledgePointDao.selectDescendantByCode(m.getCode());

            for (KnowledgePointDo child : descendant) {
                // 删除关联关系
                knowledgePointDao.disjoinOnQuestion(child.getId());
                knowledgePointDao.delete("id", child.getId());
            }
            //删除自己的关联关系
            knowledgePointDao.disjoinOnQuestion(id);
            //删除自己
            knowledgePointDao.delete("id", id);
            if(m.getParentId()!=null) {
                //更新父节点子节点数量
                knowledgePointDao.decreasingChildAmount(m.getParentId());
            }
        }
        return 0;
    }

    @Override
    public KnowledgePointDo modifyKnowledgePoint(String id, KnowledgePointPo po) throws Exception {
        id = $("id", id);
        KnowledgePointDo old = knowledgePointDao.select("id", id);
        assertNotNull("not-exist.id", old, id);

        ParameterObjectValidator.throwIfFail(po);
        //updateMap
        KeyValueMap updateMap = new KeyValueMap(3);

        //parent
        KnowledgePointDo newParent = null;
        String newParentId = trim(po.getParentId());
        if (!Objects.equals(newParentId, old.getParentId())) {
            if (newParentId != null) {
                newParent = knowledgePointDao.select("id", newParentId);
                assertNotNull("not-exist.parentId", newParent, newParentId);
                //不能是当前节点的子节点及其本身
                assertFalse("parentIsCurrent.parentId", newParentId.equals(id), newParentId);
                //   /1/0  /0
                assertFalse("parentIsCurrentDescendant.parentId", newParent.getCode().startsWith(old.getCode()), newParentId);

                knowledgePointDao.increasingChildAmount(newParentId);
            }
            updateMap.put("parent_Id", newParentId);
            old.setParentId(newParentId);

            //code
            String newCode = this.nextKnowledgeCode(newParentId);
            if (newParent != null) {
                newCode = newParent.getCode()+newCode;
            }
            knowledgePointDao.updateCodeCascadeByCode(old.getCode(), newCode);
        }
        //name
        String newName = po.getName();
        if (!Objects.equals(newName, old.getName())) {
            assertFalse("already-exist.name", knowledgePointDao.existName(newParentId, newName), newName);
            updateMap.put("name", newName);
            old.setName(newName);
        }
        //remark
        String newRemark = po.getRemark();
        if (!Objects.equals(newRemark, old.getRemark())) {
            updateMap.put("remark", newRemark);
            old.setRemark(newRemark);
        }
        knowledgePointDao.update(id, updateMap);
        return old;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected String nextKnowledgeCode(String id) throws Exception {
        if (id == null) {
            String max=knowledgePointDao.selectMaxRootCode();
            if(max==null)return "/0";
            return "/"+Integer.toString(Integer.parseInt(max.substring(1),16)+1,16);
        }
        return "/"+Integer.toString(knowledgePointDao.selectNextChildCode(id),16);
    }

    @Override
    public synchronized KnowledgePointDo addKnowledgePoint(KnowledgePointPo po) throws Exception {
        ParameterObjectValidator.throwIfFail(po);

        //parent
        KnowledgePointDo parent = null;
        String parentId = trim(po.getParentId());
        if (parentId != null) {
            parent = knowledgePointDao.select("id", parentId);
            assertNotNull("not-exist.parentId", parent, parentId);
        }
        //code
        String code = this.nextKnowledgeCode(parentId);
        if (parent != null) {
            code = parent.getCode()+code;
        }
        //name
        String name = po.getName();
        assertFalse("already-exist.name", knowledgePointDao.existName(parentId, name), name);

        KnowledgePointDo m = new KnowledgePointDo();
        m.setChildrenAmount(0);
        m.setCode(code);
        m.setId(uuid12());
        m.setName(name);
        m.setParentId(parentId);
        m.setRemark(po.getRemark());
        m.setNextChildCode(0);

        knowledgePointDao.insert(m);
        if (parentId != null) {
            //update parent
            knowledgePointDao.increasingChildAmount(parentId);
        }
        return m;
    }

    @Override
    public List<KnowledgePointDo> listKnowledgePoint() throws Exception {
        return knowledgePointDao.selects("id");
    }

    @Override
    public KnowledgePointDo checkKnowledgePoint(String id) throws Exception {
        id = $("id", id);
        KnowledgePointDo m = knowledgePointDao.select("id", id);
        assertNotNull("not-exist.id", m, id);
        //查找父知识点
        KnowledgePointDo parent = knowledgePointDao.select("id", m.getParentId());

        m.setParent(parent);

        return m;
    }
}
