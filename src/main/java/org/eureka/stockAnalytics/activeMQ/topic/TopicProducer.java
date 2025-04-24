package org.eureka.stockAnalytics.activeMQ.topic;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class TopicProducer {
    private JmsTemplate jmsTemplate;

    public TopicProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void producer(TickersList tickersList) {
        jmsTemplate.convertAndSend("test", tickersList);
    }
}