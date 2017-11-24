/**
 * shidao
 * SysRoleModel.java
 * 2015年10月30日
 */
package com.yizhuoyan.shidao.platform.entity;

import com.yizhuoyan.shidao.common.util.KeyValueMap;

import java.util.List;
import java.util.Map;

/**
 * @author root@yizhuoyan.com
 */
public class SystemRoleModel{

private static final long serialVersionUID = 6361545273496684832L;

/**
 * 唯一标识符
 */
private String id;
/**
 * 名字
 */
private String name;
/**
 * 代号
 */
private String code;
/**
 * 描述
 */
private String remark;
/**
 * 角色下功能模块
 */
private List<SystemFunctionalityModel> functionalitys;

  public Map toJSON() {
    KeyValueMap map = KeyValueMap.of(4);
    map.put("id", this.getId());
    map.put("code", this.getCode());
    map.put("name", this.getName());
    if(this.remark!=null) {
        map.put("remark", this.getRemark());
    }
    return map;
  }

  public String getId(){
  return this.id;
}

public void setId(String id){
  this.id = id;
}

public String getName(){
  return this.name;
}

public void setName(String name){
  this.name = name;
}

public String getCode(){
  return this.code;
}

public void setCode(String code){
  this.code = code;
}

public String getRemark(){
  return this.remark;
}

public void setRemark(String remark){
  this.remark = remark;
}


public List<SystemFunctionalityModel> getFunctionalitys(){
  return this.functionalitys;
}

public void setFunctionalitys(List<SystemFunctionalityModel> functionalitys){
  this.functionalitys = functionalitys;
}



}
