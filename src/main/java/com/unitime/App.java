package com.unitime;

import java.util.List;
import java.util.Scanner;

import com.unitime.UI.IntroScreen;
import com.unitime.UI.ResultView;
import com.unitime.feature.InputHandler;
import com.unitime.feature.Editor;
import com.unitime.feature.Course;
import com.unitime.algorthm.Scheduler; 

public class App {
    public static void main( String[] args ) {
        
        Scanner sc = new Scanner(System.in);
        
        // 1. 인트로
        IntroScreen.start(sc); 

        // 2. [최초 1회] InputHandler 생성 
        // (주의: InputHandler를 수정 안 했으니, new 하는 순간 질문들이 쏟아집니다.)
        // (이게 정상입니다. 프로그램 켤 때 한 번은 물어봐야 하니까요.)
        InputHandler inputHandler = new InputHandler(); 

        List<Course> mandatory = inputHandler.getMandatoryList();
        List<Course> optional = inputHandler.getOptionalList();
        int maxCredit = inputHandler.getMaxCredit();

        // 3. 알고리즘 실행
        System.out.println("\n[Algorithm] Generating optimal timetables...");
        Scheduler scheduler = new Scheduler();
        List<List<Course>> results = scheduler.schedule(mandatory, optional, maxCredit);

        // 4. 메인 루프
        while(true) {
            
            // --- View Mode ---
            int currentIndex = 0;
            boolean viewingResults = true;
            String command = "";

            while (viewingResults) {
                command = ResultView.printBatchAndGetInput(results, currentIndex, sc);

                if (command.equals("next")) {
                    if (currentIndex + 5 < results.size()) {
                        currentIndex += 5;
                    } 
                } 
                else if (command.equals("edit")) {
                    viewingResults = false;
                }
            }

            // --- Edit Mode ---
            if (command.equals("edit")) {
                // Editor 생성
                Editor editor = new Editor(mandatory, optional, maxCredit, sc);
                
                // Editor 실행 -> (여기서 InputHandler를 안 쓰니 질문 폭탄이 안 나옴!)
                editor.runEditMode(); 

                // 업데이트된 학점 반영
                maxCredit = editor.getGoalCredit();

                // 스케줄러 재실행
                System.out.println("\n[Update] Re-calculating schedules...");
                results = scheduler.schedule(mandatory, optional, maxCredit);
            }
        }
    }
}