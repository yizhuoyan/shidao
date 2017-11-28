/**
 * shidao
 * SystemFunctionalityManageController.java
 * 2015年10月31日
 */
package com.yizhuoyan.shidao.platform.controller.handler;

import com.yizhuoyan.shidao.common.dto.JsonResponse;
import com.yizhuoyan.shidao.platform.po.SystemFunctionalityPo;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityModel;
import com.yizhuoyan.shidao.platform.entity.SystemRoleModel;
import com.yizhuoyan.shidao.platform.function.SystemFuncationalityFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author root@yizhuoyan.com
 */
@Controller
@RequestMapping("/platform/functionality")
public class SystemFunctionalityManageController{
@Autowired
private SystemFuncationalityFunction function;

public JsonResponse list(String key) throws Exception{
  List<SystemFunctionalityModel> result = function.listSystemFunctionality(key);
  return JsonResponse.ok(result,f->f.toJSON());
}
public JsonResponse add(SystemFunctionalityPo po) throws Exception{
  SystemFunctionalityModel m = function.addSystemFunctionality(po);
  return JsonResponse.ok(m.getId());
}
public JsonResponse mod(String id,SystemFunctionalityPo po) throws Exception{
  function.modifySystemFunctionality(id,po);
  return JsonResponse.ok();
}
public JsonResponse get(String id) throws Exception{
  SystemFunctionalityModel m = function.checkSysFunctionalityDetail(id);
  return JsonResponse.ok(m);
}
public JsonResponse del(String id) throws Exception{
  function.deleteSystemFunctionality(id);
  return JsonResponse.ok();
}
public JsonResponse ofRoles(String id) throws Exception{
  List<SystemRoleModel> result = function.listRoleOfSystemFunctionality(id);
  return JsonResponse.ok(result,r->r.toJSON());
}




}
