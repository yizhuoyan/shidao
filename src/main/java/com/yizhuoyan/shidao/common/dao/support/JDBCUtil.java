package com.yizhuoyan.shidao.common.dao.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/2.
 */
public class JDBCUtil{
static final protected Logger log = LoggerFactory.getLogger(JDBCUtil.class);

public static final Timestamp toSqlTimestamp(Date d){
  if(d==null) return null;
  return new Timestamp(d.getTime());
}
  public static final Timestamp toSqlTimestamp(Instant d){
    if(d==null) return null;
    return Timestamp.from(d);
  }
public static final Instant toInstant(Timestamp t){
    if(t==null) return null;
    return t.toInstant();
}

public static final java.sql.Date toSqlDate(Date d){
  if(d==null) return null;
  return new java.sql.Date(d.getTime());
}

public static final String generateInsertSQL(String table, String columns){
  StringBuilder sql = new StringBuilder();
  sql.append("insert into ").append(table).append('(');
  String[] columnsArray = columns.split(",");
  int columnAmount = columnsArray.length;
  for(int i = 0; i<columnAmount; i++){
    sql.append(columnsArray[i]).append(',');
  }
  sql.setCharAt(sql.length()-1, ')');
  sql.append("values(");
  for(int i = columnAmount; i-->0; sql.append("?,")) ;
  sql.setCharAt(sql.length()-1, ')');

  return sql.toString();
}



public static final String generateLikeSql(String[] columnArr, String key){
  //组装wheresql
  StringBuilder sql = new StringBuilder();
  if(key!=null){
    sql.append("(")
        .append(columnArr[0]).append(" like ? ");
    for(int i = 1; i<columnArr.length; i++){
      sql.append(" or ").append(columnArr[i]).append(" like ? ");
    }
    sql.append(")");
  }
  return sql.toString();
}


public static final void executeInsert(Connection connection, String tableDesc, Object... values) throws Exception{
  String tableName = tableDesc.substring(0, tableDesc.indexOf("("));
  String columns = tableDesc.substring(tableName.length()+1, tableDesc.lastIndexOf(")"));
  try(PreparedStatement ps = connection.prepareStatement(
      generateInsertSQL(tableName, columns))){
    int i = values.length;
    while(i-->0){
      ps.setObject(i+1, values[i]);
    }
    log.debug("执行新增:"+ps);
    ps.executeUpdate();
  }
}

public static final int executeDelete(Connection connection, String tableDesc, Object... values) throws Exception{
  String tableName = tableDesc.substring(0, tableDesc.indexOf("("));
  String columns = tableDesc.substring(tableName.length()+1, tableDesc.lastIndexOf(")"));

  StringBuilder sql = new StringBuilder("delete from ");
  sql.append(tableName).append(" where ");
  String[] columnsArr = columns.split(",");
  sql.append(columnsArr[0]).append("=? ");
  for(int i = 1; i<columnsArr.length; i++){
    sql.append(" and ").append(columnsArr[i]).append("=?");
  }
  return executeUpdateSql(connection, sql, values);
}


public static final <R> R executeQuerySql(Connection connection, Object sql, RowMapper<R> mapper, Object... params) throws Exception{
  try(PreparedStatement ps = connection.prepareStatement(sql.toString())){
    setPreparedStatementParameter(ps, params);
    log.debug(String.format("执行sql:%s,\n\t参数:%s", sql, Arrays.toString(params)));
    ResultSet rs = ps.executeQuery();
    if(rs.next()){
      return mapper.map(rs);
    }
    return null;
  }
}

public static final int executeCountSql(Connection connection, Object sql, Object... params) throws Exception{
  try(PreparedStatement ps = connection.prepareStatement(sql.toString())){
    setPreparedStatementParameter(ps, params);
    log.debug(String.format("执行sql:%s,\n\t参数:%s", sql, Arrays.toString(params)));
    ResultSet rs = ps.executeQuery();
    if(rs.next()){
      return rs.getInt(1);
    }
    return -1;
  }
}

public static final <R> List<R> executesQuerySql(Connection connection, Object sql, RowMapper<R> mapper, Object... params) throws Exception{
  List<R> result = new LinkedList<R>();
  executesQuerySql(connection, sql, mapper, result, params);
  return result;
}

public static final <R> void executesQuerySql(Connection connection, Object sql, RowMapper<R> mapper, List<R> result, Object... params) throws Exception{
  try(PreparedStatement ps = connection.prepareStatement(sql.toString())){
    setPreparedStatementParameter(ps, params);
    log.debug(String.format("执行sql:%s,\n\t参数:%s", sql, Arrays.toString(params)));
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
      result.add(mapper.map(rs));
    }
  }
}

public static final int executeUpdateSql(Connection connection, Object sql, Object... params) throws Exception{
  try(PreparedStatement ps = connection.prepareStatement(sql.toString())){
    setPreparedStatementParameter(ps, params);
    log.debug(String.format("执行sql:%s,\n\t参数:%s", sql, Arrays.toString(params)));
    return ps.executeUpdate();
  }
}

private static void setPreparedStatementParameter(PreparedStatement ps, Object[] params) throws SQLException{
  if(params!=null){
    for(int i = params.length; i-->0; ){
      ps.setObject(i+1, params[i]);
    }
  }
}
}
