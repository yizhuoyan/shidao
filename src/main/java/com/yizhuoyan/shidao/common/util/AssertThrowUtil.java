/**
 * shidao
 * FunctionTempldate.java
 * 2016年1月20日
 */
package com.yizhuoyan.shidao.common.util;

import com.yizhuoyan.shidao.common.exception.PlatformException;

import java.util.Collection;

/**
 * 数据验证工具类
 *
 * @author root@yizhuoyan.com
 */

public class AssertThrowUtil{


static private void throwException(String message, Object... args){
  throw new PlatformException(handleMessageArguments(message,args));
}

private static String handleMessageArguments(String message,Object... args){
    StringBuilder result=new StringBuilder(message);
    if(args.length>0){
        result.append("(");
        for (Object arg : args) {
            result.append(arg).append(',');
        }
        result.setCharAt(result.length()-1,')');
    }
    return result.toString();
}

static public void assertNull(String message, Object obj, Object... args){
  if(obj!=null) throwException(message, args);
}

static public void assertNotNull(String message, Object obj, Object... args){
  if(obj==null)
    throwException(message, args);
}

/**
 * 断言字符串是null或者空字符串或空白字符串
 *
 * @param message
 * @param s
 * @param args
 */
static public void assertBlank(String message, String s, Object... args){
  if(s!=null&&s.trim().length()!=0){
    throwException(message, args);
  }
}

static public String assertNotBlank(String message, String s,
                                    Object... args){
  if(s==null||(s = s.trim()).length()==0){
    throwException(message, args);
  }
  return s;
}


static public int assertInteger(String message, String intStr,
                                Object... args){
  if(intStr==null||intStr.trim().length()==0){
    throwException(message, args);
  }
  try{
    return Integer.parseInt(intStr);
  }catch(Exception e){
    throwException(message, args);
  }
  return 0;
}

static public void assertNotEquals(String message, Object a, Object b,
                                   Object... args){
  if(a==null ? null==b : a.equals(b)){
    throwException(message, args);
  }
}

static public void assertEquals(String message, Object a, Object b,
                                Object... args){
  if(a==null ? null!=b : !a.equals(b)){
    throwException(message, args);
  }
}

static public Collection assertEmpty(String message, Collection b,
                                     Object... args){
  if(b==null||b.size()!=0){
    throwException(message, args);
  }
  return b;
}

static public Collection assertNotEmpty(String message, Collection b,
                                        Object... args){
  if(b!=null&&b.size()==0){
    throwException(message, args);
  }
  return b;
}

static public void assertTrue(String message, boolean b, Object... args){
  if(!b){
    throwException(message, args);
  }
}

static public void assertFalse(String message, boolean b, Object... args){
  if(b){
    throwException(message, args);
  }
}
}
