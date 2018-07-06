package com.yizhuoyan.shidao.platform.controller.handler;

import com.yizhuoyan.common.dto.JsonResponse;
import com.yizhuoyan.common.web.springmvc.AbstractControllerSupport;
import com.yizhuoyan.shidao.platform.po.SystemConfigPo;
import com.yizhuoyan.shidao.entity.SystemConfigEntity;
import com.yizhuoyan.shidao.platform.function.SystemConfigFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * @author root@yizhuoyan.com
 */

@RequestMapping("/platform/config")
public class SystemConfigManageController extends AbstractControllerSupport{
@Autowired
private SystemConfigFunction function;


public JsonResponse list(String key) throws Exception{
  List<SystemConfigEntity> result = function.listSystemConfig(key);

  return JsonResponse.ok(result,c->c.toJSON());
}

public JsonResponse get(String id) throws Exception{
  SystemConfigEntity m = function.checkSystemConfigDetail(id);
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
