package com.varun.alarmandreminder;

public class Remind {
    String title;

    public Remind(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
class Alarm extends Remind{
String time;
    public Alarm(String title, String time) {
        super(title);
        this.time = time;
    }
    public String getTime(){
        return time;
    }
}
class Reminder extends Remind{
    String date;

    public Reminder(String title, String date) {
        super(title);
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}