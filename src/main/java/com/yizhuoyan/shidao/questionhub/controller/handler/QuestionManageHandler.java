package com.yizhuoyan.shidao.questionhub.controller.handler;

import com.yizhuoyan.shidao.common.dto.JsonResponse;
import com.yizhuoyan.shidao.common.dto.PaginationQueryResult;
import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.questionhub.entity.QuestionModel;
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
        QuestionModel m=fun.addQuestion(po);
        return JsonResponse.ok(m.toJSONMap());
    }

    public JsonResponse list(String kind,String key,String pageSize,String pageNo)throws Exception{
        int pageSizeInt= PlatformUtil.parseInt(pageSize,-1);
        int pageNoInt=PlatformUtil.parseInt(pageNo,-1);

        PaginationQueryResult<QuestionModel> result=fun.listQuestion(kind,key,pageSizeInt,pageNoInt);

        return JsonResponse.ok(result.toJSON(QuestionModel::toJSONMap));
    }

}
