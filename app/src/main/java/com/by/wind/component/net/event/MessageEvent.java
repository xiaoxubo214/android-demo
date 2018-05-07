package com.by.wind.component.net.event;

public class MessageEvent {

    public final static String NETWORK_OK = "network_ok";
    public final static String NETWORK_FAIL = "network_fail";

    private String eventType;

    public MessageEvent(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


}
