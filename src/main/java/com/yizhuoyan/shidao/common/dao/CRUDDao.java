package com.yizhuoyan.shidao.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/20.
 */
public interface CRUDDao<T>{

void insert(T entity) throws Exception;

int delete(String column, Object value) throws Exception;

void update(Serializable id, Map<String, Object> valueMap) throws Exception;

void update(Serializable id, String columnName, Object value) throws Exception;

boolean exist(String column, Object value) throws Exception;

T select(String column, Object value) throws Exception;

List<T> selectsLike(String key, String likeColumns, String orderBy) throws Exception;


}
