package com.wade.service;

import com.wade.common.ExceptionEnum;
import com.wade.common.FlyException;
import com.wade.common.JsonUtils;
import com.wade.interceptor.UserInterceptor;
import com.wade.po.CartPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final String KEY_PREFIX = "cart:user:id:";

    @Autowired
    StringRedisTemplate redisTemplate;

    public void addCart(CartPO cart) {
        //获取登陆用户 使用线程上下文
        String user = UserInterceptor.getUser();
        String key = KEY_PREFIX + user;
        String skuId = cart.getSkuId().toString();
        Integer newCartNum = cart.getNum();
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(key);
        if(operations.hasKey(skuId)) {
            //修改数量
            String jsonCart = operations.get(skuId).toString();
            cart = JsonUtils.parse(jsonCart, CartPO.class);
            cart.setNum(cart.getNum() + newCartNum);
        }
        operations.put(skuId, JsonUtils.serialize(cart));
    }

    public List<CartPO> getCart() {
        String key = KEY_PREFIX + UserInterceptor.getUser();
        if (redisTemplate.hasKey(key)) {
            throw new FlyException(ExceptionEnum.CART_NOT_FOUND);
        }
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(key);
        List<CartPO> results = operations.values().stream().map(o -> JsonUtils.parse(o.toString(), CartPO.class))
                .collect(Collectors.toList());
        return results;
    }
}
