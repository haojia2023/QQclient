package qqcommon;

import java.io.Serializable;

public class Message implements Serializable ,MessageType {
    private static final long serialVersionUID = 1l;

    private String sender;
    private String getter;

    private String content;
    private String sendTime;

    public String getMessageType() {
        return messageType;
    }

    private String messageType;

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
