package com.liu.tcpserver;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import java.nio.charset.Charset;

/**
 * @ClassName: MyCodecFactory
 * @Auther: yu
 * @Date: 2018/11/16 16:30
 * @Description:
 */
public class MyCodecFactory implements ProtocolCodecFactory {
    private  MyRequestDecoder decoder;
    private  MyRequestEncoder encoder;

    public MyCodecFactory(){
        this.decoder = new MyRequestDecoder();
        this.encoder = new MyRequestEncoder(Charset.defaultCharset());
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }
}
