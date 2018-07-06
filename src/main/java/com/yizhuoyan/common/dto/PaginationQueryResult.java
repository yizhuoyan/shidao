package com.yizhuoyan.common.dto;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class PaginationQueryResult<T> implements Serializable {
    private int totalRows = -1;
    private int totalPages = -1;
    private int pageSize = -1;
    private int pageNo = -1;
    private List<T> rows;

    public PaginationQueryResult() {

    }
    public PaginationQueryResult(long totalSize, List<T> rows) {
        this.setTotalRows(totalSize);
        this.rows = rows;
    }

    public List<T> getRows() {
        return rows;
    }


    public int getTotalRows() {
        return this.totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = (int) totalRows;
    }

    public int getTotalPages() {
        if (this.totalRows <= 0) return 0;
        int tp = this.totalPages;
        if (tp == -1) {
            int tr = this.getTotalRows();
            int ps = this.getPageSize();
            tp = tr / ps;
            if ((tr % ps) != 0) {
                tp++;
            }
        }
        return tp;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Map toJSON(Function<T, Map> transfer) {
        Map<String, Object> map = new HashMap<>();
        if (this.totalRows > 0) {
            int z = this.rows.size();
            Map[] arr = new Map[z];
            while (z-- > 0) {
                arr[z] = transfer.apply(this.rows.get(z));
            }
            map.put("rows", arr);
            map.put("totalPages", this.getTotalPages());
        } else {
            map.put("rows", new Object[0]);
            map.put("totalPages", 0);
        }
        map.put("pageNo", this.getPageNo());
        map.put("pageSize", this.getPageSize());
        map.put("totalRows", this.getTotalRows());


        return map;
    }

}
