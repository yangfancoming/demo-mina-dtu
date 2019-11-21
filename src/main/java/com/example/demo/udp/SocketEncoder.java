package com.example.demo.udp;

import com.example.demo.model.Message;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.CharsetEncoder;


/**
 * 自定义解码器(暂不用)
 */
public class SocketEncoder extends ProtocolEncoderAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());
	//输出文本最大长度
    private int maxLineLength = Integer.MAX_VALUE;

	// 编码 将数据包转成字节数组
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        logger.info("进入  encode 。。。。 :" + message);
//        String value = (message == null ? "" : message.toString());
//        IoBuffer buf = IoBuffer.allocate(value.length()).setAutoExpand(true);
//        buf.putString(value, encoder);

//        if (buf.position() > maxLineLength) {
//            throw new IllegalArgumentException("Line length: " + buf.position());
//        }
//        buf.flip();
//        out.write(buf);
//	    out.flush();

        IoBuffer buf = IoBuffer.allocate(16).setAutoExpand(true);
        Message.packageType = new byte[]{ (byte) 0x81} ;
        Message.packageLength =  new byte[]{0x00,0x10};
        Message.packageIdentity = "13900000000".getBytes();
        byte[] bytes = byteMerger(16);
        buf.put(bytes);
//        ByteBuf buf = ctx.alloc().buffer(bytes.length);
//        buf.writeBytes(bytes);
//        InetSocketAddress inetSocketAddress = new InetSocketAddress("172.24.106.2", 5001);//指定客户端的IP及端口
        //        InetSocketAddress inetSocketAddress = new InetSocketAddress("192.168.3.9", 5001);//指定客户端的IP及端口
//        list.add(new DatagramPacket(buf, inetSocketAddress));
//        log.info("{}发送消息{}:" );

        buf.flip();
        out.write(buf);
        out.flush();
	}

    //java 合并两个byte数组  [7B 01 00 16 31 33 39 30 30 30 30 30 30 30 30 AC 18 6A 02 13 89 7B ]
    public static byte[] byteMerger(int length){
        byte[] result = new byte[length];
        System.arraycopy(Message.startMark, 0, result, 0, 1);
        System.arraycopy(Message.packageType, 0, result, 1, 1);
        System.arraycopy(Message.packageLength, 0, result, 2, Message.packageLength.length);
        System.arraycopy(Message.packageIdentity, 0, result, 2 + Message.packageLength.length, Message.packageIdentity.length);
        System.arraycopy(Message.endMark, 0, result, 2 + Message.packageLength.length + Message.packageIdentity.length, 1);
        //        byte[] bytes  = new byte[]{ 0x07} ;
        //        System.arraycopy("2".getBytes(), 0, result, 3 + Message.packageLength.length + Message.packageIdentity.length, 1);
        return result;
    }
}