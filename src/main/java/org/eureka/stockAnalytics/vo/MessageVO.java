package org.eureka.stockAnalytics.vo;

import java.io.Serializable;

public class MessageVO implements Serializable {
    private String simpleText;

    public MessageVO(String simpleText) {
        this.simpleText = simpleText;
    }

    public String getSimpleText() {
        return simpleText;
    }

    public void setSimpleText(String simpleText) {
        this.simpleText = simpleText;
    }
}