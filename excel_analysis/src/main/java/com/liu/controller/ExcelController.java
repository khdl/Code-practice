package com.liu.controller;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.liu.po.Community;
import com.liu.po.DataInfo;
import com.liu.po.Street;
import com.liu.service.DataService;
import com.liu.util.DataExcelListener;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @className: ExcelController
 * @author: yu.liu
 * @date: 2019/6/18 17:22
 * @description:
 */

@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Resource
    private DataService dataService;
    /**
     * 上传的excel解析数据
     * @param file
     */
    @RequestMapping("/upload")
    @ResponseBody
    public  void fileUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request){
        try {
           InputStream inputStream = file.getInputStream();

            DataExcelListener listener = new DataExcelListener();
            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
            excelReader.read(new Sheet(1,5, DataInfo.class));

            List<Object> datas = listener.getDatas();
            // 街道信息
            insertStreet(datas);
            //导入社区信息
            insertCommunity(datas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertCommunity(List<Object> datas) {
        for(Object data : datas){
            DataInfo info = (DataInfo) data;
            Community community = new Community();
            community.setId(UUID.randomUUID().toString());
            community.setName(info.getComName());
            //数据中如果没有街道编码，则要根据规则生成，根据街道名称去数据库里查找数据
            community.setPreCode(info.getStrCode());
            community.setCommunityCode(info.getComCode());
            community.setSysCreateTime(new Date());
            community.setCommunityAddr(info.getComAddr());
            dataService.insertCom(community);
        }
    }

    private void insertStreet(List<Object> datas) {
        //记录已经存在的街道名称（查询数据库已存在的街道）
        List<String> list = dataService.findStreetName();
        for(Object data : datas){
            DataInfo info = (DataInfo) data;
            String name = info.getStrName();
            if(!list.contains(name)){
                list.add(name);
                //街道信息
                Street street = new Street();
                street.setId(UUID.randomUUID().toString());
                street.setName(name);
                street.setPreCode(info.getRegCode());
                street.setStreetCode(info.getStrCode());
                street.setStreetAddr(info.getStrAddr());
                street.setSysCreateTime(new Date());
/*                if(StringUtils.isEmpty(street.getStreetAddr())){
                    street.setStreetAddr("");
                }*/
                dataService.insert(street);
            }
        }
    }

}

