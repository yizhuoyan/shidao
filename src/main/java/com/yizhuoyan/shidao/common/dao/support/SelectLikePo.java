package com.yizhuoyan.shidao.common.dao.support;

/**
 * Created by ben on 11/25/17.
 */
public class SelectLikePo {
    private String whereSql;
    private Object[] whereSqlParameters=new Object[0];
    private final String columns;
    private final String likeValue;
    private int pageSize;
    private int pageNo;
    private String orderBy;

    public SelectLikePo(String columns, String likeValue) {
        this.columns = columns;
        this.likeValue = likeValue;
    }

    public static SelectLikePo of(String columns,String likeValue){
        return new SelectLikePo(columns,likeValue);
    }


    public String getWhereSql() {
        return whereSql;
    }

    public SelectLikePo setWhereSql(String whereSql,Object... params) {
        if(whereSql!=null&&whereSql.length()!=0) {
            this.whereSql = whereSql;
            this.whereSqlParameters = params;
        }
        return this;
    }


    public Object[] getWhereSqlParameters() {
        return whereSqlParameters;
    }


    public String getColumns() {
        return columns;
    }


    public String getLikeValue() {
        return likeValue;
    }


    public int getPageSize() {
        return pageSize;
    }

    public SelectLikePo setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public SelectLikePo setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public SelectLikePo setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }
}
