### CountDownLatch 和 CyclicBarrier 练习

(代码在工程dailycode包 com.liu.concurrentrequestxia)

Java 中模拟并发请求，自然是很方便的，只要多开几个线程，发起请求就好了。但是，这种请求，一般会存在启动的先后顺序了，算不得真正的同时并发！Java 中提供了闭锁 CountDownLatch, 刚好就用来做这种事

开启n个线程，加一个闭锁，开启所有线程；
待所有线程都准备好后，按下开启按钮，就可以真正的发起并发请求了。


HttpClientOp  工具类，可以使用 成熟的工具包，也可以自己写一个简要的访问方法。


CountDownLatch只是语言层面的东西，也并非绝对的同时并发。具体的调用还要依赖于CPU个数，线程数及操作系统的线程调度功能等。

与 CountDownLatch 有类似功能的，还有个工具栅栏 CyclicBarrier, 也是提供一个等待所有线程到达某一点后，再一起开始某个动作，效果一致，不过栅栏的目的确实比较纯粹，就是等待所有线程到达，而前面说的闭锁 CountDownLatch 虽然实现的也是所有线程到达后再开始，但是他的触发点其实是最后那一个开关，所以侧重点是不一样的。


