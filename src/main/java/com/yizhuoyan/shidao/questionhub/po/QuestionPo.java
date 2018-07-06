package com.yizhuoyan.shidao.questionhub.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Data
public class QuestionPo {
    @NotBlank
    private String content;
    private int difficult;
    @NotBlank
    private String kindId;
    private String createUserId;

}
