package com.yizhuoyan.common.web.springmvc;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 增强RequestMappding,实现如果不在方法上使用@RequestMapiing,
 * 则默认方法名作用映射路径
 *
 * @author root@yizhuoyan.com;
 */
public class RequestMappingHandlerMappingAdivice extends RequestMappingHandlerMapping{

public RequestMappingHandlerMappingAdivice(){
  this.setUseTrailingSlashMatch(false);
}

@Override
protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType){
  RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
  if(info==null){
    RequestCondition methodCondition = getCustomMethodCondition(method);
    //使用方法名作用请求路径
    info = createRequestMappingInfo(method, methodCondition);
    if(info!=null){
      RequestMapping typeAnnotation = AnnotationUtils
                                          .findAnnotation(handlerType, RequestMapping.class);
      if(typeAnnotation!=null){
        RequestCondition typeCondition = getCustomTypeCondition(handlerType);

        info = createRequestMappingInfo(typeAnnotation, typeCondition)
                   .combine(info);
      }
    }
  }
  return info;
}

/**
 * 使用方法名作用请求路径
 *
 * @param method
 * @param methodCondition
 * @return
 */
private final RequestMappingInfo createRequestMappingInfo(Method method, RequestCondition methodCondition){
  if(!Modifier.isPublic(method.getModifiers())) return null;
  String patterns[] = new String[]{method.getName()};
  return new RequestMappingInfo(new PatternsRequestCondition(patterns,
                                                                getUrlPathHelper(), getPathMatcher(), this.useSuffixPatternMatch(),
                                                                this.useTrailingSlashMatch(), this.getFileExtensions()),
                                   null,
                                   null,
                                   null,
                                   null, null, methodCondition);
}

}
