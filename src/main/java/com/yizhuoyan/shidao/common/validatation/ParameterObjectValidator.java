/**
 * shidao
 * DtoValidater.java
 * 2016年3月28日
 */
package com.yizhuoyan.shidao.common.validatation;

import com.yizhuoyan.shidao.common.exception.ParameterException;
import com.yizhuoyan.shidao.common.exception.PlatformException;

import javax.validation.*;
import java.util.*;

/**
 * @author root@yizhuoyan.com
 */
public class ParameterObjectValidator{

private static final Validator VALIDATOR = getValidation();



private static Validator getValidation(){
    ValidatorFactory factory=Validation.buildDefaultValidatorFactory();
    return factory.getValidator();
}



public static <T> void throwIfFail(T t,
                                   Class<?>... groups){
  Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(t, groups);
  if(constraintViolations.size()!=0){
      List<String> messages=new ArrayList<>(constraintViolations.size());
     int argIndex;
     String errorKind;
     String errorField;
     String errorArgs="";
     String message;
    for(ConstraintViolation<?> violation : constraintViolations){
        message=violation.getMessage();
        errorField=violation.getPropertyPath().toString();
        if((argIndex=message.lastIndexOf("("))==-1){//无参数
            errorKind=message;
        }else{
            errorArgs=message.substring(argIndex);
            errorKind=message.substring(0,argIndex);
        }
        messages.add(errorKind+"."+errorField+errorArgs);
    }
    throw new ParameterException(messages);
  }
}

static public <T> Set<ConstraintViolation<T>> validateProperty(T t,
                                                               String propertyName, Class<?>... groups){
  return VALIDATOR.validateProperty(t, propertyName, groups);
}

static public <T> Set<ConstraintViolation<T>> validateValue(Class<T> t,
                                                            String propertyName, Object value, Class<?>... groups){
  return VALIDATOR.validateValue(t, propertyName, value, groups);
}


}
