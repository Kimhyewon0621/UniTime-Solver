package com.unitime.feature;

import org.junit.Test;
import static org.junit.Assert.*;

public class CourseTest {

    @Test
    public void testGetCredit() {
        Course course = new Course("Data Structure", 3, 0, 750, 840, "Mon 12:30-14:00");
        int credit = course.getCredit();
        assertEquals(3, credit);
    }

    @Test
    public void testGetDay() {
        Course course = new Course("Data Structure", 3, 0, 750, 840, "Mon 12:30-14:00");
        int day = course.getDay();
        assertEquals(0, day);
    }

    @Test
    public void testGetEndTime() {
        Course course = new Course("Data Structure", 3, 0, 750, 840, "Mon 12:30-14:00");
        int endTime = course.getEndTime();
        assertEquals(840, endTime);
    }

    @Test
    public void testGetName() {
        Course course = new Course("Data Structure", 3, 0, 750, 840, "Mon 12:30-14:00");
        String name = course.getName();
        assertEquals("Data Structure", name);
    }

    @Test
    public void testGetStartTime() {
        Course course = new Course("Data Structure", 3, 0, 750, 840, "Mon 12:30-14:00");
        int startTime = course.getStartTime();
        assertEquals(750, startTime);
    }

    @Test
    public void testToString() {
        Course course = new Course("Data Structure", 3, 0, 750, 840, "Mon 12:30-14:00");
        String result = course.toString();
        
        assertTrue(result.contains("Data Structure"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("Mon 12:30-14:00"));
    }

    @Test
    public void testToStringFormat() {
        Course course = new Course("Data Structure", 3, 0, 750, 840, "Mon 12:30-14:00");
        String result = course.toString();
        
        assertTrue(result.contains("Data Structure"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("credits"));
        assertTrue(result.contains("Mon 12:30-14:00"));
    }

    @Test
    public void testCourseWithDifferentDays() {
        // Monday (0)
        Course mon = new Course("Data Structure", 3, 0, 750, 840, "Mon 12:30-14:00");
        assertEquals(0, mon.getDay());
        
        // Tuesday (1)
        Course tue = new Course("Data Structure", 3, 1, 750, 840, "Tue 12:30-14:00");
        assertEquals(1, tue.getDay());
        
        // Wednesday (2)
        Course wed = new Course("Data Structure", 3, 2, 750, 840, "Wed 12:30-14:00");
        assertEquals(2, wed.getDay());
        
        // Thursday (3)
        Course thu = new Course("Data Structure", 3, 3, 750, 840, "Thu 12:30-14:00");
        assertEquals(3, thu.getDay());
        
        // Friday (4)
        Course fri = new Course("Data Structure", 3, 4, 750, 840, "Fri 12:30-14:00");
        assertEquals(4, fri.getDay());
    }

    @Test
    public void testCourseWithDifferentCredits() {
        Course c1 = new Course("Data Structure", 1, 0, 750, 840, "Mon 12:30-14:00");
        assertEquals(1, c1.getCredit());
        
        Course c2 = new Course("Data Structure", 2, 0, 750, 840, "Mon 12:30-14:00");
        assertEquals(2, c2.getCredit());
        
        Course c3 = new Course("Data Structure", 3, 0, 750, 840, "Mon 12:30-14:00");
        assertEquals(3, c3.getCredit());
        
        Course c4 = new Course("Data Structure", 4, 0, 750, 840, "Mon 12:30-14:00");
        assertEquals(4, c4.getCredit());
    }

    @Test
    public void testTimeConversion() {
        Course c1 = new Course("Data Structure", 3, 0, 540, 660, "Mon 09:00-11:00");
        assertEquals(540, c1.getStartTime());
        assertEquals(660, c1.getEndTime());
        
        Course c2 = new Course("Data Structure", 3, 0, 750, 870, "Mon 12:30-14:30");
        assertEquals(750, c2.getStartTime());
        assertEquals(870, c2.getEndTime());
        
        Course c3 = new Course("Data Structure", 3, 0, 1080, 1200, "Mon 18:00-20:00");
        assertEquals(1080, c3.getStartTime());
        assertEquals(1200, c3.getEndTime());
    }
}