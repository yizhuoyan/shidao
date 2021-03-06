/**
 * shidao
 * SystemRoleManageController.java
 * 2015年10月31日
 */
package com.yizhuoyan.shidao.platform.controller.handler;

import com.yizhuoyan.common.dto.JsonResponse;
import com.yizhuoyan.common.web.springmvc.AbstractControllerSupport;
import com.yizhuoyan.shidao.platform.po.SysRolePo;
import com.yizhuoyan.shidao.entity.SystemFunctionalityEntity;
import com.yizhuoyan.shidao.entity.SystemRoleEntity;
import com.yizhuoyan.shidao.platform.function.SystemRoleManageFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author root@yizhuoyan.com
 */
@Controller
@RequestMapping("/platform/role")
public class SystemRoleManageController extends AbstractControllerSupport{
@Autowired
private SystemRoleManageFunction function;

public JsonResponse list(String key) throws Exception{
  List<SystemRoleEntity> roles = function.listRole(key);
  return JsonResponse.ok(roles,r->r.toJSON());
}

public JsonResponse add(SysRolePo po) throws Exception{
  SystemRoleEntity m = function.addRole(po);
  return JsonResponse.ok(m);
}

public JsonResponse mod(String id,SysRolePo po) throws Exception{
  SystemRoleEntity m = function.modifyRole(id, po);
  return JsonResponse.ok(m);
}
  public JsonResponse del(String id) throws Exception{
    function.deleteSystemRole(id);
    return JsonResponse.ok();
  }

public JsonResponse get(String id) throws Exception{
  SystemRoleEntity m = function.checkRoleDetail(id);
  return JsonResponse.ok(m);
}

public JsonResponse ofFunctionality(String id) throws Exception{
  List<SystemFunctionalityEntity> functionalitys = function.listSystemFunctionalityOfRole(id);
  return JsonResponse.ok(functionalitys,f->f.toJSON());
}

public JsonResponse grantFunctionality(String id, @RequestParam(defaultValue = "") String functionalityIds) throws Exception{
  String[] functionalityIdArray = functionalityIds.split(",");
  function.grantSystemFunctionalitysToRole(id, functionalityIdArray);
  return JsonResponse.ok();
}







}
