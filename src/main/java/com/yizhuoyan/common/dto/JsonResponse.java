package com.yizhuoyan.common.dto;

import com.alibaba.fastjson.JSON;
import com.yizhuoyan.common.util.KeyValueMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class JsonResponse {
    /**
     * 应答处理结果,如果成功为ok
     */
    private final boolean ok;
    /**
     * 成功的数据
     */
    public final Object data;
    /**
     * 失败的代号
     */
    private final Object[] errors;
    /**
     * 失败的消息描述，和代号一一对应
     */
    private final String[] messages;


    public JsonResponse(Object data) {
        this.ok = true;
        this.data = data;
        this.errors = null;
        this.messages = null;
    }

    public JsonResponse(Object[] errors, String[] messages) {
        this.ok = false;
        this.data =null;
        this.errors = errors;
        this.messages = messages;
    }

    public static JsonResponse ok() {
        return ok(null);
    }



    public static JsonResponse ok(Object data) {

        return new JsonResponse(data);
    }


    public static <T> JsonResponse ok(List<T> list, Function<T, Object> transfer) {
        if (list == null) return ok();
        Object[] arr = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = transfer.apply(list.get(i));
        }
        return ok(arr);
    }
    public static <T> JsonResponse ok(T[] arr, Function<T, Object> transfer) {
        if (arr == null) return ok();
        Object[] newArr=new Object[arr.length];
        for (int i =arr.length; i-->0;) {
            newArr[i] = transfer.apply(arr[i]);
        }
        return ok(newArr);
    }


    public static JsonResponse fail(Object failCode, String message) {
        return new JsonResponse(new Object[]{failCode}, new String[]{message});
    }

    public static JsonResponse fail(Object[] failCodes, String[] messages) {
        return new JsonResponse(failCodes, messages);
    }

    public Map toJSON() {
        KeyValueMap json = new KeyValueMap(2);
        if(this.ok){
            json.put("ok", true);
            if (this.data != null) {
                json.put("data", this.data);
            }
        }else{
            json.put("errors", this.errors);
            json.put("messages", this.messages);
        }
        return json;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this.toJSON(),true);
    }




}
