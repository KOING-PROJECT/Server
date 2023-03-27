package com.koing.server.koing_server.common.enums;

public enum MessageType {

    ENTER("ENTER", 1),
    TALK("TALK", 2),
    ;

    private String type;
    private int typeNumber;

    MessageType(String type, int typeNumber) {
        this.type = type;
        this.typeNumber = typeNumber;
    }

    public String getType() {
        return type;
    }

}
