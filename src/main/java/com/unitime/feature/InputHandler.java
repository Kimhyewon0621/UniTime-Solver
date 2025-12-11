package com.unitime.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHandler {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLACK = "\u001B[30m";
    public static final String BOLD = "\u001B[1m";

    private List<Course> mandatoryList = new ArrayList<>();
    private List<Course> optionalList = new ArrayList<>();
    private int maxCredit = 0;
    
    public void handle(Scanner sc) {
        System.out.println(CYAN +"===== UniTime-Solver: Input Courses ====="+ RESET);

        // Maximum credit
        System.out.print(YELLOW+"(*'▽ '*) What is your MAXIMUM total credit?"+RESET+" ('"+RED+"positive"+RESET+"' number): ");
        while (true) {
            try {
                maxCredit = Integer.parseInt(sc.nextLine().trim());
                if (maxCredit > 0) { 
                    System.out.println("Maximum credit checked.");
                    break; 
                }
                System.out.println("Please enter a '"+RED+"positive"+RESET+"' number.");
            } catch (NumberFormatException e) {
                System.out.println(RED+"Invalid number. Please try again."+RESET);
            }
        }

        // Put into list
        
        // MandatoryList
        System.out.println(BLUE+"\n[1] Enter MANDATORY Courses"+RESET);
        inputLoop(sc, mandatoryList); 

        // OptionalList
        System.out.println(BLUE+"\n[2] Enter OPTIONAL Courses"+RESET);
        inputLoop(sc, optionalList); 

        printSummary();
    }


    // Show result
    public void printSummary() {
        System.out.println(CYAN +"\n=========================================="+ RESET);
        System.out.println(PURPLE+"        (*>3<*) Check Input Summary            "+RESET);
        System.out.println(CYAN +"=========================================="+ RESET);
        System.out.println(RED+"Maximum Credit: "+RESET + maxCredit);
        
        // Mandatory
        System.out.println(GREEN+"\n[Fixed Courses]"+RESET+" (" + mandatoryList.size() + " courses)");
        if (mandatoryList.isEmpty()) System.out.println(" (None)");
        for (Course c : mandatoryList) {
            System.out.println(" - " + c);
        }

        // Optional
        System.out.println(GREEN+"\n[Optional Courses]"+RESET+" (" + optionalList.size() + " courses)");
        if (optionalList.isEmpty()) System.out.println(" (None)");
        for (Course c : optionalList) {
            System.out.println(" - " + c);
        }

        System.out.println(CYAN +"=========================================="+ RESET);
        System.out.println("             Finding timetables...");
        System.out.println(CYAN +"=========================================="+ RESET);
    }

    // Get user input
    public void inputLoop(Scanner sc, List<Course> targetList) {
        System.out.println(PURPLE +"------------------------------------------------------------------"+ RESET);
        System.out.println("                Format: Name / Credit / Time");
        System.out.println("                Example: Data Structure / 3 / Mon 12:30 14:00");
        System.out.println(PURPLE +"------------------------------------------------------------------"+ RESET);

        while (true) {
            System.out.print(BLUE+"\nInput (or 'done'): "+RESET);
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("done")) break;

            try {
                // Split by '/'
                String[] parts = input.split("/");
                if (parts.length != 3) {
                    throw new Exception(RED +"All 3 fields must be separated by '/'"+RESET);
                }

                // Trim and parse
                String name = parts[0].trim();
                int credit = Integer.parseInt(parts[1].trim());
                String timeString = parts[2].trim(); // "Mon 12:30 14:00"

                // Validate credit
                if (credit <= 0) {
                    throw new Exception(RED +"Credit must be a positive number"+RESET);
                }

                // Split time input
                String[] timeParts = timeString.split(" ");
                if (timeParts.length != 3) {
                    throw new Exception(RED +"Time format should be 'Day Start End'"+RESET);
                }

                // Handle 'day'
                int day = -1;
                String d = timeParts[0].toLowerCase();
                if (d.startsWith("m")) day = 0;
                else if (d.startsWith("tu")) day = 1;
                else if (d.startsWith("w")) day = 2;
                else if (d.startsWith("th")) day = 3;
                else if (d.startsWith("f")) day = 4;
                else throw new Exception(RED +"Invalid day (use Mon, Tue, Wed, Thu, or Fri)"+RESET);

                // Parse time
                int startMin = parseMin(timeParts[1]);
                int endMin = parseMin(timeParts[2]);

                if (startMin >= endMin) {
                    throw new Exception(RED +"End time must be after start time" +RESET);
                }

                // String for returning toString
                String timeRaw = timeParts[0] + " " + timeParts[1] + "-" + timeParts[2];

                // Add to list
                Course c = new Course(name, credit, day, startMin, endMin, timeRaw);
                targetList.add(c);
                System.out.println(GREEN+" -> [Added] " +RESET+ name);

            } catch (NumberFormatException e) {
                System.out.println(RED +"Error: Credit must be a valid number"+ RESET);
                System.out.println(RED +"Try again: Name / Credit / Day Start End"+ RESET);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(RED +"Error: Invalid time format"+ RESET);
                System.out.println(RED +"Try again: Name / Credit / Day Start End"+ RESET);
            } catch (Exception e) {
                System.out.println(RED +"Error: "+ RESET + e.getMessage());
                System.out.println(RED +"Try again: Name / Credit / Day Start End"+ RESET);
            }
        }
    }

    // Change time into minutes
    public int parseMin(String t) throws Exception {
        try {
            String[] hhmm = t.split(":");
            if (hhmm.length != 2) {
                throw new Exception(RED +"Time must be in HH:MM format"+ RESET);
            }
            int hours = Integer.parseInt(hhmm[0]);
            int minutes = Integer.parseInt(hhmm[1]);
            
            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
                throw new Exception(RED +"Invalid time range (hours: 0-23, minutes: 0-59)"+ RESET);
            }
            
            return hours * 60 + minutes;
        } catch (NumberFormatException e) {
            throw new Exception(RED +"Time must contain valid numbers"+ RESET);
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