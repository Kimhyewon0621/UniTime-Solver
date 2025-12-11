package com.unitime.feature;

import java.util.*;

public class Editor {

    private List<Course> mandatoryList;
    private List<Course> optionList;
    private int goalCredit;
    private Scanner sc; 

    public Editor(List<Course> mandatory, List<Course> optional, int credit, Scanner scanner) {
        this.mandatoryList = mandatory;
        this.optionList = optional;
        this.goalCredit = credit;
        this.sc = scanner;
    }

    // [핵심] App.java에서 호출하는 메서드
    public void runEditMode() {
        System.out.println("\n==========================================");
        System.out.println("             [ EDIT MODE ]");
        System.out.println("==========================================");
        
        modifyCourse(this.mandatoryList, this.optionList); 

        System.out.println("Exiting Edit Mode... Saving changes...");
    }

    private void modifyCourse(List<Course> mandatory, List<Course> optional) {
        
        // [중요] 여기에 'new InputHandler()'가 있으면 절대 안 됩니다! (삭제됨)

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
            System.out.println("0. Finish Editing & View Timetables");
            System.out.print("> Select: ");

            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println("\n[Add to Mandatory] Type 'done' to finish.");
                // InputHandler 대신 직접 만든 입력 메서드를 씁니다.
                addCourseLoop(mandatory, true); 
            } 
            else if (choice.equals("2")) {
                System.out.println("\n[Add to Optional] Type 'done' to finish.");
                addCourseLoop(optional, false);
            } 
            else if (choice.equals("3")) {
                removeCourseHelper(mandatory, optional);
            } 
            else if (choice.equals("4")) {
                changeCredit();
            } 
            else if (choice.equals("0")) {
                break; 
            } 
            else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // [해결책] InputHandler의 입력 로직을 여기로 가져와서 직접 처리합니다.
    // 이렇게 하면 '최대 학점'을 묻는 불필요한 질문 없이 과목만 추가할 수 있습니다.
    private void addCourseLoop(List<Course> targetList, boolean isMandatory) {
        System.out.println("------------------------------------------------------------------");
        System.out.println("Format: Name / Credit / Time");
        System.out.println("Example: Data Structure / 3 / Mon 12:30 14:00");
        System.out.println("------------------------------------------------------------------");

        while (true) {
            System.out.print("\nInput (or 'done'): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("done")) break;

            try {
                // 1. '/' 기준으로 나누기
                String[] parts = input.split("/");
                if (parts.length != 3) throw new Exception("Use '/' to separate fields");

                String name = parts[0].trim();
                int credit = Integer.parseInt(parts[1].trim());
                String timeString = parts[2].trim();
                
                // 2. 시간 파싱 (Day Start End)
                String[] timeParts = timeString.split(" ");
                if (timeParts.length != 3) throw new Exception("Time format: Day Start End");

                int day = parseDay(timeParts[0]);
                int startMin = parseMin(timeParts[1]);
                int endMin = parseMin(timeParts[2]);

                if (startMin >= endMin) throw new Exception("End time must be after start time");

                String timeRaw = timeParts[0] + " " + timeParts[1] + "-" + timeParts[2];
                
                // 3. 과목 생성 및 추가
                Course c = new Course(name, credit, day, startMin, endMin, timeRaw);
                
                // 스크린샷에 있던 에러 해결: setMandatory 메서드 호출
                if (isMandatory) c.setMandatory(true); 
                
                targetList.add(c);
                System.out.println(" -> [Added] " + name);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Try again: Name / Credit / Day Start End");
            }
        }
    }

    // [헬퍼] 요일 파싱
    private int parseDay(String d) throws Exception {
        d = d.toLowerCase();
        if (d.startsWith("m")) return 0;
        if (d.startsWith("tu")) return 1;
        if (d.startsWith("w")) return 2;
        if (d.startsWith("th")) return 3;
        if (d.startsWith("f")) return 4;
        throw new Exception("Invalid day");
    }

    // [헬퍼] 시간 파싱
    private int parseMin(String t) throws Exception {
        String[] hhmm = t.split(":");
        if (hhmm.length != 2) throw new Exception("Time format HH:MM");
        int h = Integer.parseInt(hhmm[0]);
        int m = Integer.parseInt(hhmm[1]);
        return h * 60 + m;
    }

    // 학점 변경
    private void changeCredit() {
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

    // 과목 삭제
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
            System.out.println("List is empty.");
            return;
        }

        for (int i = 0; i < target.size(); i++) {
            System.out.println("[" + i + "] " + target.get(i).getName());
        }

        System.out.print("Enter index to remove: ");
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

    public int getGoalCredit() {
        return this.goalCredit;
    }
}