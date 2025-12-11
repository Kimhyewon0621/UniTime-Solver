package com.unitime.UI;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.unitime.feature.Course;

public class ResultView {

    // =============================================================
    // 1. Style constant (ANSI Code)
    // =============================================================
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLACK = "\u001B[30m";
    public static final String BOLD = "\u001B[1m";

    private static final int BATCH_SIZE = 5;

    /**
     * [Core Method]
     * Displays the current batch of timetables and returns the user's selection (next/edit).
     * Note: This method is purely for display and input retrieval; it does not contain pagination logic.
     * * @param allSchedules The complete list of generated timetables.
     * @param currentIndex The starting index for the current batch (managed externally by the controller).
     * @param scanner      The Scanner instance for receiving user input.
     * @return The command entered by the user (e.g., "next" or "edit").
     */
    public static String printBatchAndGetInput(List<List<Course>> allSchedules, int currentIndex, Scanner scanner) {
        
        // 1. Exception for no timetable
        if (allSchedules == null || allSchedules.isEmpty()) {
            System.out.println(RED + "\n( T_T ) No timetables found." + RESET);
            return "edit"; // Instantly go to "edit"
        }

        int totalSize = allSchedules.size();
        int endIndex = Math.min(currentIndex + BATCH_SIZE, totalSize);
        boolean hasNext = (endIndex < totalSize);

        // 2. Print Header (Print in each new page)
        System.out.println(CYAN + "\n========================= Timetable =========================" + RESET);
        System.out.println(YELLOW + BOLD + "  (*'▽ '*) Found " + totalSize + " timetables! Showing " + (currentIndex + 1) + "~" + endIndex + " (*'▽ '*)" + RESET);
        System.out.println(CYAN + "=============================================================" + RESET);

        // 3. Print 5 timetables
        for (int i = currentIndex; i < endIndex; i++) {
            printSingleSchedule(i + 1, allSchedules.get(i));
        }

        // 4. Print Neviagtion menu
        printNavigationMenu(hasNext);

        // 5. Get user input and return
        while (true) {
        System.out.print("Your choice > ");
        String input = scanner.nextLine().trim().toLowerCase();

        //Edit: return only 'next' or 'edit'
        if (input.equals("next") || input.equals("edit")) {
            return input;
        }

        //Enter wrong message -> input again (loop)
        System.out.println(RED + "Invalid command! Please type 'next' or 'edit' exactly." + RESET);

        
    }
}

    /**
     * Print each time table
     */
    private static void printSingleSchedule(int index, List<Course> schedule) {
        // Sort
        Collections.sort(schedule, (c1, c2) -> {
            if (c1.getDay() != c2.getDay()) {
                return Integer.compare(c1.getDay(), c2.getDay());
            }
            return Integer.compare(c1.getStartTime(), c2.getStartTime());
        });

        int totalCredit = 0;
        for (Course c : schedule) totalCredit += c.getCredit();

        System.out.println(PURPLE + "\n              ────────────────────────────────");
        System.out.println(String.format("             │   Recommended Timetable No.%02d   │", index));
        System.out.println("              ────────────────────────────────" + RESET);

        if (schedule.isEmpty()) {
             System.out.println("         " + YELLOW + "(Empty Schedule)" + RESET);
        } else {
            for (Course course : schedule) {
                String dayStr = getDayString(course.getDay());
                String timeStr = formatTime(course.getStartTime());
                
                System.out.println("         " + GREEN + String.format("[%s %s]", dayStr, timeStr) + RESET + 
                                   " " + course.getName() + " (" + course.getCredit() + " Credit)");
            }
        }
        System.out.println("         " + BLACK + "Total: " + totalCredit + " Credits" + RESET);
    }

    /**
     * Print choosing menu('next' or 'edit')
     */
    private static void printNavigationMenu(boolean hasNext) {
        System.out.println(CYAN + "\n-------------------------------------------------------------" + RESET);
        
        if (hasNext) {
            System.out.println("              ( > ω < ) Select: [" + BLUE + "next" + RESET + "] or [" + RED + "edit" + RESET + "] ");
            System.out.println("     Type '" + BLUE + "next" + RESET + "' to see more timetables.");
        } else {
            System.out.println("              ( > ω < ) Select: [" + RED + "edit" + RESET + "] ");
            System.out.println("     " + YELLOW + "(End of List)" + RESET + " No more timetables.");
        }
        
        System.out.println("     Type '" + RED + "edit" + RESET + "' to modify your courses.");
        System.out.println(CYAN + "-------------------------------------------------------------" + RESET);
    }

    // --- Utility Methods (Day/Time Conversion) ---
    private static String getDayString(int day) {
        switch (day) {
            case 0: return "Mon"; case 1: return "Tue"; case 2: return "Wed";
            case 3: return "Thu"; case 4: return "Fri"; default: return "???";
        }
    }

    private static String formatTime(int minutes) {
        return String.format("%02d:%02d", minutes / 60, minutes % 60);
    }
}