package com.yizhuoyan.common.exception;


import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * 参数验证异常
 *
 * @author Administrator
 */
public class ParameterException extends PlatformException {
    /**
     * 1个或多个异常消息
     */
    private final List<String> messages;


    public ParameterException(String... messages) {
        this(Arrays.asList(messages));
    }

    public ParameterException(@NonNull List<String> messages) {
        super(joinMessages(messages));
        this.messages = messages;
    }

    private static String joinMessages(List<String> messages) {
        if (messages.size() > 0) {
            StringBuilder result = new StringBuilder();
            for (String m : messages) {
                result.append(m).append(',');
            }
            return result.deleteCharAt(result.length() - 1).toString();
        }
        return "";
    }

    public List<String> getMessages() {
        return this.messages;
    }

}
