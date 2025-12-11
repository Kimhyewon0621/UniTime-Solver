package com.unitime.feature;

import java.util.*;
import com.unitime.UI.ResultView;
import com.unitime.algorthm.Scheduler;

public class Editor {

    private List<List<Course>> schedules;
    private List<Course> mandatoryList;
    private List<Course> optionList;
    private int goalCredit;
    
    private Scanner sc; 

    // Constructor
    public Editor(List<List<Course>> schedules, List<Course> mandatory, List<Course> optional, int credit, Scanner scanner) {
        this.schedules = schedules;
        this.mandatoryList = mandatory;
        this.optionList = optional;
        this.goalCredit = credit;
        this.sc = scanner;
    }

    // Routing: send to function depending on its input
    public void route(String input, List<List<Course>> currentSchedules) {
        if (currentSchedules != null) {
            this.schedules = currentSchedules;
        }

        input = input.toLowerCase().trim();

        if (input.equals("quit")) System.quit(0);
        else if (input.equals("edit")) editList(); 
        else if (input.equals("next")) viewLoop(5);
        else {
            System.out.println("[Error] Invalid input. Going back to View Mode.");
            viewLoop(0);
        }
    }

    // 'next': loop to show 5 more schedules
    private void viewLoop(int startIndex) {
        int index = startIndex;
        String nextInput;

        while (true) {
            ResultView rv = new ResultView();
            nextInput = rv.printBatchAndGetInput(this.schedules, index, this.sc);

            if (nextInput.equals("next")) {
                index += 5;
                continue;
            }
            break; 
        }
        
        route(nextInput, this.schedules); 
    }

    // 'edit': edit lists and send it back to InputHandler 
    private void editList() {
        System.out.println("[Edit Mode]");
        modifyCourse(this.mandatoryList, this.optionList); 

        System.out.println("Re-calculating schedules...");

        Scheduler scheduler = new Scheduler();
        this.schedules = scheduler.schedule(this.mandatoryList, this.optionList, this.goalCredit);
    }

    // helper of 'edit'
    private void modifyCourse(List<Course> mandatory, List<Course> optional) {

        InputHandler ih = new InputHandler();

        while (true) {
            System.out.println("\n------------------------------------------");
            System.out.println(" Current Goal Credit: " + this.goalCredit);
            System.out.println(" Mandatory: " + mandatory.size() + " courses");
            System.out.println(" Optional : " + optional.size() + " courses");
            System.out.println("------------------------------------------");
            System.out.println("1. Add/Edit MANDATORY Courses");
            System.out.println("2. Add/Edit OPTIONAL Courses");
            System.out.println("3. Remove a Course");
            System.out.println("4. Change Goal Credit");
            System.out.println("0. Finish Editing (Run Scheduler)");
            System.out.print("> Select: ");

            String choice = sc.nextLine().trim();

            // Add
            if (choice.equals("1")) {
                System.out.println("\n[Add to Mandatory] Type 'done' to finish.");
                ih.inputLoop(sc, mandatory);
            } 
            else if (choice.equals("2")) {
                System.out.println("\n[Add to Optional] Type 'done' to finish.");
                ih.inputLoop(sc, optional);
            } 
            else if (choice.equals("3")) {
                removeCourseHelper(mandatory, optional);
            } 
            else if (choice.equals("4")) {
                System.out.print("Enter new max credit: ");
                try {
                    int newCredit = Integer.parseInt(sc.nextLine().trim());
                    if (newCredit > 0) {
                        this.goalCredit = newCredit;
                        System.out.println("Goal credit updated.");
                    } else {
                        System.out.println("Credit must be positive.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid number.");
                }
            } 
            else if (choice.equals("0")) {
                break;
            } 
            else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // helper of 'edit': remove
    private void removeCourseHelper(List<Course> mandatory, List<Course> optional) {
        System.out.println("\n[Remove Course]");
        System.out.println("1. From Mandatory");
        System.out.println("2. From Optional");
        System.out.print("> Select list: ");
        String listType = sc.nextLine().trim();

        List<Course> target = null;
        if (listType.equals("1")) target = mandatory;
        else if (listType.equals("2")) target = optional;
        else {
            System.out.println("Canceled.");
            return;
        }

        if (target.isEmpty()) {
            System.out.println("This list is empty.");
            return;
        }

        for (int i = 0; i < target.size(); i++) {
            System.out.println("[" + i + "] " + target.get(i).getName());
        }

        System.out.print("Enter index to remove (or -1 to cancel): ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim());
            if (idx >= 0 && idx < target.size()) {
                Course removed = target.remove(idx);
                System.out.println("Removed: " + removed.getName());
            } else if (idx != -1) {
                System.out.println("Invalid index.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public void exit(){
        System.out.println("[Bye] Closing system...");
    }
}