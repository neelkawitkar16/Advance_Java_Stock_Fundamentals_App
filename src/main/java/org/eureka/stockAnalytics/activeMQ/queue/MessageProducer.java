package org.eureka.stockAnalytics.activeMQ.queue;

import org.eureka.stockAnalytics.vo.MessageVO;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private JmsTemplate jmsTemplate;

    public MessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void producer(MessageVO message) {
        jmsTemplate.convertAndSend("org.eureka.stocks", message);
    }
}