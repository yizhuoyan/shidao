package com.yizhuoyan.common.dao.support;

import com.yizhuoyan.common.dao.CRUDDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public abstract class SingleTableDaoSupport<T> extends JDBCUtil implements CRUDDao<T> {
    final protected Logger log;
    final protected String tableName;
    final protected String idColumn;
    final protected String[] columnArray;
    final protected String columns;
    @Autowired
    private DataSource dataSource;

    public SingleTableDaoSupport(String tableName, String columns) {
        this(tableName, columns, false);
    }

    /**
     * @param tableName   b表名
     * @param columns     所有列，以逗号隔开，主键必须放在第一位
     * @param sortColumns 是否按照字符串升序顺序排列列名在sql中
     */
    public SingleTableDaoSupport(String tableName, String columns, boolean sortColumns) {
        this.tableName = tableName;
        this.columnArray = columns.split(",");
        this.idColumn = columnArray[0];
        if (sortColumns) {
            Arrays.sort(this.columnArray);
        }
        this.columns = join(this.columnArray, ",");
        log = LoggerFactory.getLogger(this.getClass());
    }

    private static String join(String[] arr, String join) {
        StringBuilder result = new StringBuilder(arr[0]);
        for (int i = 1, z = arr.length; i < z; i++) {
            result.append(join).append(arr[i]);
        }
        return result.toString();
    }

    protected String generateSelectColumns(String tableAlias) {
        StringBuilder result = new StringBuilder(columns.length() + columnArray.length * (tableAlias.length() + 1) + 2);

        for (int i = 0, z = columnArray.length; i < z; i++) {
            result.append(tableAlias).append(".").append(columnArray[i]).append(",");
        }

        result.setCharAt(result.length() - 1, ' ');
        return result.toString();
    }

    protected String generateSelectColumns() {
        return columns;
    }

    @Override
    public final void insert(T entity) throws Exception {
        try (PreparedStatement ps = getConnection().prepareStatement(
                generateInsertSQL(tableName, columns))) {
            this.obj2row(ps, entity);
            log.debug("执行insert：" + ps);
            ps.executeUpdate();
        }
    }

    @Override
    public int delete(String column, Object value) throws Exception {
        return executeUpdateSql(Sql.deleteFrom(tableName).where(column, "=?"), value);
    }




    @Override
    public void update(Serializable id, Map<String, Object> columns) throws Exception {
        if (columns.size() == 0) return;
        // 2创建sql语句对象
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tableName).append(" set ");
        // 保持参数值
        List<Object> values = new ArrayList<>(columns.size() + 1);
        for (Map.Entry<String, Object> entry : columns.entrySet()) {
            sql.append(entry.getKey()).append("=?,");
            values.add(entry.getValue());
        }
        sql.setCharAt(sql.length() - 1, ' ');
        sql.append(" where ").append(idColumn).append("=?");
        values.add(id);

        executeUpdateSql(sql, values.toArray());

    }

    @Override
    public void update(Serializable id, String column, Object value) throws Exception {
        Sql sql = Sql.updateTable(tableName)
                .set(column+"=?")
                .where(idColumn, "=?");
        executeUpdateSql(sql, value, id);
    }

    @Override
    public T select(String column, Object value) throws Exception {
        Sql sql = Sql.select(generateSelectColumns())
                .from(tableName)
                .where(column, "=?")
                .limit(0, 1);
        return executeQuerySql(sql, this::row2obj, value);
    }

    @Override
    public boolean exist(String column, Object value) throws Exception {
        Sql sql = Sql.selectCount("1")
                .from(tableName)
                .where(column, "=?");
        return executeCountSql(sql, value) > 0;
    }



    @Override
    public List<T> selectsByLike(SelectLikePo po) throws Exception {
        String[] columnArr = po.getColumns().split(",");
        Sql sql = Sql.select(generateSelectColumns())
                .from(tableName)
                .where(po.getWhereSql())
                .andWhere(generateLikeSql(columnArr, po.getLikeValue()))
                .orderBy(po.getOrderBy());
        String key=po.getLikeValue();
        if(key==null){
            return executesQuerySql(sql, this::row2obj,po.getWhereSqlParameters());
        }else{
            key = "%" + key + "%";
            Object[] whereSqlParamArr=po.getWhereSqlParameters();
            int whereSqlParamLength=whereSqlParamArr.length;

            Object[] paramArr = new String[columnArr.length+whereSqlParamLength];
            System.arraycopy(whereSqlParamArr,0,paramArr,0,whereSqlParamLength);
            for (int i =paramArr.length; i-->whereSqlParamLength;paramArr[i]=key);
            return executesQuerySql(sql, this::row2obj, paramArr);
        }

    }



    @Override
    public int selectsByLikeOnPagination(List<T> pageData,SelectLikePo po) throws Exception {
        //the total rows
        int total = 0;
        //be liked columns
        String[] columnArr = po.getColumns().split(",");
        String key=po.getLikeValue();
        String whereLikeSql = generateLikeSql(columnArr, key);

        Sql countSql = Sql.selectCount("1")
                .from(tableName)
                .where(po.getWhereSql())
                .andWhere(whereLikeSql);

        Object[] params = po.getWhereSqlParameters();
        if (key != null) {
            int whereSqlParamLength=params.length;
            Object[] paramsNew = new String[columnArr.length+whereSqlParamLength];
            System.arraycopy(params,0,paramsNew,0,whereSqlParamLength);
            key = "%" + key + "%";
            for (int i =paramsNew.length; i-->whereSqlParamLength;paramsNew[i]=key);
            params=paramsNew;
        }
        total = executeCountSql(countSql, params);
        if (total > 0) {
            // 执行分页查询
            Sql querySql = Sql.select(generateSelectColumns())
                    .from(tableName)
                    .where(po.getWhereSql())
                    .andWhere(whereLikeSql)
                    .orderBy(po.getOrderBy())
                    .limit((po.getPageNo() - 1) * po.getPageSize(), po.getPageSize());

            executesQuerySql(querySql, this::row2obj, pageData, params);

        }
        return total;
    }

    public List<T> selects(String orderBys) throws Exception {
        Sql sql = Sql.select(generateSelectColumns())
                .from(this.tableName)
                .orderBy(orderBys);
        return executesQuerySql(sql, this::row2obj);

    }

    @Override
    public List<T> selects(String column, Object value,String orderBy) throws Exception {
        Sql sql = Sql.select(generateSelectColumns())
                .from(tableName)
                .where(column, "=?")
                .orderBy(orderBy);

        return executesQuerySql(sql, this::row2obj, value);
    }

    public Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }



    protected final void executeInsert(String tableDesc, Object... values) throws Exception {
        executeInsert(getConnection(), tableDesc, values);
    }

    protected final int executeDelete(String tableDesc, Object... values) throws Exception {
        return executeDelete(getConnection(), tableDesc, values);
    }


    protected final T executeQuerySql(Object sql, RowMapper<T> mapper, Object... params) throws Exception {

        return executeQuerySql(getConnection(), sql, mapper, params);
    }


    protected final int executeCountSql(Object sql, Object... params) throws Exception {
        return executeCountSql(getConnection(), sql, params);
    }

    protected final List<T> executesQuerySql(Object sql, RowMapper<T> mapper, Object... params) throws Exception {
        return executesQuerySql(getConnection(), sql, mapper, params);
    }

    protected final void executesQuerySql(Object sql, RowMapper<T> mapper, List<T> result, Object... params) throws Exception {
        executesQuerySql(getConnection(), sql, mapper, result, params);
    }

    protected final int executeUpdateSql(Object sql, Object... params) throws Exception {
        return executeUpdateSql(getConnection(), sql, params);
    }

    protected final int executeInsertSql(Object sql, Object... params) throws Exception {
        return executeUpdateSql(getConnection(), sql, params);
    }

    protected final int executeDeleteSql(Object sql, Object... params) throws Exception {
        return executeUpdateSql(getConnection(), sql, params);
    }


    protected abstract T row2obj(ResultSet rs) throws Exception;

    protected abstract void obj2row(PreparedStatement ps, T e) throws Exception;


}
