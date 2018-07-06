package com.yizhuoyan.shidao.common.web.springmvc;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlatformDispatcherServlet extends DispatcherServlet{


public PlatformDispatcherServlet(){

  this.setDetectAllHandlerMappings(true);
  this.setDetectAllHandlerAdapters(true);
  this.setDetectAllHandlerExceptionResolvers(false);
  this.setDetectAllViewResolvers(false);
  this.setDispatchOptionsRequest(false);
  this.setDispatchTraceRequest(false);

}




@Override
protected void doDispatch(HttpServletRequest request,
                          HttpServletResponse response) throws Exception{
  request.setCharacterEncoding("utf-8");
  response.setCharacterEncoding("utf-8");
  super.doDispatch(request, response);
}


}
