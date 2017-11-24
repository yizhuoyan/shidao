package com.yizhuoyan.shidao.platform.po;

import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.common.validatation.MaxLength;
import com.yizhuoyan.shidao.common.validatation.MustIn;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SystemFunctionalityPo{

@NotBlank
@MaxLength(512)
private String code;
@NotBlank
@MaxLength(32)
private String name;
@MaxLength(256)
private String url;
    @MaxLength(32)
private String parentId;
    @MaxLength(16)
private String orderCode;
    @MaxLength(512)
private String remark;
@MustIn("0/1")
private int status=1;
@MustIn("0/1/2")
private int kind=1;

public int getKind(){
  return kind;
}

public void setKind(int kind){
  this.kind = kind;
}

public String getCode(){
  return this.code;
}

public void setCode(String code){
  this.code = code;
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

public String getParentId(){
  return this.parentId;
}

public void setParentId(String parentId){
  this.parentId = PlatformUtil.trim(parentId);
}

public String getOrderCode(){
  return this.orderCode;
}

public void setOrderCode(String orderCode){
  this.orderCode = orderCode;
}

public String getRemark(){
  return this.remark;
}

public void setRemark(String remark){
  this.remark = remark;
}

public int getStatus(){
  return this.status;
}

public void setStatus(int status){
  this.status = status;
}


}
