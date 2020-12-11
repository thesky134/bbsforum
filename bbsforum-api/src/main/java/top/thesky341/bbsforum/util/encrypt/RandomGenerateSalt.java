package top.thesky341.bbsforum.util.encrypt;

import top.thesky341.bbsforum.util.common.RandomGenerateString;

import java.util.Random;

/**
 * @author thesky
 * @date 2020/12/9
 */
public class RandomGenerateSalt {
    /**
     * 随机生成16位的包含字母数字的盐值
     * @return 16位字符串
     */
    public static String generateSalt() {
        return RandomGenerateString.generateString(16);
    }
}
