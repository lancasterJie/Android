package com.example.work4.model;

public class Record {
    private int id;
    private String title;
    private String content;
    private String time;

    public Record(int id, String title, String content, String time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getTime() { return time; }
}
