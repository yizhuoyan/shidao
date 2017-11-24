package com.yizhuoyan.shidao.platform.po;

import com.yizhuoyan.shidao.common.validatation.MaxLength;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
