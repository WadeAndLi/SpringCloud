import com.wade.FlyMsgApplication;
import com.wade.common.MQConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlyMsgApplication.class)
public class SMSTest {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Test
    public void sendMessage() throws InterruptedException {
        Map<String, String> msg = new HashMap<>();
        msg.put("phone", "17521074650");
        msg.put("code", "68486");
        amqpTemplate.convertAndSend("fly.sms.exchange", MQConstant.SMS_VERIFY_CODE, msg);

        Thread.sleep(10000L);
    }
}
