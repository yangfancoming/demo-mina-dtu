package com.example.demo.udp;

import com.example.demo.model.Message;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 自定义解码器(暂不用)
 */
public class SocketEncoder extends ProtocolEncoderAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

	// 编码 将数据包转成字节数组
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) {
        logger.info("进入  encode 。。。。 :" + message);
//        buf.putString(value, encoder);
//        if (buf.position() > maxLineLength) {
//            throw new IllegalArgumentException("Line length: " + buf.position());
//        }
        byte[] bytes = (byte[])message;
        IoBuffer buf = IoBuffer.allocate(bytes.length).setAutoExpand(true);
        buf.put(bytes);
        buf.flip();
        out.write(buf);
        out.flush();
	}


}