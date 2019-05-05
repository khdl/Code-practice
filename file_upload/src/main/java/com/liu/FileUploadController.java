package com.liu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.Iterator;

/**
 * @className: FileUploadController
 * @author: yu.liu
 * @date: 2019/5/5 11:25
 * @description:  上传文件控制器（测试后，方法1的速度太慢，一般不采用）
 */
@Controller
@RequestMapping("/file")
public class FileUploadController {

    /**
     * 通过流的方式上传文件
     * @param file
     * @return
     */
    @RequestMapping("/fileUpload1")
    public void fileUpload1(@RequestParam("file") CommonsMultipartFile file){
        long startTime = System.currentTimeMillis();
        System.out.println("filename:" + file.getOriginalFilename());

        try {
            OutputStream os = new FileOutputStream("D:/" + System.currentTimeMillis() + file.getOriginalFilename());
            InputStream is = file.getInputStream();
            int temp;
            while((temp = is.read()) != -1){
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("方法1运行的时间：" + String.valueOf(endTime - startTime) + "ms");
    }

    /**
     * 采用file.Transto 来保存上传的文件
     * @param file
     */
    @RequestMapping("/fileUpload2")
    public  void fileUpload2(@RequestParam("file") CommonsMultipartFile file){
        long startTime = System.currentTimeMillis();
        System.out.println("filename:" + file.getOriginalFilename());

        File newFile  = new File("D:/" + System.currentTimeMillis() + file.getOriginalFilename());
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("方法2运行的时间：" + String.valueOf(endTime - startTime) + "ms");
    }


    /**
     * 采用spring提供的上传文件的方法
     * @param request
     */
    @RequestMapping("/fileUpload3")
    public  void fileUpload3(HttpServletRequest request) throws Exception{
        long startTime = System.currentTimeMillis();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()){
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                System.out.println("filename:" + file.getOriginalFilename());
                if(file != null){
                    File newFile  = new File("D:/" + System.currentTimeMillis() + file.getOriginalFilename());
                    file.transferTo(newFile);
                }
            }
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法3的运行时间："+String.valueOf(endTime-startTime)+"ms");
    }
}
