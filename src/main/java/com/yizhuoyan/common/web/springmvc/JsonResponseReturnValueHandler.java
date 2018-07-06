package com.yizhuoyan.common.web.springmvc;

import com.yizhuoyan.common.dto.JsonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/10/23.
 */
public class JsonResponseReturnValueHandler implements HandlerMethodReturnValueHandler{

@Override
public boolean supportsReturnType(MethodParameter mp){
  return mp.getParameterType()==JsonResponse.class;
}

@Override
public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception{
  modelAndViewContainer.setRequestHandled(true);
  if(o!=null){
    JsonResponse result = (JsonResponse)o;
    HttpServletResponse resp = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
    resp.setContentType("application/json;charset=utf-8");
    try(PrintWriter writer = resp.getWriter()){
      writer.write(result.toString());
    }
  }

}
}
