package com.yizhuoyan.shidao.platform.po;

import com.yizhuoyan.common.util.validatation.MaxLength;
import com.yizhuoyan.common.util.validatation.MustIn;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 系统配置参数对象
 * 用于封装客户端提交参数
 *
 * @author Administrator
 */
@Data
public class SystemConfigPo{
/**
 * 项目名称
 */
@NotBlank
@MaxLength(64)
private String name;
/**
 * 内容
 */
@NotBlank
@MaxLength(256)
private String value;
/**
 * 描述
 */
@NotBlank
@MaxLength(512)
private String remark;

@MustIn("0/1/9")
private int status=1;



}
