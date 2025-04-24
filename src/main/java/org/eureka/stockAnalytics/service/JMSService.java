package org.eureka.stockAnalytics.service;

import org.eureka.stockAnalytics.activeMQ.queue.MessageProducer;
import org.eureka.stockAnalytics.activeMQ.topic.TickersList;
import org.eureka.stockAnalytics.activeMQ.topic.TopicProducer;
import org.eureka.stockAnalytics.exception.StockNotFoundException;
import org.eureka.stockAnalytics.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JMSService {
    private MessageProducer messageProducer;
    private TopicProducer topicProducer;

    @Autowired
    public JMSService(MessageProducer messageProducer, TopicProducer topicProducer) {
        this.messageProducer = messageProducer;
        this.topicProducer = topicProducer;
    }

    public String postSimpleQueueMessage(String firstMessage) {
        MessageVO messageVO = new MessageVO(firstMessage);

        try {
            messageProducer.producer(messageVO);
            return "Message sent successfully!";
        } catch (Exception e) {
            throw new StockNotFoundException("Failed to send the message");
        }
    }

    public String passTickersList(List<String> tickers) {
        TickersList tickersList = new TickersList();
        tickersList.setTickers(tickers);

        try {
            topicProducer.producer(tickersList);
            return "Message sent successfully!";
        } catch (Exception e) {
            throw new StockNotFoundException("Failed to sent the message");
        }
    }
}
