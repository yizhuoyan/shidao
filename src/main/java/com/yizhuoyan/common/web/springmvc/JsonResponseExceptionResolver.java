package com.yizhuoyan.common.web.springmvc;

import com.yizhuoyan.common.dto.JsonResponse;
import com.yizhuoyan.common.exception.ParameterException;
import com.yizhuoyan.common.exception.PlatformException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/11/22 0022.
 */
public class JsonResponseExceptionResolver implements HandlerExceptionResolver,MessageSourceAware {
    private MessageSource messageSource;
    private final String defaultCode="fatal-error";
    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
        try {
            List<String> codes=new ArrayList<>();
            //spring参数绑定异常
            if(ex instanceof BindException){
                BindException be=(BindException) ex;
                List<FieldError> fieldErrors = be.getFieldErrors();
                for(FieldError error:fieldErrors){
                    codes.add("must-type-match-"+be.getFieldType(error.getField()).getName()+"."+error.getField());
                }
                //输入异常
            }else if(ex instanceof ParameterException){
                ParameterException pe=(ParameterException) ex;
                List<String> messages=pe.getMessages();
                codes=messages;
                //业务异常
            }else if (ex instanceof PlatformException) {
                codes.add(ex.getMessage());
            }else{
                //系统异常
                codes.add(defaultCode);
            }

            resp.setContentType("application/json;charset=utf-8");
            try (PrintWriter writer = resp.getWriter()) {
                String[] messages=new String[codes.size()];
                for(int i=0,z=messages.length;i<z;i++){
                    messages[i]=i18nMessage(codes.get(i));
                }
                writer.write(JsonResponse.fail(codes.toArray(),messages).toString());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * code格式 异常类型.发生字段1.发生字段2(约束值1，值2)...
     * @param code
     * @return
     */
    private String i18nMessage(String code){
        //消息代号
        String messageCode=null;
        //消息参数
        List<String> messageArgs=new ArrayList<>();
        //消息参数开始位置
        int argsIndex=code.indexOf('(');
        if(argsIndex==-1){
            String[] codeAndfields=code.split("\\.");
            messageCode=codeAndfields[0];
            for(int i=1;i<codeAndfields.length;i++){
                messageArgs.add(codeAndfields[i]);
            }
        }else{
            String[] codeAndfields=code.substring(0,argsIndex).split("\\.");
            messageCode=codeAndfields[0];
            for(int i=1;i<codeAndfields.length;i++){
                messageArgs.add(codeAndfields[i]);
            }
            messageArgs.addAll(Arrays.asList(code.substring(argsIndex+1,code.lastIndexOf(')')).split(",")));
        }
        System.out.println(messageCode+"-"+messageArgs);
        //i18n消息
        String message=messageSource.getMessage(messageCode,messageArgs.toArray(),null);
        return message;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource=messageSource;
    }
}
