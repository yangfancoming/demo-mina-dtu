package com.example.demo.model;

import java.io.Serializable;


/*报文*/
public class Message implements Serializable {

    public static byte[] startMark = new byte[]{0x7B};
    public static byte[] packageType = new byte[1];
    public static byte[] packageLength = new byte[2];
    public static byte[] packageIdentity = new byte[11];
    public static byte[] endMark = new byte[]{0x7B};

}
