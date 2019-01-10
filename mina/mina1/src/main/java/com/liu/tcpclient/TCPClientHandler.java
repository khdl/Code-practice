package com.liu.tcpclient;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: TCPClientHandler
 * @Auther: yu
 * @Date: 2018/11/15 15:22
 * @Description:
 *
 */
public class TCPClientHandler extends IoHandlerAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(TCPClientHandler.class);
    private final String values;


    public TCPClientHandler(String values) {
        this.values = values;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String mes = message.toString();
        System.out.println("The received message is:" + mes);
    }
    @Override
    public void sessionOpened(IoSession session) {
        session.write(values);
    }
}
