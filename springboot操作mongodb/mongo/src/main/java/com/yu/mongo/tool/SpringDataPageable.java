package com.yu.mongo.tool;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Optional;

/**
 * @className: SpringDataPageable
 * @author: yu.liu
 * @date: 2019/8/6 13:35
 * @description: 分页参数类
 */
public class SpringDataPageable implements Serializable, Pageable {
    private static final long serialVersionUID = 1;
    //当前页
    private  Integer number;

    //当前页条数
    private  Integer size;

    //排序条件
    private  Sort sort;


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public boolean isPaged() {
        return false;
    }

    @Override
    public boolean isUnpaged() {
        return false;
    }

    @Override
    public int getPageNumber() {
        return getNumber();
    }

    @Override
    public int getPageSize() {
        return getSize();
    }

    @Override
    public long getOffset() {
        return (getNumber() -1) * getSize();
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return null;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Optional.empty();
    }
}
