package com.liu.io;

import org.apache.commons.io.*;

import java.io.File;

/**
 * @ClassName: UtilityExample
 * @Auther: yu
 * @Date: 2019/1/26 13:37
 * @Description: IO工具类测试
 */
public class UtilityExample {
    private static final String EXAMPLE_TXT_PATH = "D:\\GitHub\\Code-practice\\dailycode\\test\\exp.txt";
    private static final String PARENT_DIR = "D:\\GitHub\\Code-practice\\dailycode\\test";

    public static void main(String[] args){
        try {
            runExample();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  static  void  runExample() throws Exception {
        //FilenameUtils
        System.out.println("Full path of exampleTxt: " + FilenameUtils.getFullPath(EXAMPLE_TXT_PATH));
        System.out.println("Full name of exampleTxt: " + FilenameUtils.getName(EXAMPLE_TXT_PATH));
        System.out.println("Extension of exampleTxt: " + FilenameUtils.getExtension(EXAMPLE_TXT_PATH));
        System.out.println("Base name of exampleTxt: " + FilenameUtils.getBaseName(EXAMPLE_TXT_PATH));

        // FileUtils
        File exampleFile = FileUtils.getFile(EXAMPLE_TXT_PATH);
        LineIterator iterator = FileUtils.lineIterator(exampleFile);
        while (iterator.hasNext()){
            System.out.println("t" + iterator.next());
        }
        iterator.close();

        File parent = FileUtils.getFile(PARENT_DIR);
        System.out.println("Parent directory contains exampleTxt file: " +
                FileUtils.directoryContains(parent, exampleFile));

        //IOCase
        String str1 = "This is a new String.";
        String str2 = "This is another new String, yes!";
        System.out.println("Ends with string (case sensitive): " + IOCase.SENSITIVE.checkEndsWith(str1, "string."));
        System.out.println("Ends with string (case insensitive): " + IOCase.INSENSITIVE.checkEndsWith(str1, "string."));
        System.out.println("String equality: " + IOCase.SENSITIVE.checkEquals(str1, str2));

        // FileSystemUtils
        System.out.println("Free disk space (in KB): " + FileSystemUtils.freeSpaceKb("C:"));
        System.out.println("Free disk space (in MB): " + FileSystemUtils.freeSpaceKb("C:") / 1024/1024);
    }
}
