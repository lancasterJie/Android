package com.example.test3;

public class Record {
    private final long id;
    private final String title;
    private final String content;
    private final String time;

    public Record(long id, String title, String content, String time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}

