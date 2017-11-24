/**
 * shidao
 * FunctionTempldate.java
 * 2016年1月20日
 */
package com.yizhuoyan.shidao.common.validatation;

import com.yizhuoyan.shidao.common.exception.ParameterException;
import com.yizhuoyan.shidao.common.exception.PlatformException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户输入验证
 *
 * @author root@yizhuoyan.com
 */

public class ParameterValidator {

 static private void handleIllegalParameter(String name,String errorKind,List<String> errors,Object... args){
     StringBuilder code=new StringBuilder(errorKind);
     code.append(".").append(name);
     if(args.length>0) {
         code.append("(");
         for (Object arg : args) {
             code.append(arg).append(',');
         }
         code.setCharAt(code.length()-1,')');
     }
     if(errors!=null){
        errors.add(code.toString());
     }else{
         throw  new ParameterException(code.toString());
     }
 }

    static public String $(String name,String value){
        return checkNotBlank(name,value,null);
    }
    static public String $(String name,String value,List<String> errors){
        return checkNotBlank(name,value,errors);
    }
    static public String checkNotBlank(String name,String value){
        return checkNotBlank(name,value,null);
    }

    static public String checkNotBlank(String name,String input,List<String> errors){

        if(input==null||(input = input.trim()).length()==0){
            handleIllegalParameter(name,"must-not-blank",errors);
        }
        return input;
    }
    public static int checkInteger(String name, String input){
        return checkInteger(input,name,null);

    }
 public static int checkInteger(String name, String input,List<String> errors){
        if(input!=null&&(input = input.trim()).length()!=0) {
            try {
                return Integer.parseInt(input);
            } catch (Exception e) {}
        }
        handleIllegalParameter(name,"must-integer", errors);
        return 0;
}

 public static void checkNotEquals(String inputA, String inputB,List<String> errors){
  if(inputA==null ? null==inputB : inputA.equals(inputB)){
      handleIllegalParameter("", "must-not-equals",errors);
  }
}

 public static void checkEquals(String inputA, String inputB,List<String> errors){
  if(inputA==null ? null!=inputB : !inputA.equals(inputB)){
      handleIllegalParameter("","must-equals",errors);
  }
}

 public static void checkLessThan(String name,String input, int max,List<String> errors){
  if(input.length()>max){
      handleIllegalParameter(name, "must-less-than",errors,max);
  }
}
    public static void checkGreatThan(String name,String input,int min,List<String> errors){
        if(input.length()<min){
            handleIllegalParameter(name, "must-great-than",errors,min);
        }
    }
    public static void checkBetween(String name,String input, int min,int max,List<String> errors){
        int length=input.length();
        if(min>length||length>max){
            handleIllegalParameter(name, "must-between",errors,min,max);
        }
    }

}
