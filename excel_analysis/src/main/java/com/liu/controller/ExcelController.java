package com.liu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;

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
            FileInputStream  fis = (FileInputStream) file.getInputStream();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
