package top.thesky341.bbsforum.util.common;

import java.util.Random;

/**
 * @author thesky
 * @date 2020/12/10
 */
public class RandomGenerateString {
    /**
     * 生成一个随机字符串
     * @param length 指定生成字符串的长度
     * @return 返回生成的字符串
     */
    public static String generateString(int length) {
        if (length <= 0) {
            length = 1;
        }
        StringBuilder sb = new StringBuilder("");
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            if (num >= 0 && num <= 9) {
                sb.append(num);
            } else if (num >= 10 && num <= 35) {
                sb.append((char) (num + 'a' - 10));
            } else {
                sb.append((char) (num + 'A' - 36));
            }
        }
        return sb.toString();
    }
}
