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
        
        // 1. Scanner는 프로그램 전체에서 단 하나만 생성합니다.
        Scanner sc = new Scanner(System.in);
        
        IntroScreen.start();
        System.out.println("Press [ENTER] to start...");
        sc.nextLine();

        // 2. 초기 입력 받기
        InputHandler inputHandler = new InputHandler();
        inputHandler.handle(sc); // 생성한 스캐너 전달

        // 3. 첫 스케줄 계산
        System.out.println("\n[Algorithm] Generating optimal timetables...");
        Scheduler scheduler = new Scheduler();
        List<List<Course>> results = scheduler.schedule(
            inputHandler.getMandatoryList(), 
            inputHandler.getOptionalList(), 
            inputHandler.getMaxCredit()
        );

        // 4. Editor 생성 및 제어권 넘기기 (이후 처리는 Editor가 담당)
        // Editor가 'next'나 'edit' 루프를 모두 관리하도록 합니다.
        Editor editor = new Editor(
            results, 
            inputHandler.getMandatoryList(), 
            inputHandler.getOptionalList(), 
            inputHandler.getMaxCredit(),
            sc // 스캐너 전달
        );

        editor.start(); 
        
        // Editor 내부에서 System.exit(0) 하거나 루프가 끝나면 여기로 옴
        sc.close();
    }
}