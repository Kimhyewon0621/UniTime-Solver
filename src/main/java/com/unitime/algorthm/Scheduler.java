package com.unitime.algorthm;

import java.util.ArrayList;
import java.util.List;

import com.unitime.feature.Course;

public class Scheduler {

    private List<List<Course>> allSchedules;

    public List<List<Course>> schedule(List<Course> mandatoryList, List<Course> optionList, int goalCredit) {
        
        allSchedules = new ArrayList<>();

        List<Course> currentSchedule = new ArrayList<>();

        int currentCredit = 0;
        for (Course m : mandatoryList) {
            currentSchedule.add(m);
            currentCredit += m.getCredit();
        }

        findCombinations(0, currentSchedule, currentCredit, optionList, goalCredit);

        return allSchedules;
    }

    private void findCombinations(int index, List<Course> currentSchedule, int currentCredit, List<Course> optionList, int goalCredit) {
        allSchedules.add(new ArrayList<>(currentSchedule));

        for (int i = index; i < optionList.size(); i++) {
            Course candidate = optionList.get(i);

            if (currentCredit + candidate.getCredit() > goalCredit) {
                continue;
            }

            if (isTimeOverlap(currentSchedule, candidate)) {
                continue;
            }

            currentSchedule.add(candidate);

            findCombinations(i + 1, currentSchedule, currentCredit + candidate.getCredit(), optionList, goalCredit);

            currentSchedule.remove(currentSchedule.size() - 1);
        }
    }

    private boolean isTimeOverlap(List<Course> currentTable, Course newCourse) {
        for (Course existing : currentTable) {
            if (existing.getDay() != newCourse.getDay()) {
                continue;
            }
            if (existing.getStartTime() < newCourse.getEndTime() && 
                existing.getEndTime() > newCourse.getStartTime()) {
                return true; 
            }
        }
        return false;
    }
}