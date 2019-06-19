package com.liu.controller;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.liu.po.DataInfo;
import com.liu.util.DataExcelListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: ExcelController
 * @author: yu.liu
 * @date: 2019/6/18 17:22
 * @description:
 */

@Controller
@RequestMapping("/excel")
public class ExcelController {

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
            // 先导入街道信息
            insertStreet(datas);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertStreet(List<Object> datas) {
        //记录已经存在的街道名称（查询数据库已存在的街道）
        List<String> list = new ArrayList<>();
        for(Object data : datas){
            DataInfo info = (DataInfo) data;
            String name = info.getStrName();
            if(!list.contains(name)){
                list.add(name);
                //街道信息

            }
        }
    }
}
