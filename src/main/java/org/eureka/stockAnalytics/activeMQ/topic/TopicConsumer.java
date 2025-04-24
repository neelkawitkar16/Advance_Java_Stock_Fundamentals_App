package org.eureka.stockAnalytics.activeMQ.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    @JmsListener(destination = "test", containerFactory = "topicListenerFactory")
    public void consumer(TickersList tickersList) {
        System.out.println("Message received consumer 1: " + tickersList.getTickers().toString());
    }

    @JmsListener(destination = "test", containerFactory = "topicListenerFactory")
    public void consumer2(TickersList tickersList) {
        System.out.println("Message received consumer 2: " + tickersList.getTickers().toString());
    }
}
