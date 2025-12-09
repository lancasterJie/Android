package com.example.fourthhomework;

public class Record {
    private int _id;         // 主键（自增）
    private String title;    // 标题（前几个字）
    private String content;  // 完整内容
    private String time;     // 保存时间

    // 构造方法（新增时用，无需传入 _id）
    public Record(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    // 构造方法（查询/编辑时用，包含 _id）
    public Record(int _id, String title, String content, String time) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    // Getter 和 Setter
    public int get_id() { return _id; }
    public void set_id(int _id) { this._id = _id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}