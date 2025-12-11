package com.unitime.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHandler {

    private List<Course> mandatoryList = new ArrayList<>();
    private List<Course> optionalList = new ArrayList<>();
    private int maxCredit = 0;

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";
    
    public InputHandler() {
        Scanner sc = new Scanner(System.in);

        System.out.println(PURPLE+"===== UniTime-Solver: Input Courses ====="+RESET);

        // Maximum credit
        System.out.print(BOLD+"What is your MAXIMUM total credit? (positive number): "+RESET);
        while (true) {
            try {
                maxCredit = Integer.parseInt(sc.nextLine().trim());
                if (maxCredit > 0) { 
                    System.out.println(BLUE+"Maximum credit checked."+RESET);
                    break; 
                }
                System.out.println(RED+"Please enter a positive number."+RESET);
            } catch (NumberFormatException e) {
                System.out.println(RED+"Invalid number. Please try again."+RESET);
            }
        }

        // Put into list
        
        // MandatoryList
        System.out.println(BOLD+"\n[1] Enter MANDATORY Courses"+RESET);
        inputLoop(sc, mandatoryList); 

        // OptionalList에 담기
        System.out.println(BOLD+"\n[2] Enter OPTIONAL Courses"+RESET);
        inputLoop(sc, optionalList); 

        // Show result
        System.out.println(ANSI+"\n=========================================="+RESET);
        System.out.println(YELLOW+"           Check Input Summary            "+RESET);
        System.out.println(ANSI+"=========================================="+RESET);
        System.out.println(BLUE+"Maximum Credit: " + maxCredit+RESET);
        
        // Mandatory
        System.out.println(GREEN+"\n[Fixed Courses] (" + mandatoryList.size() + " courses)"+RESET);
        if (mandatoryList.isEmpty()) System.out.println(" (None)");
        for (Course c : mandatoryList) {
            System.out.println(GREEN+" - " + c+RESET);
        }

        // Optional
        System.out.println(BOLD+"\n[Optional Courses] (" + optionalList.size() + " courses)"+RESET);
        if (optionalList.isEmpty()) System.out.println(" (None)");
        for (Course c : optionalList) {
            System.out.println(" - " + c);
        }

        System.out.println(CYAN+"=========================================="+RESET);
        System.out.println(YELLOW+"Finding timetables..."+RESET);
        System.out.println(CYAN+"=========================================="+RESET);
    }

    // Get user input
    public void inputLoop(Scanner sc, List<Course> targetList) {
        System.out.println(CYAN+"------------------------------------------------------------------"+RESET);
        System.out.println(BOLD+"Format: Name / Credit / Time"+RESET);
        System.out.println("Example: Data Structure / 3 / Mon 12:30 14:00");
        System.out.println(CYAN+"------------------------------------------------------------------"+RESET);

        while (true) {
            System.out.print("\nInput (or 'done'): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("done")) break;

            try {
                // Split by '/'
                String[] parts = input.split("/");
                if (parts.length != 3) {
                    throw new Exception(RED+"All 3 fields must be separated by '/'"+RESET);
                }

                // Trim and parse
                String name = parts[0].trim();
                int credit = Integer.parseInt(parts[1].trim());
                String timeString = parts[2].trim(); // "Mon 12:30 14:00"

                // Validate credit
                if (credit <= 0) {
                    throw new Exception(RED+"Credit must be a positive number"+RESET);
                }

                // Split time input
                String[] timeParts = timeString.split(" ");
                if (timeParts.length != 3) {
                    throw new Exception(RED+"Time format should be 'Day Start End'"+RESET);
                }

                // Handle 'day'
                int day = -1;
                String d = timeParts[0].toLowerCase();
                if (d.startsWith("m")) day = 0;
                else if (d.startsWith("tu")) day = 1;
                else if (d.startsWith("w")) day = 2;
                else if (d.startsWith("th")) day = 3;
                else if (d.startsWith("f")) day = 4;
                else throw new Exception(RED+"Invalid day (use Mon, Tue, Wed, Thu, or Fri)"+RESET);

                // Parse time
                int startMin = parseMin(timeParts[1]);
                int endMin = parseMin(timeParts[2]);

                if (startMin >= endMin) {
                    throw new Exception(RED+"End time must be after start time"+RESET);
                }

                // String for returning toString
                String timeRaw = timeParts[0] + " " + timeParts[1] + "-" + timeParts[2];

                // Add to list
                Course c = new Course(name, credit, day, startMin, endMin, timeRaw);
                targetList.add(c);
                System.out.println(YELLOW+" -> [Added] "+YELLOW + name);

            } catch (NumberFormatException e) {
                System.out.println(RED+"Error: Credit must be a valid number"+RESET);
                System.out.println(RED+"Try again: Name / Credit / Day Start End"+RESET);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(RED+"Error: Invalid time format"+RESET);
                System.out.println(RED+"Try again: Name / Credit / Day Start End"+RESET);
            } catch (Exception e) {
                System.out.println(RED+"Error: " + e.getMessage()+RESET);
                System.out.println(RED+"Try again: Name / Credit / Day Start End"+RESET);
            }
        }
    }

    // Change time into minutes
    public int parseMin(String t) throws Exception {
        try {
            String[] hhmm = t.split(":");
            if (hhmm.length != 2) {
                throw new Exception(RED+"Time must be in HH:MM format"+RESET);
            }
            int hours = Integer.parseInt(hhmm[0]);
            int minutes = Integer.parseInt(hhmm[1]);
            
            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
                throw new Exception(RED+"Invalid time range (hours: 0-23, minutes: 0-59)"+RESET);
            }
            
            return hours * 60 + minutes;
        } catch (NumberFormatException e) {
            throw new Exception(RED+"Time must contain valid numbers"+RESET);
        }
    }

    // Getters
    public List<Course> getMandatoryList() {
        return mandatoryList;
    }

    public List<Course> getOptionalList() {
        return optionalList;
    }

    public int getMaxCredit() {
        return maxCredit;
    }
}