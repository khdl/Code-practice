package com.liu.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.comparator.SizeFileComparator;

import java.io.File;
import java.util.Date;

/**
 * @ClassName: ComparatorExample
 * @Auther: yu
 * @Date: 2019/1/26 14:48
 * @Description: 比较器测试
 */
public class ComparatorExample {
    private static final String PARENT_DIR = "D:\\GitHub\\Code-practice\\dailycode\\test";
    private static final String FILE_1 ="D:\\GitHub\\Code-practice\\dailycode\\test\\exp.txt";
    private static final String FILE_2 = "D:\\GitHub\\Code-practice\\dailycode\\test\\1.txt";

    public static void main(String[] args){
        runExample();
    }

    public  static  void  runExample() {

        //NameFileComparator
        File parentDir = FileUtils.getFile(PARENT_DIR);
        NameFileComparator comparator = new NameFileComparator(IOCase.SENSITIVE);
        File[] sortedFiles = comparator.sort(parentDir.listFiles());
        System.out.println("Sorted by name files in parent directory: ");
        for (File file: sortedFiles) {
            System.out.println("t"+ file.getAbsolutePath());
        }

        // SizeFileComparator
        SizeFileComparator sizeComparator = new SizeFileComparator(true);
        File[] sizeFiles = sizeComparator.sort(parentDir.listFiles());
        System.out.println("Sorted by size files in parent directory: ");
        for (File file: sizeFiles) {
            System.out.println("t"+ file.getName() + " with size (kb): " + file.length());
        }

        // LastModifiedFileComparator
        LastModifiedFileComparator lastModified = new LastModifiedFileComparator();
        File[] lastModifiedFiles = lastModified.sort(parentDir.listFiles());
        System.out.println("Sorted by last modified files in parent directory: ");
        for (File file: lastModifiedFiles) {
            Date modified = new Date(file.lastModified());
            System.out.println("t"+ file.getName() + " last modified on: " + modified);
        }

    }
}
