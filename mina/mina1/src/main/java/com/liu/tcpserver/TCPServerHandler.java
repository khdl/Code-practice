package com.liu.tcpserver;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: TCPServerHandler
 * @Auther: yu
 * @Date: 2018/11/15 14:20
 * @Description:
 */
public class TCPServerHandler extends IoHandlerAdapter{
    private  static  final Logger log  = LoggerFactory.getLogger(TCPServerHandler.class);

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
       String mes = message.toString();
       System.out.println("The received message is:" + mes);
       session.write("服务端收到消息");
       session.close(true);
       if(mes.endsWith("quit")){
           session.close(true);
       }
    }

    @Override
    public void sessionCreated(IoSession ioSession) throws  Exception{
        System.out.println("server session  created");
        super.sessionCreated(ioSession);
    }
    @Override
    public  void  sessionOpened(IoSession session) throws Exception{
        System.out.println("server session opened");
        super.sessionOpened(session);
    }
    @Override
    public void sessionClosed(IoSession session) throws  Exception{
        System.out.println("server session closed");
        super.sessionClosed(session);
    }
}
