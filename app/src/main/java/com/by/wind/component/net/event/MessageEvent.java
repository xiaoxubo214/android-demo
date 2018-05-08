package com.by.wind.component.net.event;

public class MessageEvent {

    public final static String NETWORK_OK = "network_ok";
    public final static String NETWORK_FAIL = "network_fail";

    public final static String SPLASH_FINISH = "splash_finish";

    public final static String SCAN_MESSAGE = "message";
    public final static String SCAN_TEAM = "scan";
    public final static String SCAN_SHOP = "ship";
    public final static String SCAN_SALE = "sale";

    private String eventType;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public MessageEvent(String eventType) {
        this.eventType = eventType;
    }

    public MessageEvent(String eventType,String message) {
        this.eventType = eventType;
        this.message = message;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


}
