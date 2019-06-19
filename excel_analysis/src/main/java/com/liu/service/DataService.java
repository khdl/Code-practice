package com.liu.service;

import com.liu.po.Community;
import com.liu.po.Street;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: DataService
 * @author: yu.liu
 * @date: 2019/6/19 11:12
 * @description: 数据处理服务
 */
@Service
public interface DataService {
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
