package com.liu.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.input.XmlStreamReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName: InputExample
 * @Auther: yu
 * @Date: 2019/1/26 15:03
 * @Description: 输入测试
 */
public class InputExample {
    private static final String XML_PATH  = "D:\\GitHub\\Code-practice\\dailycode\\test\\2.xml";
    private static final String INPUT = "This should go to the output.";

    public static void main(String[] args){
        runExample();
    }

    public  static  void  runExample() {

        // TeeInputStream
        XmlStreamReader xmlReader = null;
        TeeInputStream tee = null;

        try{
            File xml = FileUtils.getFile(XML_PATH);
            xmlReader = new XmlStreamReader(xml);
            System.out.println("XML encoding: " + xmlReader.getEncoding());
            ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes("US-ASCII"));
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            tee = new TeeInputStream(in, out, true);
            tee.read(new byte[INPUT.length()]);

            System.out.println("Output stream: " + out.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                xmlReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                tee.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
