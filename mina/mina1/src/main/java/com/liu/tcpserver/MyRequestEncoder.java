package com.liu.tcpserver;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * @ClassName: MyRequestEncoder
 * @Auther: yu
 * @Date: 2018/11/16 16:32
 * @Description:
 */
public class MyRequestEncoder extends ProtocolEncoderAdapter {
    private final Charset charset;
    public MyRequestEncoder(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        String sms = (String) message;
        CharsetEncoder ce = charset.newEncoder();
        IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
        sms = sms + "编码";
        buffer.putString(sms,ce);
        buffer.flip();
        out.write(buffer);
    }

    @Override
    public void dispose(IoSession session) throws Exception {

    }
}
