package com.wade.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wade.po.UserPO;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public String generateToken(UserPO user) {
        return JWT.create().withAudience(user.getId().toString())
                .sign(Algorithm.HMAC256(user.getPassword()));
    }
}
