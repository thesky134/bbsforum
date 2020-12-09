package top.thesky341.bbsforum.util.encrypt;

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
        StringBuilder sb = new StringBuilder("");
        Random random = new Random();
        for(int i = 0; i < 16; i++) {
            int num = random.nextInt(62);
            if(num >= 0 && num <= 9) {
                sb.append(num);
            } else if(num >= 10 && num <= 35) {
                sb.append((char)(num + 'a' - 10));
            } else {
                sb.append((char)(num + 'A' - 36));
            }
        }
        return sb.toString();
    }
}
