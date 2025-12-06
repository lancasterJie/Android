package com.example.lvassignment;

public class Task {
    private String title;
    private String time;
    private boolean isStar;
    private boolean isDone;
    public Task(String title,String time){
        this.title=title;
        this.time=time;
        this.isStar=false;
        this.isDone=false;
    }
    public String getTitle(){return title;}
    public String getTime(){return time;}
    public boolean isStar(){return isStar;}
    public boolean isDone(){return isDone;}

    public void setDone(boolean done){isDone=done;}

    public void setStar(boolean star){isStar=star;}
}
