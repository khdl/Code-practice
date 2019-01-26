package com.liu.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.*;

import java.io.File;

/**
 * @ClassName: FiltersExample
 * @Auther: yu
 * @Date: 2019/1/26 14:40
 * @Description: 过滤器测试
 */
public class FiltersExample {
    private static final String PARENT_DIR = "D:\\GitHub\\Code-practice\\dailycode\\test";

    public static void main(String[] args){
        runExample();
    }

    public  static  void  runExample() {
        // NameFileFilter
        File dir = FileUtils.getFile(PARENT_DIR);
        String[] acceptedNames = {"exp", "exp.txt"};
        for (String file: dir.list(new NameFileFilter(acceptedNames, IOCase.INSENSITIVE))) {
            System.out.println("File found, named: " + file);
        }

        //WildcardFileFilter
        for (String file: dir.list(new WildcardFileFilter("*xp*"))) {
            System.out.println("Wildcard file found, named: " + file);
        }

        // PrefixFileFilter
        for (String file: dir.list(new PrefixFileFilter("ex"))) {
            System.out.println("Prefix file found, named: " + file);
        }

        // SuffixFileFilter
        for (String file: dir.list(new SuffixFileFilter(".txt"))) {
            System.out.println("Suffix file found, named: " + file);
        }

        // OrFileFilter
        for (String file: dir.list(new OrFileFilter(
                new WildcardFileFilter("*amp*"), new SuffixFileFilter(".txt")))) {
            System.out.println("Or file found, named: " + file);
        }

        for (String file: dir.list(new AndFileFilter(
                new WildcardFileFilter("*ample*"),
                new NotFileFilter(new SuffixFileFilter(".txt"))))) {
            System.out.println("And/Not file found, named: " + file);
        }
    }
}
