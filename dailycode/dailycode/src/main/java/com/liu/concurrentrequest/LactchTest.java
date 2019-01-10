package com.liu.concurrentrequest;

import sun.net.www.http.HttpClient;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: yu
 * @Date: 2018/10/9 20:45
 * @Description:测试CountDownLatch,模拟真正并发请求
 */
public class LactchTest {
    public static void main(String[] args) throws InterruptedException {
       Runnable taskTemp = new Runnable() {
           private  int iCounter;
           @Override
           public void run() {
               for (int i = 0; i < 10; i++) {
                  HttpClientOp.doGet("https://www.baidu.com/");
                  iCounter++;
                   System.out.println(System.nanoTime()+"["+Thread.currentThread().getName()+"] iCounter= "+ iCounter);
                   try{
                       Thread.sleep(100);
                   }catch (InterruptedException e){
                       e.printStackTrace();
                   }
               }
           }
       };
       LactchTest lactchTest = new LactchTest();
       lactchTest.setTaskAllInOnce(5,taskTemp);
    }

    private void setTaskAllInOnce(int threadNums,  Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNums);
        for (int i = 0; i <threadNums ; i++) {
            Thread t = new Thread(){
                @Override
                public  void  run(){
                    try {
                        startGate.await();
                        try {
                            task.run();
                        }finally {
                            endGate.countDown();
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            };
            t.start();
        }
        long startTime =System.nanoTime();
        System.out.println(startTime+"["+Thread.currentThread()+"] All Thred is ready,concurrent going ...");
        startGate.countDown();
        endGate.await();
        long endTime = System.nanoTime();
        System.out.println(endTime+"["+Thread.currentThread()+"] All Thread is compeleted");

    }
}
