package com.ehaqui.ehcore.api.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security
{   
    public static String MD5(String string)
    {
        String code = null;
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(string.getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            code = hash.toString(16);
        } catch (NoSuchAlgorithmException nsae)
        {
            // ignore
        }
        return code;
    }
}
