package com.liu.mapper;

import com.liu.po.Community;
import com.liu.po.Street;

import java.util.List;

/**
 * @className: DataMapper
 * @author: yu.liu
 * @date: 2019/6/19 11:15
 * @description:
 */
public interface DataMapper {
    /**
     * 新增街道数据
     * @param street
     */
    void insert(Street street);

    /**
     * 查找已经存在的街道名称
     * @return
     */
    List<String> findStreetName();

    /**
     * 新增社区信息
     * @param community
     */
    void insertCom(Community community);
}
