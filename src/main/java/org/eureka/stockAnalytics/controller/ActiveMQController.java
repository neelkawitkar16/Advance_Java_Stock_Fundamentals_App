package org.eureka.stockAnalytics.controller;

import org.eureka.stockAnalytics.exception.StockNotFoundException;
import org.eureka.stockAnalytics.service.JMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/jms")
@RestController
public class ActiveMQController {

    @Autowired
    private JMSService jmsService;

    @PostMapping(value = "/simpleQueueMessage")
    public String postSimpleQueueMessage(@RequestBody String firstMessage) {
        return jmsService.postSimpleQueueMessage(firstMessage);
    }

    @PostMapping(value = "/passTickersList")
    public String passTickersList(@RequestBody List<String> tickers) {
        return jmsService.passTickersList(tickers);
    }


    /*@ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity generateExceptions(Exception e) {
        return new ResponseEntity().internalServerError().body(e.getMessage());
    }*/
}
