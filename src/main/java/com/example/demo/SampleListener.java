package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
class SampleListener {
    private static final Logger LOG = LoggerFactory.getLogger(SampleListener.class);

    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(value = "my.exchange", type = "topic"),
                    value = @Queue(value = "my-queue"),
                    key = "my-queue-routing"
            )
    )
    void listen(Message message) {
        LOG.info("Received message: {}", message);
    }
}
