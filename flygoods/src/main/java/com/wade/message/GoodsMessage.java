package com.wade.message;

import com.wade.common.MQConstant;
import com.wade.service.PageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsMessage {

    @Autowired
    private PageService pageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.insert", durable = "true"),
            exchange = @Exchange(
                    name = "fly.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = MQConstant.GOODS_INSERT
    ))
    public void reIndexGoods(Long spuId) {
        if (null != spuId) {
            pageService.createHtml(spuId);
        }
    }
}
