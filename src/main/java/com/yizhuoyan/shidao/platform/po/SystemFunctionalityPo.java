package com.yizhuoyan.shidao.platform.po;

import com.yizhuoyan.shidao.common.util.PlatformUtil;
import com.yizhuoyan.shidao.common.validatation.MaxLength;
import com.yizhuoyan.shidao.common.validatation.MustIn;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
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

}
