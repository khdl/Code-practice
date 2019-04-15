package com.liu;

import java.io.*;
import java.util.Base64;

/**
 * @ClassName: Base64Utils
 * @Auther: yu
 * @Date: 2019/4/15 16:31
 * @Description: 编码解码工具包
 */
public class Base64Utils {
    /**
     * 文件读取缓冲区大小
     */
    private  static  final  int CACHE_SIZE = 1024;

    /**
     * 字符串解码为二进制数据
     * @param base64
     * @return
     */
    public static  byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    /**
     * 二进制数据编码为BASE64字符串
     * @param bytes
     * @return
     */
    public static String encode(byte[] bytes){
         return  new String(Base64.getEncoder().encode(bytes));
    }

    /**
     * 将文件编码为BASE64字符串
     * @param filePath
     * @return
     */
    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToBytes(filePath);
        return  encode(bytes);
    }

    /**
     * 文件转二进制数组
     * @param filePath
     * @return
     */
    private static byte[] fileToBytes(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if(file.exists()){
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while((nRead = inputStream.read(cache)) != -1){
                outputStream.write(cache, 0 , nRead);
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();
            data = outputStream.toByteArray();
        }
        return  data;
    }

    /**
     * BASE64字符串转回文件
     * @param filePath
     * @param base64
     */
    public  static  void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }

    /**
     * 二进制数据写文件
     * @param bytes
     * @param filePath
     */
    private static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = in.read(cache)) != -1) {
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }
}
