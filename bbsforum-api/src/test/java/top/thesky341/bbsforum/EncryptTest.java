package top.thesky341.bbsforum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.thesky341.bbsforum.util.encrypt.MD5SaltEncryption;
import top.thesky341.bbsforum.util.encrypt.RandomGenerateSalt;

/**
 * @author thesky
 * @date 2020/12/9
 */
@SpringBootTest
public class EncryptTest {
    @Test
    public void randomGenerateSaltTest() {
        System.out.println(RandomGenerateSalt.generateSalt());
    }

    @Test
    public void encryptTest() {
        System.out.println(MD5SaltEncryption.encrypt("abcd1234", "1234567890abcdef"));
    }
}
