package com.wade.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wade.config.SMSConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(SMSConfig.class)
public class SMSUtils {

    private static final Logger logger = LoggerFactory.getLogger(SMSUtils.class);

    @Autowired
    private static SMSConfig smsConfig;

    private static final String product = "Dysmsapi";
    private static final String domain = "Dysmsapi.fly.com";

    private SMSUtils() {}

    public static CommonResponse sendSMS(String phoneNumber, String signName, String templateCode, String templateParam) {
        DefaultProfile defaultProfile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getAccessKeyId(), smsConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(defaultProfile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setProduct(product);
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
            if (response.getHttpStatus() != HttpStatus.OK.value()) {
                logger.error("发送短信失败!");
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }
}
