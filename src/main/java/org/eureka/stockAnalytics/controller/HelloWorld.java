package org.eureka.stockAnalytics.controller;

import org.eureka.stockAnalytics.vo.SampleRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloWorld {

    /*
    @PathVariable - binds placeholder from the URI to the method parameter.
        If URI and method parameter has the "same name" then no need for value [like in case of firstProgram() method]

    @RequestMapping - specify on the method in the  controller, to map an HTTP request to the URL to this method.
    */

    @GetMapping(value = "/firstProgram/{userInput}")
    public String firstProgram(@PathVariable String userInput) {
        return userInput + "Hello World!";
    }

    //A get mapping to concatenate a string to itself
    @GetMapping(value = "/connectTwoStrings/{input1}/{input2}")
    public String concatString(@PathVariable(value = "input1") String str1,  //here URI has names input1, input2 and the method parameters has str1 and str2 -> use value
                               @PathVariable(value = "input2") String str2) {
        return str1 + " " + str2;
    }

    @GetMapping(value = "/concatString/{input1}")
    public String concatStringWithRequestParam(@PathVariable(value = "input1") String str1,
                               @RequestParam(value = "input2") String str2) {          // @RequestParam - bind HTTP parameters/Query params into  method arguments.
        return str1 + " " + str2;
    }

    //A GET mapping to receive a list of ticker symbols in the Request Body
    @GetMapping(value = "/tickersListPrinter/{app}")
    public String tickersListPrinter(@PathVariable String app,
                                     @RequestParam(value = "input") String input,
                                     @RequestBody List<String> tickerSymbols) {
        return app + " " + input + " "+ tickerSymbols.toString();
    }

    //A get mapping to receive Custom Request
    @GetMapping(value = "/customRequestReceiver")
    public List<String> customRequestReceiver(@RequestBody List<SampleRequest> sampleRequest) {
        List<String> tickerSymbols = sampleRequest.stream()
                .map(SampleRequest::getTickerSymbol)
                .collect(Collectors.toList());
        return tickerSymbols;
    }

}
