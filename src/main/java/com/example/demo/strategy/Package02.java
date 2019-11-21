package com.example.demo.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

/**
 * Created by Administrator on 2019/11/18.
 * @ Description: 0x02 终端请求注销
 * @ author  山羊来了
 * @ date 2019/11/18---13:22
 */
@Component
public class Package02 implements PackageTypeJudge {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Integer type() {
        return 0x02;
    }

    @Override
    public byte[] caculate(ByteArrayInputStream inputStream) {
        log.info("Package02");
        return null;
    }


}
