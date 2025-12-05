package com.unitime.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHandler {

    private static List<Course> mandatoryList = new ArrayList<>();
    private static List<Course> optionalList = new ArrayList<>();
    private static int maxCredit = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.println("===== UniTime-Solver: Input Courses =====");

        // 목표 학점
        System.out.print("What is your MAXIMUM total credit? (positive number): ");
        while (true) {
            try {
                maxCredit = Integer.parseInt(sc.nextLine().trim());
                if (maxCredit > 0) { 
                    System.out.println("Maximum credit checked.");
                    break; 
                }
                System.out.println("Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }

        // Put into list
        
        // MandatoryList
        System.out.println("\n[1] Enter MANDATORY Courses");
        inputLoop(sc, mandatoryList); 

        // OptionalList에 담기
        System.out.println("\n[2] Enter OPTIONAL Courses");
        inputLoop(sc, optionalList); 

        // Show result
        System.out.println("\n==========================================");
        System.out.println("           Check Input Summary            ");
        System.out.println("==========================================");
        System.out.println("Maximum Credit: " + maxCredit);
        
        // Mandatory
        System.out.println("\n[Fixed Courses] (" + mandatoryList.size() + " courses)");
        if (mandatoryList.isEmpty()) System.out.println(" (None)");
        for (Course c : mandatoryList) {
            System.out.println(" - " + c);
        }

        // Optional
        System.out.println("\n[Optional Courses] (" + optionalList.size() + " courses)");
        if (optionalList.isEmpty()) System.out.println(" (None)");
        for (Course c : optionalList) {
            System.out.println(" - " + c);
        }

        System.out.println("==========================================");
        System.out.println("Finding timetables...");
        System.out.println("==========================================");

        sc.close();
    }

    // Get user input
    private static void inputLoop(Scanner sc, List<Course> targetList) {
        System.out.println("------------------------------------------------------------------");
        System.out.println("Format: Name / Credit / Time");
        System.out.println("Example: Data Structure / 3 / Mon 12:30 14:00");
        System.out.println("------------------------------------------------------------------");

        while (true) {
            System.out.print("\nInput (or 'done'): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("done")) break;

            try {
                // Split by '/'
                String[] parts = input.split("/");
                if (parts.length != 3) {
                    throw new Exception("All 3 fields must be separated by '/'");
                }

                // Trim and parse
                String name = parts[0].trim();
                int credit = Integer.parseInt(parts[1].trim());
                String timeString = parts[2].trim(); // "Mon 12:30 14:00"

                // Validate credit
                if (credit <= 0) {
                    throw new Exception("Credit must be a positive number");
                }

                // Split time input
                String[] timeParts = timeString.split(" ");
                if (timeParts.length != 3) {
                    throw new Exception("Time format should be 'Day Start End'");
                }

                // Handle 'day'
                int day = -1;
                String d = timeParts[0].toLowerCase();
                if (d.startsWith("m")) day = 0;
                else if (d.startsWith("tu")) day = 1;
                else if (d.startsWith("w")) day = 2;
                else if (d.startsWith("th")) day = 3;
                else if (d.startsWith("f")) day = 4;
                else throw new Exception("Invalid day (use Mon, Tue, Wed, Thu, or Fri)");

                // Parse time
                int startMin = parseMin(timeParts[1]);
                int endMin = parseMin(timeParts[2]);

                if (startMin >= endMin) {
                    throw new Exception("End time must be after start time");
                }

                // String for returning toString
                String timeRaw = timeParts[0] + " " + timeParts[1] + "-" + timeParts[2];

                // Add to list
                Course c = new Course(name, credit, day, startMin, endMin, timeRaw);
                targetList.add(c);
                System.out.println(" -> [Added] " + name);

            } catch (NumberFormatException e) {
                System.out.println("Error: Credit must be a valid number");
                System.out.println("Try again: Name / Credit / Day Start End");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Invalid time format");
                System.out.println("Try again: Name / Credit / Day Start End");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Try again: Name / Credit / Day Start End");
            }
        }
    }

    // Change time into minutes
    private static int parseMin(String t) throws Exception {
        try {
            String[] hhmm = t.split(":");
            if (hhmm.length != 2) {
                throw new Exception("Time must be in HH:MM format");
            }
            int hours = Integer.parseInt(hhmm[0]);
            int minutes = Integer.parseInt(hhmm[1]);
            
            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
                throw new Exception("Invalid time range (hours: 0-23, minutes: 0-59)");
            }
            
            return hours * 60 + minutes;
        } catch (NumberFormatException e) {
            throw new Exception("Time must contain valid numbers");
        }
    }

    //Getters
    public static List<Course> getMandatoryList() {
    return mandatoryList;
    }

    public static List<Course> getOptionalList() {
        return optionalList;
    }

    public static int getMaxCredit() {
        return maxCredit;
    }
}