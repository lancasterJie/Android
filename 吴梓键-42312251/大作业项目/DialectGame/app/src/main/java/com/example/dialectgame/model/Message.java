package com.example.dialectgame.model;

public class Message {
    private String content;
    private final boolean isUser;
    private final String timestamp;
    private boolean isComplete;

    public Message(String content, boolean isUser, String timestamp) {
        this.content = content;
        this.isUser = isUser;
        this.timestamp = timestamp;
        this.isComplete = isUser;
    }

    public String getContent() { return content; }
    public boolean isUser() { return isUser; }
    public String getTimestamp() { return timestamp; }
    public boolean isComplete() { return isComplete; }

    public void appendContent(String newText) {
        this.content += newText;
    }

    public void markComplete() {
        this.isComplete = true;
    }
}
