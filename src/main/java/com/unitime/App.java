package com.unitime;

import java.util.List;
import java.util.Scanner;
import com.unitime.UI.IntroScreen;
import com.unitime.algorthm.Scheduler;
import com.unitime.feature.Course;
import com.unitime.feature.Editor;
import com.unitime.feature.InputHandler;

public class App {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLACK = "\u001B[30m";
    public static final String BOLD = "\u001B[1m";
    public static void main(String[] args) {
        
        // Only one scanner!!!!!
        Scanner sc = new Scanner(System.in);
        
        IntroScreen.start();
        System.out.println("Press"+PURPLE+" [ENTER]"+RESET+" to start...");
        sc.nextLine();

        // get input
        InputHandler inputHandler = new InputHandler();
        inputHandler.handle(sc);

        // First result
        System.out.println("\n[Algorithm] Generating optimal timetables...");
        Scheduler scheduler = new Scheduler();
        List<List<Course>> results = scheduler.schedule(
            inputHandler.getMandatoryList(), 
            inputHandler.getOptionalList(), 
            inputHandler.getMaxCredit()
        );

        // Editor is in charge of all post-operations (next,quit,edit)
        Editor editor = new Editor(
            results, 
            inputHandler.getMandatoryList(), 
            inputHandler.getOptionalList(), 
            inputHandler.getMaxCredit(),
            sc 
        );
        editor.start(); 
        
        sc.close();
    }
}