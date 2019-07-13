package com.wade.service;

import com.wade.common.MQConstant;
import com.wade.common.NumberUtils;
import com.wade.type.CheckTypeEnum;
import com.wade.common.ExceptionEnum;
import com.wade.common.FlyException;
import com.wade.mapper.UserMapper;
import com.wade.po.UserPO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TokenService tokenService;

    private static final String KEY_PREFIX = "user:verify:phone:";


    public Boolean checkDataType(String data, CheckTypeEnum type) {
        UserPO user = new UserPO();
        if (type == CheckTypeEnum.USER_TYPE) {
            user.setUsername(data);
        } else if (type == CheckTypeEnum.PHONE_TYPE) {
            user.setPhone(data);
        } else {
            throw new FlyException(ExceptionEnum.ERROR_CHECK_TYPE);
        }
        return userMapper.selectCount(user) == 0;
    }

    public void sendCode(String phone) {
        String key = KEY_PREFIX + phone;
        String code = NumberUtils.generateCode(6);
        Map<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);
        amqpTemplate.convertAndSend("fly.sms.exchange", MQConstant.SMS_VERIFY_CODE, msg);

        //保存验证码
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
    }

    public String loginUser(String username, String password) {
        UserPO user = new UserPO();
        user.setUsername(username);
        user.setPassword(password);
        UserPO result = userMapper.selectOne(user);
        if (null == result) {
            throw new FlyException(ExceptionEnum.USER_LOGIN_ERROR);
        }
        return tokenService.generateToken(result);
    }
}
