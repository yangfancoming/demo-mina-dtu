package com.example.demo.chain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChainService {

    // 起始/结束 标识处理器
    @Autowired  PackageMark packageMark;
    // 包类型处理器
    @Autowired PackageType packageType;
    // 包长度处理器
    @Autowired PackageLength packageLength;
    // DTU 身份识别码
    @Autowired PackageIdentity packageIdentity;


    // 设置责任链 节点处理顺序
    public Chain setService(){
        packageMark.setSuccessor(packageType).setSuccessor(packageLength).setSuccessor(packageIdentity);
        return packageMark;
    }

}
