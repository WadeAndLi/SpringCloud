import com.wade.RedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void contextLoaders() {
//        redisTemplate.opsForValue().set("What", "wade");
//        System.out.println(redisTemplate.opsForValue().get("What"));
        BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps("user:name");
        boundHashOperations.put("name", "Rose");
        boundHashOperations.put("age", "30");

        System.out.println(boundHashOperations.get("name"));
        System.out.println(boundHashOperations.entries());
    }
}
