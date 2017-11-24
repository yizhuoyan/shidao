package com.yizhuoyan.shidao.common.dao.support;

/**
 * Created by Administrator on 2017/11/2.
 */
public class Sql{
private final StringBuilder sql;


private Sql(){
  this.sql = new StringBuilder();
}

public static Sql select(String s){
  Sql sq = new Sql();
  sq.sql.append("select ").append(s);
  return sq;
}

public static Sql selectCount(String s){
  Sql sq = new Sql();
  sq.sql.append("select count(").append(s).append(")");
  return sq;
}

public static Sql selectDistinct(String s){
  Sql sq = new Sql();
  sq.sql.append("select distinct ").append(s);
  return sq;
}

public static Sql update(String s){
  Sql sq = new Sql();
  sq.sql.append("update ").append(s);
  return sq;
}

public static Sql deleteFrom(String s){
  Sql sq = new Sql();
  sq.sql.append("delete from ").append(s);
  return sq;
}

public static Sql insertInto(String s){
  Sql sq = new Sql();
  sq.sql.append("insert into ").append(s);
  return sq;
}

public Sql setAnother(String column, String value){
  sql.append(",").append(column).append('=').append(value);
  return this;
}

public Sql set(String column, String value){
  sql.append(" set ").append(column).append('=').append(value);
  return this;
}


public Sql from(String s){
  sql.append(" from ").append(s);
  return this;
}

public Sql from(String... arr){
  sql.append(" from ");
  for(String s : arr){
    sql.append(s);
  }
  return this;
}

private boolean isBlank(String s){
  return s==null||(s.trim()).length()==0;
}

private boolean isNotBlank(String s){
  return s!=null&&(s.trim()).length()!=0;
}

public Sql where(String s){
  sql.append(" where ").append(s);
  return this;
}

public Sql whereIf(String s){
  if(isNotBlank(s)){
    sql.append(" where ").append(s);
  }
  return this;
}

public Sql where(String... arr){
  sql.append(" where ");
  for(String s : arr){
    sql.append(s);
  }
  return this;
}

public Sql like(String s){
  sql.append(" like ").append(s);
  return this;
}

public Sql or(String s){
  sql.append(" or ").append(s);
  return this;
}

public Sql and(String s){
  sql.append(" and ").append(s);
  return this;
}

public Sql between(String s){
  sql.append(" between ").append(s);
  return this;
}

public Sql in(String s){
  sql.append(" in ").append(s);
  return this;
}

public Sql join(String s){
  sql.append(" join ").append(s);
  return this;
}

public Sql on(String s){
  sql.append(" on ").append(s);
  return this;
}

public Sql having(String s){
  sql.append(" having ").append(s);
  return this;
}

public Sql groupBy(String s){
  sql.append(" group by ").append(s);
  return this;
}

public Sql orderBy(String s){
  sql.append(" order by ").append(s);
  return this;
}

public Sql orderByIf(String s){
  if(isNotBlank(s)){
    sql.append(" order by ").append(s);
  }
  return this;
}

public Sql limit(int offset, int size){
  sql.append(" limit ").append(offset).append(",").append(size);
  return this;
}

public String toString(){
  return sql.toString();
}


}
