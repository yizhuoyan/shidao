package com.yizhuoyan.shidao.questionhub.controller.handler;

import com.yizhuoyan.shidao.common.dto.JsonResponse;
import com.yizhuoyan.shidao.questionhub.entity.KnowledgePointDo;
import com.yizhuoyan.shidao.questionhub.function.QuestionHubFunction;
import com.yizhuoyan.shidao.questionhub.po.KnowledgePointPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Controller
@RequestMapping("/questionhub/knowledgePoint")
public class KnowledgePointManageHandler {
    @Autowired
    private QuestionHubFunction fun;

    public JsonResponse add(KnowledgePointPo po)throws  Exception{
        KnowledgePointDo m=fun.addKnowledgePoint(po);
        return JsonResponse.ok(m.toJsonMap());
    }
    public JsonResponse get(String id)throws  Exception{
        KnowledgePointDo m=fun.checkKnowledgePoint(id);
        return JsonResponse.ok(m.toJsonMap());
    }
    public JsonResponse del(String id)throws  Exception{
        fun.deleteKnowledgePoint(id);
        return JsonResponse.ok();
    }

    public JsonResponse mod(String id,KnowledgePointPo po)throws  Exception{
        KnowledgePointDo m=fun.modifyKnowledgePoint(id,po);
        return JsonResponse.ok(m.toJsonMap());
    }

    public JsonResponse list()throws Exception{

        List<KnowledgePointDo> result=fun.listKnowledgePoint();

        return JsonResponse.ok(result, KnowledgePointDo::toJsonMap);
    }

}
