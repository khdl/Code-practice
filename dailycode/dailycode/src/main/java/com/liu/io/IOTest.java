package com.liu.io;

import java.io.*;
import java.util.Scanner;

/**
 * @className: IOTest
 * @author: yu.liu
 * @date: 2019/4/30 14:21
 * @description:
 */
public class IOTest {
    private static final String EXAMPLE_PATH = "D:\\GitHub\\Code-practice\\dailycode\\test\\exp.txt";
    private static final String NEW_FILE = "D:\\GitHub\\Code-practice\\dailycode\\test\\1.txt";

    public static void main(String[] args) {
        //test();
        test5();
    }

    /**
     * 将标准输入的内容写入到文件
     */
    public static void test5(){
        Scanner in = new Scanner(System.in);
        try{
            Writer out = new FileWriter(NEW_FILE);
            String s;
            while(!(s = in.nextLine()).equals("Q")){
                 out.write(s + "\n");
            }
            out.flush();
            out.close();
            in.close();

        }catch (IOException e){
            e.printStackTrace();
        }


    }

    /**
     * 将文件A的内容拷贝到文件B
     */
    public static  void test4(){
        int b;
        try {
            InputStream in = new FileInputStream(EXAMPLE_PATH);
            OutputStream out = new FileOutputStream(NEW_FILE);
            while((b = in.read()) != -1){
                out.write(b);
            }
            out.flush();
            out.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将标准输入（键盘输入）显示到标准输出（显示器），支持字符。
     */
    public static void test(){
        char ch;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try{
            while ( (ch =(char)in.read()) !=-1){
                System.out.println(ch);
            }
        }catch (IOException  e){

        }
    }

    /**
     * 将一个java类输出到控制台
     */
    public static  void test1(){
        String s;
        try {
            BufferedReader in = new BufferedReader(new FileReader(EXAMPLE_PATH));
            while((s = in.readLine()) != null){
                System.out.println(s);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将一个java类输出到控制台
     */
    public static  void test2(){
        int b;
        try {
            FileReader in = new FileReader(EXAMPLE_PATH);
            while((b = in.read()) !=  -1){
                System.out.print((char) b);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  将一个java类输出到控制台(可能会出现问题)
     */
    public static  void test3(){
        int n = 50;
        byte[] buffer = new byte[n];
        try {
            FileInputStream in = new FileInputStream(EXAMPLE_PATH);
            while ((in.read(buffer,0,n) != -1 && n > 0)){
                System.out.print(new String(buffer));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
