/**
 * shidao
 * SysFunctionalityModel.java
 * 2015年10月30日
 */
package com.yizhuoyan.shidao.platform.entity;

import com.yizhuoyan.shidao.common.dto.Tree;
import com.yizhuoyan.shidao.common.util.KeyValueMap;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author root@yizhuoyan.com
 */
public class SystemFunctionalityModel implements Tree.Node<String>{
/**
 * 状态常量
 */
public static final int STATUS_ACTIVATE = 1, STATUS_DEACTIVATE = 0;
/**
 * 系统功能类型
 * 目录
 * 菜单
 * 按钮或链接
 */
public static final int KIND_DIRECTORY = 0, KIND_MENU = 1, KIND_BUTTON_AND_LINK = 2;

private static final long serialVersionUID = 8390482014375996154L;
/**
 * 唯一标识符
 */
private String id;
/**
 * 代号
 */
private String code;
/**
 * 名字
 */
private String name;
/**
 * 路径
 */
private String url;

/**
 * 父模块
 */

private SystemFunctionalityModel parent;
/**
 * 父模块ID
 */
private String parentId;
/**
 * 排序号
 */
private String orderCode;
/**
 * 创建时间
 */
private Date createTime;
/**
 * 状态
 */
private int status;
/**
 * 所有直接子模块
 */
private List<SystemFunctionalityModel> children;
/**
 * 备注
 */
private String remark;
/**
 * 功能界面种类
 */
private int kind;

/**
 * 拥有角色
 */
private List<SystemRoleModel> roles;


  public Map toJSON() {
    
    KeyValueMap result = new KeyValueMap(8);
    
    result.put("id",this.getId());
    result.put("code",this.getCode());
      result.put("name",this.getName());
      result.put("kind",this.getKind());
      result.put("status",this.getStatus());
      result.put("parentId",this.parentId);

      if(this.url!=null) {
          result.put("url",this.getUrl());
      }
      result.put("createTime",this.getCreateTime());
      if(this.orderCode!=null) {
          result.put("orderCode",this.getOrderCode());
      }
      if(this.remark!=null) {
          result.put("remark",this.getRemark());
      }
    if(this.parent!=null){
      result.put("parent",KeyValueMap.of(2)
              .put("id",parent.getId())
              .put("name",parent.getName())
              .put("code",parent.getCode())
      );
    }

    if(this.roles!=null){
        int size=this.roles.size();
        Object[] roles=new Object[size];
        SystemRoleModel r;
        while(size-->0){
            r=this.roles.get(size);
            roles[size]=KeyValueMap.of(2)
                    .put("id",r.getId())
                    .put("name",r.getName());
        }
        result.put("roles",roles);
    }
    return result;
  }

  public String getRemark(){
  return this.remark;
}

public void setRemark(String remark){
  this.remark = remark;
}

public String getCode(){
  return this.code;
}

public void setCode(String code){
  this.code = code;
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

public String getUrl(){
  return this.url;
}

public void setUrl(String url){
  this.url = url;
}

public int getKind(){
  return kind;
}

public void setKind(int kind){
  this.kind = kind;
}

public SystemFunctionalityModel getParent(){
  return this.parent;
}

public void setParent(SystemFunctionalityModel parent){
  this.parent = parent;
}

public String getOrderCode(){
  return this.orderCode;
}

public void setOrderCode(String orderCode){
  this.orderCode = orderCode;
}

public Date getCreateTime(){
  return this.createTime;
}

public void setCreateTime(Date createTime){
  this.createTime = createTime;
}

public int getStatus(){
  return this.status;
}

public void setStatus(int status){
  this.status = status;
}

public String getParentId(){
  return this.parentId;
}

public void setParentId(String parentId){
  this.parentId = parentId;
}

public List<SystemRoleModel> getRoles(){
  return this.roles;
}

public void setRoles(List<SystemRoleModel> roles){
  this.roles = roles;
}

public List<SystemFunctionalityModel> getChildren(){
  return this.children;
}

public void setChildren(List<SystemFunctionalityModel> children){
  this.children = children;
}


}
