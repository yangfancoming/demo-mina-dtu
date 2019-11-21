package com.example.demo.chain;

import com.example.demo.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2019/11/18.
 *
 * @ Description: 包长度处理器
 * @ author  山羊来了
 * @ date 2019/11/18---12:01
 */
@Component
public class PackageLength extends Chain {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ByteArrayInputStream handler(ByteArrayInputStream input) throws IOException {
        log.info("包长度处理器: "+input.available());
        byte[] length = new byte[2];
        input.read(length);
        Message.packageLength = length;
        log.info("剩余有效字节数: "+input.available());
        return process(input);
    }
}
