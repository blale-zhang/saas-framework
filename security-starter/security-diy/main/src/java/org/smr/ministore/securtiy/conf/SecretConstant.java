package org.smr.ministore.securtiy.conf;

/**
 * @Author: Helon
 * @Description: JWT使用常量值
 * @Data: Created in 2018/7/27 14:37
 * @Modified By:
 */
public class SecretConstant {

    //签名秘钥 自定义
    public static final String BASE64SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.\n" +
            "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.\n" +
            "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\n";

    //超时毫秒数（默认30分钟）
    public static final int EXPIRESSECOND = 1800000;

    //用于JWT加密的密匙 自定义
    public static final String DATAKEY = "************";

}
