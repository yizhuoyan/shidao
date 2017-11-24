/**
 * shidao
 * SystemConfigController.java
 * 2015年10月31日
 */
package com.yizhuoyan.shidao.platform.controller.handler;

import com.yizhuoyan.shidao.common.dto.JsonResponse;
import com.yizhuoyan.shidao.common.web.springmvc.AbstractControllerSupport;
import com.yizhuoyan.shidao.platform.po.SystemConfigPo;
import com.yizhuoyan.shidao.platform.entity.SystemConfigModel;
import com.yizhuoyan.shidao.platform.function.SystemConfigFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * @author root@yizhuoyan.com
 */
@Controller
@RequestMapping("/platform/systemconfig")
public class SystemConfigManageController extends AbstractControllerSupport{
@Autowired
private SystemConfigFunction function;


public JsonResponse list(String key) throws Exception{
  List<SystemConfigModel> result = function.listSystemConfig(key);

  return JsonResponse.ok(result,c->c.toJSON());
}

public JsonResponse get(String id) throws Exception{
  SystemConfigModel m = function.checkSystemConfigDetail(id);
  return JsonResponse.ok(m.toJSON());
}

public JsonResponse mod(String id, SystemConfigPo po) throws Exception{
  function.modifySystemConfig(id, po);
  return JsonResponse.ok();
}

public JsonResponse add(SystemConfigPo po) throws Exception{
  function.addSystemConfig(po);
  return JsonResponse.ok();
}

}
