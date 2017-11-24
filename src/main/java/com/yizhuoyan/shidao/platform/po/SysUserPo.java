package com.yizhuoyan.shidao.platform.po;

import com.yizhuoyan.shidao.common.validatation.MaxLength;
import com.yizhuoyan.shidao.common.validatation.MustIn;

import javax.validation.constraints.*;


/**
 * @author yizhuoyan@
 */
public class SysUserPo{
/**
 * 账户
 */
@NotBlank
@MaxLength(16)
private String account;
/**
 * 名字
 */
@NotBlank
@MaxLength(16)
private String name;
/**
 * 头像地址
 */
@MaxLength(256)
private String portraitPath;
/**
 * 状态
 */
@MustIn("0/1")
private int status;
/**
 * 备注
 */
@MaxLength(512)
private String remark;


public String getAccount(){
  return this.account;
}

public void setAccount(String account){
  this.account = account;
}

public String getName(){
  return this.name;
}

public void setName(String name){
  this.name = name;
}

public String getPortraitPath(){
  return this.portraitPath;
}

public void setPortraitPath(String portraitPath){
  this.portraitPath = portraitPath;
}

public int getStatus(){
  return this.status;
}

public void setStatus(int status){
  this.status = status;
}

public String getRemark(){
  return this.remark;
}

public void setRemark(String remark){
  this.remark = remark;
}


}
