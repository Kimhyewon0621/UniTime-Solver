package com.unitime.feature;

import java.util.*;

import com.unitime.UI.ResultView;
import com.unitime.algorthm.Scheduler;

public class Editor {

    public List<List<Course>> schedules;

    private Editor (String input, List<List<Course>> schedules) { //schedules: Scheduler의 전체 시간표 리스트 넣어야 함
        this.schedules = schedules;

        input = input.toLowerCase();
        if (input.equals("quit")) quit();
        else if (input.equals("edit")) editList();
        else if (input.equals("next")) viewNext(0);
        else System.out.println("[Error] Invalid input.");
    }

    public void viewNext(int i) {
        ResultView rv = new ResultView();
        Scanner sc = new Scanner(System.in);

        int index = i + 5;
        
        String command = rv.printBatchAndGetInput(schedules, index, sc);
        
    }

    public void editList() {
        System.out.println("\n[Edit Mode] 과목을 수정합니다...");
        
        // TODO: 기존 필수/선택 과목 리스트 가져오기
        // (어딘가에 저장되어 있어야 함 - 전역 변수 or 파일 or 반환값)
        List<Course> mandatoryList = getMandatoryListFromSomewhere();
        List<Course> optionList = getOptionListFromSomewhere();
        int goalCredit = getGoalCreditFromSomewhere();
        
        // 사용자에게 수정 받기 (기존 입력 클래스 활용)
        CourseInputHandler.modifyCourses(mandatoryList, optionList, scanner);
        
        // 수정된 리스트로 Scheduler 재실행
        Scheduler scheduler = new Scheduler();
        List<List<Course>> newSchedules = scheduler.schedule(mandatoryList, optionList, goalCredit);
        
        // 처음부터 다시 출력 (currentIndex = 0)
        String input = ResultView.printBatchAndGetInput(newSchedules, 0, scanner);
        
        // 다시 라우팅
        if (input.equals("next")) {
            viewNext(newSchedules, 0, scanner);
        } else if (input.equals("edit")) {
            editList(newSchedules, scanner);
        }
    }

    public void quit() {

    }

}
