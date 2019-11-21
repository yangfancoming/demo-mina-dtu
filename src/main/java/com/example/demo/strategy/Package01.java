package com.example.demo.strategy;

import com.example.demo.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

/**
 * Created by Administrator on 2019/11/18.
 * @ Description: 0x01 终端请求注册   dtu --->>> dsc
 * @ author  山羊来了
 * @ date 2019/11/18---13:22
 */
@Component
public class Package01 implements PackageTypeJudge {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Integer type() {
        return 0x01;
    }

    @Override
    public byte[] caculate(ByteArrayInputStream inputStream) {
        log.info("Package01");
        Message.packageType = new byte[]{ (byte) 0x81} ;
        Message.packageLength =  new byte[]{0x00,0x10};
        Message.packageIdentity = "13900000000".getBytes();
        byte[] bytes = byteMerger(16);
        return bytes;
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
