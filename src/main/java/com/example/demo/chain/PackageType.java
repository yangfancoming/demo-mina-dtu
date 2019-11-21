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
 * @ Description: 包类型处理器
 * @ author  山羊来了
 * @ date 2019/11/18---12:01
 */
@Component
public class PackageType extends Chain {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ByteArrayInputStream handler(ByteArrayInputStream input) throws IOException {
        log.info("包类型处理器: "+input.available());
        byte[] type = new byte[1];
        input.read(type);
        Message.packageType = type;
        log.info("剩余有效字节数: "+input.available());
        return process(input);
    }
}
