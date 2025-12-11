package com.unitime.feature;

import java.util.List;
import java.util.Scanner;

import com.unitime.UI.ResultView;
import com.unitime.algorthm.Scheduler;

public class Editor {

    private List<List<Course>> schedules;
    private List<Course> mandatoryList;
    private List<Course> optionList;
    private int goalCredit;
    
    private Scanner sc;

    //add color
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLACK = "\u001B[30m";
    public static final String BOLD = "\u001B[1m";


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

        if (input.equals("quit")) quit();
        else if (input.equals("edit")) editList(); 
        else if (input.equals("next")) viewLoop(5);
        else {
            System.out.println(RED+"[Error] Invalid input. Going back to View Mode."+RESET);
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
                if (index + 5 < schedules.size()) {
                    index += 5;
                } else {
                    System.out.println(BOLD + "[Info] No more schedules to show."+RESET);
                }
                continue;
            }
            break; 
        }
        
        route(nextInput, this.schedules); 
    }

    // 'edit': edit lists and send it back to InputHandler 
    private void editList() {
        System.out.println(YELLOW + "[Edit Mode]" + RESET);
        modifyCourse(this.mandatoryList, this.optionList); 

        System.out.println(GREEN + "Re-calculating schedules..."+ GREEN);

        Scheduler scheduler = new Scheduler();
        this.schedules = scheduler.schedule(this.mandatoryList, this.optionList, this.goalCredit);
    }

    // helper of 'edit'
    private void modifyCourse(List<Course> mandatory, List<Course> optional) {

        InputHandler ih = new InputHandler();

        while (true) {
            System.out.println(CYAN+"\n------------------------------------------"+RESET);
            System.out.println(BOLD+" Current Goal Credit: "+ RESET + this.goalCredit);
            System.out.println(GREEN+" Mandatory: " + mandatory.size() + " courses"+RESET);
            System.out.println(" Optional : " + optional.size() + " courses");
            System.out.println(CYAN+"------------------------------------------"+RESET);
            System.out.println("1. Add/Edit MANDATORY Courses");
            System.out.println("2. Add/Edit OPTIONAL Courses");
            System.out.println("3. Remove a Course");
            System.out.println("4. Change Goal Credit");
            System.out.println("0. Finish Editing (Run Scheduler)");
            System.out.print(YELLOW+"> Select: "+RESET);

            String choice = sc.nextLine().trim();

            // Add
            if (choice.equals("1")) {
                System.out.println(GREEN+"\n[Add to Mandatory] Type 'done' to finish."+RESET);
                ih.inputLoop(sc, mandatory);
            } 
            else if (choice.equals("2")) {
                System.out.println(BOLD+"\n[Add to Optional] Type 'done' to finish."+RESET);
                ih.inputLoop(sc, optional);
            } 
            else if (choice.equals("3")) {
                removeCourseHelper(mandatory, optional);
            } 
            else if (choice.equals("4")) {
                System.out.print(PURPLE+"Enter new max credit: "+RESET);
                try {
                    int newCredit = Integer.parseInt(sc.nextLine().trim());
                    if (newCredit > 0) {
                        this.goalCredit = newCredit;
                        System.out.println(GREEN+"Goal credit updated."+RESET);
                    } else {
                        System.out.println(RED+"Credit must be positive."+RESET);
                    }
                } catch (Exception e) {
                    System.out.println(RED+"Invalid number."+RESET);
                }
            } 
            else if (choice.equals("0")) {
                break;
            } 
            else {
                System.out.println(RED+"Invalid choice."+RESET);
            }
        }
    }

    // helper of 'edit': remove
    private void removeCourseHelper(List<Course> mandatory, List<Course> optional) {
        System.out.println(PURPLE+"\n[Remove Course]"+RESET);
        System.out.println("1. From Mandatory");
        System.out.println("2. From Optional");
        System.out.print(YELLOW+"> Select list: "+RESET);
        String listType = sc.nextLine().trim();

        List<Course> target = null;
        if (listType.equals("1")) target = mandatory;
        else if (listType.equals("2")) target = optional;
        else {
            System.out.println(BLUE+"Canceled."+RESET);
            return;
        }

        if (target.isEmpty()) {
            System.out.println(YELLOW+"This list is empty."+RESET);
            return;
        }

        for (int i = 0; i < target.size(); i++) {
            System.out.println("[" + i + "] " + target.get(i).getName());
        }

        System.out.print(BOLD+"Enter index to remove (or -1 to cancel): "+RESET);
        try {
            int idx = Integer.parseInt(sc.nextLine().trim());
            if (idx >= 0 && idx < target.size()) {
                Course removed = target.remove(idx);
                System.out.println(GREEN+"Removed: "+RESET + removed.getName());
            } else if (idx != -1) {
                System.out.println(RED+"Invalid index."+RESET);
            }
        } catch (Exception e) {
            System.out.println(RED+"Invalid index."+RESET);
        }
    }

    public void quit(){
        System.out.println(BLUE+"[Bye] Closing system..."+RESET);
    }
}