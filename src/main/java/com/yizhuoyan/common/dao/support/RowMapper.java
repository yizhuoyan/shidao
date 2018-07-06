package com.yizhuoyan.common.dao.support;

import java.sql.ResultSet;

/**
 * Created by Administrator on 2017/11/2.
 */
@FunctionalInterface
public interface RowMapper<T>{

T map(ResultSet rs) throws Exception;
}
