package com.unitime;

import java.util.List;
import java.util.Scanner;
import com.unitime.UI.IntroScreen;
import com.unitime.algorthm.Scheduler;
import com.unitime.feature.Course;
import com.unitime.feature.Editor;
import com.unitime.feature.InputHandler;

public class App {
    public static void main(String[] args) {
        
        // Only one scanner!!!!!
        Scanner sc = new Scanner(System.in);
        
        IntroScreen.start();
        System.out.println("Press [ENTER] to start...");
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