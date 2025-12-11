package com.unitime.feature;

public class Course implements Comparable<Course> {
    
    private String name;
    private int credit;
    private int day;
    private int startTime;
    private int endTime;
    private String timeRaw;    

    // =======================================================
    // [추가 1] 필수 과목인지 여부를 저장하는 변수
    // =======================================================
    private boolean isMandatory = false; 


    public Course(String name, int credit, int day, int startTime, int endTime, String timeRaw) {
        this.name = name;
        this.credit = credit;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeRaw = timeRaw;
    }

    // ... (기존 Getters 생략) ...
    public String getName() { return name; }
    public int getCredit() { return credit; }
    public int getDay() { return day; }
    public int getStartTime() { return startTime; }
    public int getEndTime() { return endTime; }


    // =======================================================
    // [추가 2] "이건 필수야!" 라고 설정하는 메서드 (에러 해결 핵심!)
    // =======================================================
    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    // =======================================================
    // [추가 3] 필수인지 아닌지 확인하는 메서드 (ResultView에서 색깔 칠할 때 씀)
    // =======================================================
    public boolean isMandatory() {
        return isMandatory;
    }


    @Override
    public int compareTo(Course other) {
        if (this.day != other.day) {
            return Integer.compare(this.day, other.day);
        }
        return Integer.compare(this.startTime, other.startTime);
    }

    @Override
    public String toString() {
        return String.format("%s | %d (credits) | %s ", name, credit, timeRaw);
    }
}