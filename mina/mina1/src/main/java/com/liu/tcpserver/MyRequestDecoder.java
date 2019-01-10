package com.liu.tcpserver;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @ClassName: MyRequestDecoder
 * @Auther: yu
 * @Date: 2018/11/16 16:33
 * @Description:
 */
public class MyRequestDecoder extends ProtocolDecoderAdapter {
    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        byte[] bytes = new byte[in.limit()];
        in.get(bytes,0,in.limit());
        String s = new String(bytes) + "解码";
        out.write(s);
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {

    }

    @Override
    public void dispose(IoSession session) throws Exception {

    }
}
