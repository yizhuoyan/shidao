package com.yizhuoyan.common.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class AbstractFilterSupport implements Filter{
protected ServletContext application;

@Override
public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
    throws IOException, ServletException{
  try{
    this.doFilter((HttpServletRequest)req, (HttpServletResponse)resp, chain);
  }catch(Exception e){
    throw new ServletException(e);
  }
}

public abstract void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws Exception;


@Override
public final void init(FilterConfig config) throws ServletException{
  this.application = config.getServletContext();
  try{
    Method[] methods = this.getClass().getMethods();
    String methodName = null;
    for(Method method : methods){
      methodName = method.getName();
      if(methodName.startsWith("set")){
        String paramName = methodName2paramName(methodName);
        String paramValue = config.getInitParameter(paramName);
        method.invoke(this, paramValue);
      }
    }
  }catch(Exception e){
    throw new ServletException(e);
  }finally{
    init();
  }
}

private final String methodName2paramName(String methodName){
  char[] cs = methodName.toCharArray();
  cs[3] += 32;
  return new String(cs, 3, cs.length-3);
}

public void init(){

}

@Override
public void destroy(){

}
}
