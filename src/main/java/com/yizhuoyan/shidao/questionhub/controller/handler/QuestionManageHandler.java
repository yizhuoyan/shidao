package com.yizhuoyan.shidao.questionhub.controller.handler;

import com.yizhuoyan.common.dto.JsonResponse;
import com.yizhuoyan.common.dto.PaginationQueryResult;
import com.yizhuoyan.common.util.PlatformUtil;
import com.yizhuoyan.shidao.entity.QuestionDo;
import com.yizhuoyan.shidao.questionhub.function.QuestionHubFunction;
import com.yizhuoyan.shidao.questionhub.po.QuestionPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Controller
@RequestMapping("/questionhub/question")
public class QuestionManageHandler  {
    @Autowired
    private QuestionHubFunction fun;

    public JsonResponse add(QuestionPo po)throws  Exception{
        po.setCreateUserId("t1");
        QuestionDo m=fun.addQuestion(po);
        return JsonResponse.ok(m.toJSONMap());
    }
    public JsonResponse get(String id)throws  Exception{
        QuestionDo m=fun.checkQuestion(id);
        return JsonResponse.ok(m.toJSONMap());
    }
    public JsonResponse mod(String id,QuestionPo po)throws  Exception{
        QuestionDo m=fun.modifyQuestion(id,po);
        return JsonResponse.ok(m.toJSONMap());
    }

    public JsonResponse list(String kind,String key,String pageSize,String pageNo)throws Exception{
        int pageSizeInt= PlatformUtil.parseInt(pageSize,-1);
        int pageNoInt=PlatformUtil.parseInt(pageNo,-1);

        PaginationQueryResult<QuestionDo> result=fun.listQuestion(kind,key,pageSizeInt,pageNoInt);

        return JsonResponse.ok(result.toJSON(QuestionDo::toJSONMap));
    }

}
