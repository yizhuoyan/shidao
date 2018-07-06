package com.yizhuoyan.shidao.platform.controller.handler;

import com.yizhuoyan.shidao.common.dto.JsonResponse;
import com.yizhuoyan.shidao.common.web.springmvc.AbstractControllerSupport;
import com.yizhuoyan.shidao.platform.dto.UserContext;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityDo;
import com.yizhuoyan.shidao.platform.function.UserCommonFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 公用处理者控制器
 *
 * @author Administrator
 */
@Controller
@SessionAttributes("CURRENT_USER_CONTEXT")
public class UserCommonController extends AbstractControllerSupport{
@Autowired
private UserCommonFunction commonFunction;

/**
 * 测试出@SessionAttributes和@responseBody不能共用,会出Cannot create a session after the response has been committed
 * 所以此处使用HttpSession作为参数,让session先创建
 *
 * @param account xx
 * @param password  xx
 * @return
 * @throws Exception
 */
public JsonResponse login(String account, String password, HttpServletRequest req) throws Exception{
  UserContext context = this.commonFunction.login(account, password);
  HttpSession session = req.getSession(false);
  if(session!=null){
    session.invalidate();
  }
  session=req.getSession();
  //保存登陆凭据
  context.setToken(session.getId());
  context.setCurrentLoginIp(req.getRemoteHost());
  UserContext.saveCurrentUser(context);
  session.setAttribute("TOKEN",context.getToken());

  return JsonResponse.ok(context.toJson());
}


public JsonResponse logout(HttpSession session){
  session.invalidate();
  return JsonResponse.ok();
}

public JsonResponse modifyMyPassword(String account,String oldPassword,
                               String newPassword, String newPasswordConfirm) throws Exception{
  UserContext context = UserContext.getCurrentUser();
  String userId = context.getUserId();
  if(context.isFirstLogin()){
    //第一次登陆修改密码不需要旧密码
    this.commonFunction.firstLoginModifyPassword(userId,newPassword,newPasswordConfirm);
  }else{
    this.commonFunction.modifyPassword(userId, oldPassword, newPassword,
        newPasswordConfirm);
  }
  return JsonResponse.ok();
}

public JsonResponse myMenu() throws Exception{
  UserContext context = UserContext.getCurrentUser();
  List<SystemFunctionalityDo> functionalitys = context.getMenuFunctionalitys();
  return JsonResponse.ok(functionalitys,f->f.toJSON());
}

}
