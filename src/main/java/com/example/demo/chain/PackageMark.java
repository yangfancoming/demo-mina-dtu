package com.example.demo.chain;

import com.example.demo.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Administrator on 2019/11/18.
 *
 * @ Description: 起始/结束 标识处理器
 * @ author  山羊来了
 * @ date 2019/11/18---12:01
 */
@Component
public class PackageMark extends Chain {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ByteArrayInputStream handler(ByteArrayInputStream input) throws IOException {
        log.info("起始/结束 标识处理器: "+input.available());
        byte[] start = new byte[1];
        input.read(start);
        boolean equals = Arrays.equals(start, Message.startMark);
        // 如果起始标识不是 0x7B 则直接断链
        if (!equals) return input;
        log.info("剩余有效字节数: "+input.available());
        return process(input);
    }
}
