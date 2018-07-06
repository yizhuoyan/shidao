package com.yizhuoyan.shidao.platform.po;

import com.yizhuoyan.shidao.common.validatation.MaxLength;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class SysRolePo {
    /**
     * 代号
     */
    @NotBlank
    @MaxLength(32)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @MaxLength(32)
    private String name;
    /**
     * 描述
     */
    @MaxLength(512)
    private String remark;





}
