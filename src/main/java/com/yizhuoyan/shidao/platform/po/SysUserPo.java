package com.yizhuoyan.shidao.platform.po;

import com.yizhuoyan.common.util.validatation.MaxLength;
import com.yizhuoyan.common.util.validatation.MustIn;
import lombok.Data;

import javax.validation.constraints.*;


/**
 * @author yizhuoyan@
 */
@Data
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


}
