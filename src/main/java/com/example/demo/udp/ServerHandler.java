package com.example.demo.udp;

import com.example.demo.model.Message;
import com.example.demo.strategy.PackageTypeJudge;
import com.example.demo.strategy.PackageTypeService;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;


public class ServerHandler extends IoHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PackageTypeService packageTypeService;

    @Override
    public void sessionCreated(IoSession session)  {
        logger.info("连接创建 : " + session.getRemoteAddress().toString());
    }

    @Override
    public void sessionOpened(IoSession session)  {
        logger.info("连接打开: " + session.getRemoteAddress().toString());
    }

	@Override
	public void messageReceived(IoSession session, Object message){
        logger.info("接受到数据 :" + message);
        ByteArrayInputStream inputStream =  (ByteArrayInputStream)message;
//        logger.info("跳过字节数 :" + inputStream.skip(1));
//        logger.info("读取内容 :" + inputStream.read());
        PackageTypeJudge packageTypeJudge = packageTypeService.map.get(Integer.valueOf(Message.packageType[0]));
        byte[] result = packageTypeJudge.caculate(inputStream);
        if (result == null){
            logger.info("数据处理异常 请联系管理员！");
            return;
        }
        // 如果是无效协议包 则直接跳过数据回复
//        if (Arrays.equals(new byte[]{0x04}, Message.packageType)){
//            logger.info("收到 0x84 包 直接忽略！");
//            return;
//        }
		session.write(result);
	}


	@Override
	public void messageSent(IoSession session, Object message)  {
		logger.info("返回消息内容 : " + message.toString());
	}


    @Override
    public void exceptionCaught(IoSession session, Throwable cause)  {
        logger.error("出现异常 :" + session.getRemoteAddress().toString() + " : " + cause.toString());
        session.write("出现异常");
        session.closeNow();
    }

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)  {
		if (status == IdleStatus.READER_IDLE) {
			logger.info("进入读空闲状态");
			session.closeNow();
		} else if (status == IdleStatus.BOTH_IDLE) {
			logger.info("BOTH空闲");
			session.closeNow();
		}
	}

	@Override
	public void sessionClosed(IoSession session)  {
		logger.info("连接关闭 : " + session.getRemoteAddress().toString());
		int size = session.getService().getManagedSessions().values().size();
		logger.info("连接关闭时session数量==》" + size);
	}

}