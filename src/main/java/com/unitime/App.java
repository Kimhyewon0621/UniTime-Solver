package com.unitime;

import java.util.List;
import java.util.Scanner;

import com.unitime.UI.IntroScreen;
import com.unitime.UI.ResultView;
import com.unitime.algorthm.Scheduler;
import com.unitime.feature.Course;
import com.unitime.feature.InputHandler; 

public class App {
    public static void main( String[] args ) {
        
        Scanner sc = new Scanner(System.in);
        IntroScreen.start();
        System.out.println("Press [ENTER] to start...");
        sc.nextLine();
        while(true) {
            
            InputHandler inputHandler = new InputHandler(); 

            System.out.println("\n[Algorithm] Generating optimal timetables...");
            Scheduler scheduler = new Scheduler();
            List<List<Course>> results = scheduler.schedule(
                inputHandler.getMandatoryList(), 
                inputHandler.getOptionalList(), 
                inputHandler.getMaxCredit()
            );

            int currentIndex = 0;
            boolean viewingResults = true;

            while (viewingResults) {

                String command = ResultView.printBatchAndGetInput(results, currentIndex, sc);

                if (command.equals("next")) {

                    if (currentIndex + 5 < results.size()) {
                        currentIndex += 5;
                    } else {

                    }
                } 
                else if (command.equals("edit")) {
                    viewingResults = false;
                }
            }
        }
    }
        
}