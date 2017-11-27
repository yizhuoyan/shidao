package com.yizhuoyan.shidao.questionhub.controller.handler;

import com.yizhuoyan.shidao.common.dto.JsonResponse;
import com.yizhuoyan.shidao.questionhub.entity.QuestionKindModel;
import com.yizhuoyan.shidao.questionhub.function.QuestionHubFunction;
import com.yizhuoyan.shidao.questionhub.po.QuestionKindPo;
import com.yizhuoyan.shidao.questionhub.po.QuestionPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Controller
@RequestMapping("/questionhub/questionkind")
public class QuestionKindManageHandler {
    @Autowired
    private QuestionHubFunction fun;

    public JsonResponse mod(String id,QuestionKindPo po)throws  Exception{
        QuestionKindModel m=fun.modifyQuestionKind(id,po);
        return JsonResponse.ok(m.toJsonMap());
    }

    public JsonResponse list()throws Exception{

        List<QuestionKindModel> result=fun.listQuestionKind();

        return JsonResponse.ok(result,QuestionKindModel::toJsonMap);
    }

}
