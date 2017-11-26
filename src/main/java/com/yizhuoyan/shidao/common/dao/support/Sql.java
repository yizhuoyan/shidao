package com.yizhuoyan.shidao.common.dao.support;

/**
 * Created by Administrator on 2017/11/2.
 */
public class Sql {
    private final StringBuilder sql;
    private boolean hasWhere;
    private boolean hasFrom;


    private Sql() {
        this.sql = new StringBuilder();
    }

    public static Sql select(String s) {
        Sql sq = new Sql();
        sq.sql.append("select ").append(s);
        return sq;
    }


    public static Sql selectCount(String s) {
        Sql sq = new Sql();
        sq.sql.append("select count(").append(s).append(")");
        return sq;
    }

    public static Sql selectDistinct(String s) {
        Sql sq = new Sql();
        sq.sql.append("select distinct ").append(s);
        return sq;
    }

    public static Sql update(String s) {
        Sql sq = new Sql();
        sq.sql.append("update ").append(s);
        return sq;
    }

    public static Sql deleteFrom(String s) {
        Sql sq = new Sql();
        sq.sql.append("delete from ").append(s);
        return sq;
    }

    public static Sql insertInto(String s) {
        Sql sq = new Sql();
        sq.sql.append("insert into ").append(s);
        return sq;
    }

    public Sql andSet(String column, String value) {
        sql.append(",").append(column).append('=').append(value);
        return this;
    }

    public Sql set(String column, String value) {
        sql.append(" set ").append(column).append('=').append(value);
        return this;
    }


    public Sql from(String table) {
        if(hasFrom){
            sql.append(",").append(table);
        }else{
            sql.append(" from ").append(table);
            hasFrom=true;
        }
        return this;
    }
    public Sql from(String table,String alias) {
        if(hasFrom){
            sql.append(",").append(table).append(" ").append(alias);
        }else{
            sql.append(" from ").append(table).append(" ").append(alias);
            hasFrom=true;
        }
        return this;
    }



    private boolean isBlank(String s) {
        return s == null || (s.trim()).length() == 0;
    }

    private boolean isNotBlank(String s) {
        return s != null && (s.trim()).length() != 0;
    }




    public Sql where(String s) {
        if (isNotBlank(s)) {
            sql.append(" where ").append(s);
            hasWhere=true;
        }
        return this;
    }
    public Sql where(String column,String condition) {
        if (isNotBlank(column)) {
            sql.append(" where ").append(column).append(condition);
            hasWhere=true;
        }
        return this;
    }
    public Sql andWhere(String s) {
        if (isNotBlank(s)) {
            if(hasWhere){
                sql.append(" and ").append(s);
            }else{
                sql.append(" where ").append(s);
                hasWhere=true;
            }

        }
        return this;
    }
    public Sql andWhere(String column,String condition) {
        if (isNotBlank(column)) {
            if(hasWhere){
                sql.append(" and ").append(column).append(condition);
            }else{
                sql.append(" where ").append(column).append(condition);
                hasWhere=true;
            }

        }
        return this;
    }

    public Sql orWhere(String s) {
        if (isNotBlank(s)) {
            if(hasWhere){
                sql.append(" or ").append(s);
            }else{
                sql.append(" where ").append(s);
                hasWhere=true;
            }
        }
        return this;
    }
    public Sql orWhere(String column,String condition) {
        if (isNotBlank(column)) {
            if(hasWhere){
                sql.append(" or ").append(column).append(condition);
            }else{
                sql.append(" where ").append(column).append(condition);
                hasWhere=true;
            }
        }
        return this;
    }





    public Sql and(String s) {
        sql.append(" and ").append(s);
        return this;
    }

    public Sql between(String s) {
        sql.append(" between ").append(s);
        return this;
    }

    public Sql in(String s) {
        sql.append(" in ").append(s);
        return this;
    }

    public Sql join(String s) {
        sql.append(" join ").append(s);
        return this;
    }



    public Sql having(String s) {
        sql.append(" having ").append(s);
        return this;
    }

    public Sql groupBy(String s) {
        sql.append(" group by ").append(s);
        return this;
    }

    public Sql orderBy(String s) {
        if (isNotBlank(s)) {
            sql.append(" order by ").append(s);
        }
        return this;
    }

    public Sql limit(int offset, int size) {
        sql.append(" limit ").append(offset).append(",").append(size);
        return this;
    }

    public String toString() {
        return sql.toString();
    }


}
