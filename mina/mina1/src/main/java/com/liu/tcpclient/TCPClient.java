package com.liu.tcpclient;

import com.liu.tcpserver.MyCodecFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @ClassName: TCPClient
 * @Auther: yu
 * @Date: 2018/11/15 15:11
 * @Description:
 */
public class TCPClient {

    public static void main(String[] args){
        IoConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(10000);

        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue()
                        ,LineDelimiter.WINDOWS.getValue())));

        //connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new MyCodecFactory()));

        connector.setHandler(new TCPClientHandler("大家好！"));

     /*   connector.connect(new InetSocketAddress("127.0.0.1", 9090));
        System.out.println("1111");*/

  /*    ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 9090));
        // 等待是否连接成功，相当于是转异步执行为同步执行。
        future.awaitUninterruptibly();
        // 连接成功后获取会话对象。如果没有上面的等待，由于connect()方法是异步的，session可能会无法获取。
        System .out.println(session);*/

        ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 9090));
        future.addListener(new IoFutureListener<ConnectFuture>() {
            public void operationComplete(ConnectFuture future) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                IoSession session = future.getSession();
                System.out.println("++++++++++++++++++++++++++++");
            }
        });
        System.out.println("*************");
    }
}
