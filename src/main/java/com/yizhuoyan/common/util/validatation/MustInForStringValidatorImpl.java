package com.yizhuoyan.common.util.validatation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/24 0024.
 */
public class MustInForStringValidatorImpl implements ConstraintValidator<MustIn, String> {
    private Set<String> valueSet;
    @Override
    public void initialize(MustIn constraintAnnotation) {
        String[] inValueArr=constraintAnnotation.value().split("/");

        valueSet=new HashSet<>(inValueArr.length);
        for (String v:inValueArr){
            valueSet.add(v);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        value=value.trim();
        if(value.length()==0)return true;
        if(valueSet.contains(value)){
            return true;
        }
        return false;
    }
}
