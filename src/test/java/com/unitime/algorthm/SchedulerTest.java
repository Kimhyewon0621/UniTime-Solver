package com.unitime.algorthm;

import static org.junit.Assert.*; 
import org.junit.Test;           
import java.util.ArrayList;
import java.util.List;
import com.unitime.feature.Course;

public class SchedulerTest {

    @Test
    public void testBasicSchedule() {
        List<Course> mandatory = new ArrayList<>();
        mandatory.add(new Course("OSS", 3, 0, 600, 720, "Mon 10:00-12:00"));

        List<Course> option = new ArrayList<>();
        option.add(new Course("CS", 3, 1, 600, 720, "Tue 10:00-12:00"));
        
        Scheduler scheduler = new Scheduler();
        List<List<Course>> result = scheduler.schedule(mandatory, option, 10);

        assertTrue(result.size() > 0);
    }

    @Test
    public void testTimeConflict() {
        List<Course> mandatory = new ArrayList<>();
        mandatory.add(new Course("OSS", 3, 0, 600, 720, "Mon 10:00-12:00"));

        List<Course> option = new ArrayList<>();
        option.add(new Course("Tennis", 2, 0, 660, 750, "Mon 11:00-12:30"));

        Scheduler scheduler = new Scheduler();
        List<List<Course>> result = scheduler.schedule(mandatory, option, 10);

        List<Course> firstSchedule = result.get(0);
        boolean hasConflict = false;
        for(Course c : firstSchedule) {
            if(c.getName().contains("Tennis")) {
                hasConflict = true;
            }
        }

        assertFalse(hasConflict);
    }

    @Test
    public void testCreditLimit() {
        List<Course> mandatory = new ArrayList<>();
        mandatory.add(new Course("OSS", 3, 0, 600, 720, "Mon 10:00"));

        List<Course> option = new ArrayList<>();
        option.add(new Course("DS)", 3, 1, 600, 720, "Tue"));
        option.add(new Course("CS", 3, 2, 600, 720, "Wed"));
        option.add(new Course("BPM", 3, 3, 600, 720, "Thu"));

        int goalCredit = 5;

        Scheduler scheduler = new Scheduler();
        List<List<Course>> result = scheduler.schedule(mandatory, option, goalCredit);

        int totalCredit = 0;
        for(Course c : result.get(0)) {
            totalCredit += c.getCredit();
        }

        assertTrue(totalCredit <= goalCredit);
    }
}