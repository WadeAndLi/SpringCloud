package com.wade.message;

import com.wade.common.JsonUtils;
import com.wade.common.MQConstant;
import com.wade.config.SMSConfig;
import com.wade.util.SMSUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
@EnableConfigurationProperties(SMSConfig.class)
public class SMSListener {

    @Autowired
    private SMSConfig smsConfig;

    @Autowired
    private SMSUtils smsUtils;
    /**
     * 发送短信验证
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sms.verify.code.queue", durable = "true"),
            exchange = @Exchange(name="fly.sms.exchange", type = ExchangeTypes.TOPIC),
            key = MQConstant.SMS_VERIFY_CODE
    ))
    public void sendMessage(Map<String, String> message) {
        if (CollectionUtils.isEmpty(message)) {
            return;
        }
        String phone = message.remove("phone");
        if (StringUtils.isEmpty(phone)) {
            return;
        }

        smsUtils.sendSMS(phone, smsConfig.getSignName(), smsConfig.getVerifyCodeTemplate(), JsonUtils.serialize(message));
    }
}
