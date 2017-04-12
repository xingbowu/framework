package com.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xingbowu on 17/4/10.
 */
public class HashAlgorithmImp {

    public static byte[] computMD5(String key){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(key.getBytes());
            return md5.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long hash(byte[] digest, int nTime)
    {
        long rv = ((long)(digest[3 + nTime * 4] & 0xFF) << 24)
                | ((long)(digest[2 + nTime * 4] & 0xFF) << 16)
                | ((long)(digest[1 + nTime * 4] & 0xFF) << 8)
                | ((long)digest[0 + nTime * 4] & 0xFF);
        return rv & 0xffffffffL;
    }

}
