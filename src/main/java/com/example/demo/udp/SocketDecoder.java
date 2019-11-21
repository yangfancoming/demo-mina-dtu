package com.example.demo.udp;


import com.example.demo.chain.Chain;
import com.example.demo.chain.ChainService;
import com.example.demo.util.SpringUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;


/**
 * 自定义解码器
 */
public class SocketDecoder extends CumulativeProtocolDecoder {

    private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws IOException {
        logger.info("进入  doDecode 。。。。 :" + in.remaining());
        byte[] data = new byte[in.remaining()];
        in.get(data, 0, in.remaining());
        ChainService chainService = (ChainService)SpringUtils.getBean("chainService");
        Chain chain = chainService.setService();
        ByteArrayInputStream inputStream = chain.handler(new ByteArrayInputStream(data));
        out.write(inputStream);
		return true;
	}
}
