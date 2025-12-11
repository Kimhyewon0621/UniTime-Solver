package com.unitime.algorthm;

import java.util.ArrayList;
import java.util.List;

import com.unitime.feature.Course;

public class Scheduler {
    //List to store all valid timetable combinations
    private List<List<Course>> allSchedules;
    
    /**
     * Creates all possible schedules that fit your rules (time & credit limit)
     * 
     * @param mandatoryList List of courses that MUST be included
     * @param optionList List of optional courses that CAN be combined
     * @param goalCredit Maximum total credit allowed
     * @return List<List<Course>> All valid schedule combinations found
     */
    public List<List<Course>> schedule(List<Course> mandatoryList, List<Course> optionList, int goalCredit) {
        
        allSchedules = new ArrayList<>();

        List<Course> currentSchedule = new ArrayList<>();

        int currentCredit = 0;
        
        // 1. Add mandatory courses to the schedule first
        for (Course m : mandatoryList) {
            currentSchedule.add(m);
            currentCredit += m.getCredit();
        }

        // 2. Start the recursive search to find combinations
        findCombinations(0, currentSchedule, currentCredit, optionList, goalCredit);

        return allSchedules;
    }
    /**
     * Recursively finds all valid course combinations (Backtracking)
     * @param index Start index for searching in optionList (prevents duplicates)
     * @param currentSchedule List of courses currently selected in this recursion
     * @param currentCredit Total credit hours accumulated in the current schedule
     */
    private void findCombinations(int index, List<Course> currentSchedule, int currentCredit, List<Course> optionList, int goalCredit) {
        allSchedules.add(new ArrayList<>(currentSchedule));

        // Loop through remaining options
        // Start from 'index' to prevent duplicates (e.g., A+B and B+A)
        for (int i = index; i < optionList.size(); i++) {
            Course candidate = optionList.get(i);
            
            // [Skip Rule 1] Stop if adding the course exceeds the max credit allowed
            if (currentCredit + candidate.getCredit() > goalCredit) {
                continue;
            }

            // [Skip Rule 2] Stop if the course overlaps with the current schedule
            if (isTimeOverlap(currentSchedule, candidate)) {
                continue;
            }

            // 1. CHOOSE: Add the course
            currentSchedule.add(candidate);

            // 2. RECURSE: Go deeper to find the next course (start from i + 1)
            findCombinations(i + 1, currentSchedule, currentCredit + candidate.getCredit(), optionList, goalCredit);

            // 3. BACKTRACK: Remove the course to try the next option in the loop
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