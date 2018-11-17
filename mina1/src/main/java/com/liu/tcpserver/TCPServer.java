package com.liu.tcpserver;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @ClassName: TCPServer
 * @Auther: yu
 * @Date: 2018/11/15 11:45
 * @Description:
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);
        //编写过滤器
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue()
                        ,LineDelimiter.WINDOWS.getValue())));

       // acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MyCodecFactory()));

     /*   //绑定自定义的过滤器
        acceptor.getFilterChain().addLast("myIoFilter", new ReferenceCountingFilter(new MyIoFilter()));*/
        //设置handler
        acceptor.setHandler(new TCPServerHandler());

        //绑定端口
        acceptor.bind(new InetSocketAddress(9090));

    }
}
