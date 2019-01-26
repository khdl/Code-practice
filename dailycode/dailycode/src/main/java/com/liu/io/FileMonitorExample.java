package com.liu.io;


import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.io.monitor.FileEntry;

import java.io.File;

/**
 * @ClassName: FileMonitorExample
 * @Auther: yu
 * @Date: 2019/1/26 14:04
 * @Description: 文件监控器测试
 */
public class FileMonitorExample {
    private static final String EXAMPLE_PATH = "D:\\GitHub\\Code-practice\\dailycode\\test\\exp.txt";
    private static final String PARENT_DIR = "D:\\GitHub\\Code-practice\\dailycode\\test";
    private static final String NEW_DIR = "D:\\GitHub\\Code-practice\\dailycode\\test\\test1";
    private static final String NEW_FILE = "D:\\GitHub\\Code-practice\\dailycode\\test\\test1\\1.txt";

    public static void main(String[] args){
        runExample();
    }

    public  static  void  runExample() {

        // FileEntry
        FileEntry entry = new FileEntry(FileUtils.getFile(EXAMPLE_PATH));
        System.out.println("File monitored: " + entry.getFile());
        System.out.println("File name: " + entry.getName());
        System.out.println("Is the file a directory?: " + entry.isDirectory());

        // File Monitoring
        File parentDir = FileUtils.getFile(PARENT_DIR);
        FileAlterationObserver observer = new FileAlterationObserver(parentDir);
        observer.addListener(new FileAlterationListenerAdaptor(){

            @Override
            public void onFileCreate(File file) {
                System.out.println("File created: " + file.getName());
            }

            @Override
            public void onFileDelete(File file) {
                System.out.println("File deleted: " + file.getName());
            }

            @Override
            public void onDirectoryCreate(File dir) {
                System.out.println("Directory created: " + dir.getName());
            }

            @Override
            public void onDirectoryDelete(File dir) {
                System.out.println("Directory deleted: " + dir.getName());
            }
        });

        FileAlterationMonitor monitor = new FileAlterationMonitor(500, observer);
        try {
            monitor.start();

            File newDir = new File(NEW_DIR);
            File newFile = new File(NEW_FILE);

            newDir.mkdirs();
            newFile.createNewFile();

            Thread.sleep(1000);

            FileDeleteStrategy.NORMAL.delete(newFile);
            FileDeleteStrategy.NORMAL.delete(newDir);

            monitor.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
