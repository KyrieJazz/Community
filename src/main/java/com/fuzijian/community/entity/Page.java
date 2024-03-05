package com.fuzijian.community.entity;

import org.thymeleaf.standard.expression.GreaterLesserExpression;

/*
*   封装页面相关的信息
* */
public class Page {

    // 当前的页码
    private int current = 1;
    // 显示的页面上线
    private int limit = 10;
    // 数据的总数，用于计算总的页数
    private int rows;
    // 查询路径(用于复用分页的链接)
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        // 添加非法判断
        if (current >= 1){
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >= 1 && limit <= 100){
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0){
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // 数据库查询时需要使用的方法
    /*
    *   获取当前页面的起始行
    * */
    public int getOffset(){
        return (current * limit) - limit;
    }

    /*
    *   获取总的页数
    * */
    public int getTotal(){
        if(rows % limit == 0){
            return rows / limit;
        }
        else {
            return rows / limit + 1;
        }
    }

    /*
    *   获取起始页面
    * */
    public int getFrom(){
        int from = current - 2;
        return Math.max(from, 1);
    }

    public int getTo(){
        int to = current + 2;
        // 最后一页的值
        int total = getTotal();
        return Math.min(to, total);
    }
}
