package org.smr.common.utils;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import org.apache.commons.codec.digest.DigestUtils;

public class IdentityUtil {
    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new SecureRandom();
    public static final String SIGN_TYPE = "MD5";
    public static final String SIGN = "sign";

    public IdentityUtil() {
    }

    public static boolean inspectionSign(String xmlStr, String key) throws Exception {
        Map<String, String> data = XMLUtil.xmlToMap(xmlStr);
        if (!data.containsKey("sign")) {
            return false;
        } else {
            String sign = (String)data.get("sign");
            return generateSignature(data, key, "MD5").equals(sign);
        }
    }

    public static boolean inspectionSign(Map<String, String> data, String key) throws Exception {
        if (!data.containsKey("sign")) {
            return false;
        } else {
            String sign = (String)data.get("sign");
            return generateSignature(data, key, "MD5").equals(sign);
        }
    }

    public static String generateSignature(Map<String, String> data, String key, String signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = (String[])keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        String[] var6 = keyArray;
        int var7 = keyArray.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String k = var6[var8];
            if (!k.equals("sign") && ((String)data.get(k)).trim().length() > 0) {
                sb.append(k).append("=").append(((String)data.get(k)).trim()).append("&");
            }
        }

        sb.append("key=").append(key);
        if ("MD5".equals(signType)) {
            return DigestUtils.md5Hex(sb.toString()).toUpperCase();
        } else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }

    public static String uuid() {
        String uuid = String.valueOf(UUID.randomUUID()).replace("-", "");
        uuid = uuid.substring(7);
        return uuid;
    }

    public static String getLocalhostIp() {
        String ip = "";

        try {
            ip = InetAddress.getLocalHost().toString();
            ip = ip.substring(ip.indexOf("/") + 1);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return ip;
    }

    public static String createSign(Map<String, String> parameters, String secrect) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();

        while(it.hasNext()) {
            Entry entry = (Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + secrect);
        String sign = DigestUtils.md5Hex(sb.toString()).toUpperCase();
        return sign;
    }

    public static String shaSign(String str) {
        MessageDigest crypt = null;
        String signature = null;

        try {
            crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        return signature;
    }

    public static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        byte[] var2 = hash;
        int var3 = hash.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String generateNonceStr() {
        char[] nonceChars = new char[32];

        for(int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(RANDOM.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length()));
        }

        return new String(nonceChars);
    }

    public static String getMoeny(double price) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(price));
        BigDecimal moeny = bigDecimal.multiply(new BigDecimal("100"));
        return moeny.setScale(0, 1).toString();
    }

    public static String getTimeStamp() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000L);
    }
}
