package com.unitime.feature;

public class Course {
    private String name;        // course name
    private int credit;         // credit

    // time
    private int day;            // 0=Mon, 1=Tue, 2=Wed, 3=Thu, 4=Fri
    private int startTime;
    private int endTime;
    
    // for toString
    private String timeRaw;    

    public Course(String name, int credit, 
                  int day, int startTime, int endTime, String timeRaw) {
        this.name = name;
        this.credit = credit;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeRaw = timeRaw;
    }

    // Getters
    public String getName() { return name; }
    public int getCredit() { return credit; }
    public int getDay() { return day; }
    public int getStartTime() { return startTime; }
    public int getEndTime() { return endTime; }

    @Override
    public String toString() {
        return String.format("%s | %d (credits) | %s ", 
                name, credit, timeRaw);
    }
}