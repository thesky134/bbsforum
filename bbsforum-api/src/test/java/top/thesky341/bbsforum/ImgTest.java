package top.thesky341.bbsforum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author thesky
 * @date 2020/12/10
 */
@SpringBootTest
public class ImgTest {
    @Value("${imgAbsolutePathPrefix}")
    private String pathPrefix;

    @Test
    public void imgPathTest() {
        System.out.println(pathPrefix);
    }
}
