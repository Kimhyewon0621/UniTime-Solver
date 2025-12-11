package com.unitime;

import java.util.List;
import java.util.Scanner;

// 지금까지 만든 파일들(패키지) 가져오기
import com.unitime.UI.ResultView;
import com.unitime.feature.InputHandler;
import com.unitime.feature.Course;
import com.unitime.algorthm.Scheduler; // 패키지명 오타(algorthm) 주의! 수정했다면 algorithm으로 변경하세요.

public class App {
    public static void main( String[] args ) {
        // 1. 프로그램 전체에서 쓸 스캐너 생성
        Scanner sc = new Scanner(System.in);

        // 2. 인트로 화면 보여주기 (IntroScreen 코드가 있다면 주석 해제)
        // IntroScreen.show(); 
        System.out.println("\n=== Welcome to UniTime-Solver! ==="); // 임시 인트로
        System.out.println("Press [ENTER] to start...");
        sc.nextLine();

        // 3. 메인 무한 루프 (edit을 누르면 다시 여기로 돌아옴)
        while(true) {
            
            // --- [STEP 1] 사용자 입력 받기 ---
            // InputHandler가 생성되면서 사용자에게 과목 입력을 받음
            InputHandler inputHandler = new InputHandler(); 

            // --- [STEP 2] 시간표 짜기 (알고리즘 가동) ---
            System.out.println("\n[Algorithm] Generating optimal timetables...");
            Scheduler scheduler = new Scheduler();
            List<List<Course>> results = scheduler.schedule(
                inputHandler.getMandatoryList(), 
                inputHandler.getOptionalList(), 
                inputHandler.getMaxCredit()
            );

            // --- [STEP 3] 결과 보여주기 (View Loop) ---
            int currentIndex = 0;
            boolean viewingResults = true;

            while (viewingResults) {
                // ResultView를 불러서 화면을 보여주고, 사용자의 선택("next" or "edit")을 받아옴
                String command = ResultView.printBatchAndGetInput(results, currentIndex, sc);

                if (command.equals("next")) {
                    // 다음 페이지로 이동 로직
                    if (currentIndex + 5 < results.size()) {
                        currentIndex += 5;
                    } else {
                        // 더 이상 없는데 next 누른 경우 (ResultView에서 이미 메시지 띄웠으므로 패스)
                    }
                } 
                else if (command.equals("edit")) {
                    // 입력 화면으로 돌아가기 위해 내부 루프 탈출 -> 외부 while문의 처음으로 이동
                    viewingResults = false;
                }
            }
        }
    }
}
