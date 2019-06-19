package com.liu.service.impl;

import com.liu.mapper.DataMapper;
import com.liu.po.Community;
import com.liu.po.Street;
import com.liu.service.DataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: DataServiceImpl
 * @author: yu.liu
 * @date: 2019/6/19 11:13
 * @description:
 */
@Service
public class DataServiceImpl implements DataService {

    @Resource
    private DataMapper dataMapper;
    /**
     * 新增街道数据
     *
     * @param street
     */
    @Override
    public void insert(Street street) {
       dataMapper.insert(street);
    }

    /**
     * 查找已经存在的街道名称
     *
     * @return
     */
    @Override
    public List<String> findStreetName() {
        return dataMapper.findStreetName();
    }

    /**
     * 新增社区信息
     *
     * @param community
     */
    @Override
    public void insertCom(Community community) {
        dataMapper.insertCom(community);
    }
}
