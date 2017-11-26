package com.yizhuoyan.shidao.platform.support.service;

import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.platform.dao.SystemConfigDao;
import com.yizhuoyan.shidao.platform.entity.SystemConfigModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemConfigService{
private static Log LOG = LogFactory.getLog(SystemConfigService.class);
private final SystemConfigDao dao;


@Autowired
public SystemConfigService(SystemConfigDao dao){
  this.dao = dao;
}



public int getDefaultPageSize(){
  return getSystemConfig("PAGINATION-PAGESIZE",10);
}

@SuppressWarnings("unchecked")
public <T> T getSystemConfig(String name, T defaultValue){

  try{
    SystemConfigModel dic = dao.select("name",name);
    String value = PlatformUtil.trim(dic.getValue());
    if(value==null){
      return defaultValue;
    }
    if(defaultValue==null){
      return (T)value;
    }
    if(defaultValue instanceof Integer){
      return (T)(Integer)Integer.parseInt(value);
    }
    if(defaultValue instanceof Double){
      return (T)(Double)Double.parseDouble(value);
    }
    if(defaultValue instanceof Boolean){
      return (T)(Boolean)Boolean.parseBoolean(value);
    }
  }catch(Exception e){
    LOG.debug("获取系统配置["+name+"]失败,使用默认值["+defaultValue+"]");
  }
  return defaultValue;
}

}
