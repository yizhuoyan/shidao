package com.yizhuoyan.shidao.common.exception;


import java.util.ArrayList;
import java.util.List;

/**
 * 参数验证异常
 *
 * @author Administrator
 */
public class ParameterException extends RuntimeException {
    /**
     * 1个或多个异常消息
     */
    private List<String> messages;


    public ParameterException(String... messages) {
        this.messages=new ArrayList<>(messages.length);
        for(String m:messages){
            this.messages.add(m);
        }
    }

    public ParameterException(List<String> messages) {
        if(messages==null)throw new NullPointerException();
        this.messages = messages;
    }

    public List<String> getMessages() {
        return this.messages;
    }
    @Override
    public String getMessage() {

        return this.messages.isEmpty() ? null: this.messages.get(0);
    }
}
