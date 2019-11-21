package com.example.demo.config;


import com.example.demo.udp.ServerHandler;
import com.example.demo.udp.SocketFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;


@Configuration
public class MinaConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

	// socket占用端口
	@Value("${mina.port}")
	private int port;

	@Bean
	public LoggingFilter loggingFilter() {
		return new LoggingFilter();
	}

	@Bean
	public IoHandler ioHandler() {
		return new ServerHandler();
	}

	@Bean
	public InetSocketAddress inetSocketAddress() {
		return new InetSocketAddress(port);
	}

	@Bean
	public IoAcceptor ioAcceptor() throws Exception {
//		IoAcceptor acceptor = new NioSocketAcceptor(); // TCP
        IoAcceptor acceptor = new NioDatagramAcceptor();// UPD
		acceptor.getFilterChain().addLast("logger", loggingFilter());
		// 使用自定义编码解码工厂类
		acceptor.getFilterChain().addLast("coderc", new ProtocolCodecFilter(new SocketFactory()));
		acceptor.setHandler(ioHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(inetSocketAddress());
		logger.info("Socket服务器在端口：" + port + "已经启动");
		return acceptor;
	}

}