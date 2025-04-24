package org.eureka.stockAnalytics.activeMQ.queue;

import org.eureka.stockAnalytics.vo.MessageVO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @JmsListener(destination = "org.eureka.stocks")
    public void consumer(MessageVO messageVO) {
        System.out.println("Message Received is: " + messageVO.getSimpleText());
    }
}