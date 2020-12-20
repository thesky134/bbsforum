package top.thesky341.bbsforum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

/**
 * @author thesky
 * @date 2020/12/19
 */
@SpringBootTest
public class UserTest {
    @Test
    public void timeTest() {
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DAY_OF_YEAR, -30);
        System.out.println(time);
    }
}
