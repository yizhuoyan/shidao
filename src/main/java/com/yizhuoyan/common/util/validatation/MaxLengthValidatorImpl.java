package com.yizhuoyan.common.util.validatation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Administrator on 2017/11/24 0024.
 */
public class MaxLengthValidatorImpl implements ConstraintValidator<MaxLength, String> {
    private int max;
    @Override
    public void initialize(MaxLength constraintAnnotation) {
        max=constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        if((value.trim().length()>max)){
            return false;
        }
        return true;
    }
}
