package com.by.wind.component.event;

public class MessageEvent {

    public final static String NETWORK_OK = "network_ok";
    public final static String NETWORK_FAIL = "network_fail";

    public final static String SPLASH_FINISH = "splash_finish";

    public final static String SCAN_MESSAGE = "scan_message";
    public final static String SCAN_TEAM = "scan_team";
    public final static String SCAN_SHOP = "scan_shop";
    public final static String SCAN_SALE = "scan_sale";

    public final static String BACK_MESSAGE = "back_message";
    public final static String BACK_TEAM =      "back_team";
    public final static String BACK_SHOP =      "back_shop";
    public final static String BACK_SALE =      "back_sale";

    public final static String CLOSE_MESSAGE = "close_message";
    public final static String CLOSE_TEAM = "close_team";
    public final static String CLOSE_SHOP = "close_shop";
    public final static String CLOSE_SALE = "close_sale";

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

}
