package com.yizhuoyan.shidao.common.dao;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */
public interface QueryDao<T>{

List<T> selects(String column, Object value) throws Exception;


}
