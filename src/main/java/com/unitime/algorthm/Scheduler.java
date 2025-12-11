package com.unitime.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeTableSolver {

    public List<List<Course>> solve(List<Course> mandatoryList, List<Course> optionList, int goalCredit) {
        
        List<List<Course>> suggestionList = new ArrayList<>();

        for (int i = 0; i < 5; i++) { 
            
            List<Course> oneTable = new ArrayList<>();
            int currentTotalCredit = 0;

            for (Course m : mandatoryList) { 
                oneTable.add(m);
                currentTotalCredit += m.getCredit();
            }

            List<Course> shuffledOptions = new ArrayList<>(optionList);
            Collections.shuffle(shuffledOptions);

            for (Course o : shuffledOptions) {
                if (currentTotalCredit + o.getCredit() > goalCredit) { 
                    continue;  
                }

                if (!isTimeOverlap(oneTable, o)) { 
                    oneTable.add(o);
                    currentTotalCredit += o.getCredit();
                }
            }

            suggestionList.add(oneTable);
        }

        return suggestionList;
    }

    
    private boolean isTimeOverlap(List<Course> currentTable, Course newCourse) {
        for (Course existing : currentTable) {
            if (existing.getStartTime() < newCourse.getEndTime() && 
            existing.getEndTime() > newCourse.getStartTime()) {
                return true; 
            }
        }
        return false;
    }
}