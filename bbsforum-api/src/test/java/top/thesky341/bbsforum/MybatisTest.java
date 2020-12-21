package top.thesky341.bbsforum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import top.thesky341.bbsforum.entity.Category;
import top.thesky341.bbsforum.entity.Chara;
import top.thesky341.bbsforum.entity.Post;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.mapper.CategoryMapper;
import top.thesky341.bbsforum.mapper.PostMapper;
import top.thesky341.bbsforum.mapper.UserMapper;
import top.thesky341.bbsforum.util.encrypt.MD5SaltEncryption;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author thesky
 * @date 2020/12/9
 */
@SpringBootTest
public class MybatisTest {
    @Resource
    UserMapper userMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    CategoryMapper categoryMapper;

    @Test
    @Transactional
    public void mapperTest() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("12345@qq.com");
        user.setPasswd(MD5SaltEncryption.encrypt("abcd1234", "1234567890abcdef"));
        user.setSalt("1234567890abcdef");
        Chara chara = new Chara();
        chara.setId(3);
        user.setChara(chara);
        userMapper.addUser(user);
        System.out.println(userMapper.getUserById(user.getId()));
    }

    @Test
    public void batchAddPost() {
        String str = "《西游记》是中国古代第一部浪漫主义章回体长篇神魔小说。现存明刊百回本《西游记》均无作者署名。清代学者吴玉搢等首先提出《西游记》作者是明代吴承恩。 " +
                "全书主要描写了孙悟空出世及大闹天宫后，依次遇见了唐僧、白龙马、猪八戒和沙僧，西行取经，一路上历经艰险、降妖伏魔，经历了九九八十一难，终于到达西天见到如来" +
                "佛祖，最终五圣成真的故事。该小说以“唐僧取经”这一历史事件为蓝本，通过作者的艺术加工，深刻地描绘了明代社会现实。" +
                "《西游记》是中国神魔小说的经典之作，达到了古代长篇浪漫主义小说的巅峰，与《三国演义》《水浒传》《红楼梦》并称为中国古典四大名著。《西游记》自问世以来在民间广" +
                "为流传，各式各样的版本层出不穷明代刊本有六种清代刊本抄本也有七种典籍所记已佚版本十三种。鸦片战争以后，大量中国古典文学作品" +
                "被译为西文，《西游记》渐渐传入欧美，被译为英法德意西手语世（世界语）斯（斯瓦西里语）俄捷罗波日朝越等语言";
        Random random = new Random();
        for(int i = 0; i < 663; i++) {
            int titleSize = random.nextInt(58) + 3;
            StringBuilder titleSB = new StringBuilder();
            for(int j = 0; j < titleSize; j++) {
                char c = str.charAt(random.nextInt(str.length()));
                titleSB.append(c);
            }
            String title = titleSB.toString();

            int contentSize = random.nextInt(3000) + 1;
            StringBuilder contentSB = new StringBuilder();
            for(int j = 0; j < contentSize; j++) {
                char c = str.charAt(random.nextInt(str.length()));
                contentSB.append(c);
            }
            String content = contentSB.toString();
            int userId = 8;
            int categoryId;
            int reward = 0;
            int num = random.nextInt(23);
            if(num <= 10) {
                categoryId = 1;
            } else if(num <= 20) {
                categoryId = 3;
            } else {
                categoryId = 2;
                reward = random.nextInt(300) + 10;
            }
            boolean hidden = false;
            if(random.nextInt(100) <= 1) {
                hidden = true;
            }
            boolean deleted = false;
            if(random.nextInt(100) == 0) {
                deleted = true;
            }
            boolean top = false;
            if(random.nextInt(100) == 0) {
                top = true;
            }
            boolean excellent = false;
            if(random.nextInt(10) == 0) {
                excellent = true;
            }
            User user = userMapper.getUserById(8);
            Category category = categoryMapper.getCategoryById(categoryId);
            Post post = new Post();
            post.setCategory(category);
            post.setContent(content);
            post.setDeleted(deleted);
            post.setExcellent(excellent);
            post.setHidden(hidden);
            post.setReward(reward);
            post.setTop(top);
            post.setTitle(title);
            post.setUser(user);
            postMapper.batchAddPost(post);
        }
    }
}
