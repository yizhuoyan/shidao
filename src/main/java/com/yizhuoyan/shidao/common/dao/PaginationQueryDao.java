package com.yizhuoyan.shidao.common.dao;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */
public interface PaginationQueryDao<T>{

    int selectsLike(String key, String likeColumns, String orderBy, int pageNo, int pageSize, List<T> pageData) throws Exception;

}
