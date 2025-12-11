package com.unitime.feature;

public class Editor {

    private Editor (String input) {
        input = input.toLowerCase();
        if (input.equals("quit")) quit();
        else if (input.equals("edit")) editList();
        else if (input.equals("next")) viewNext();
        else System.out.println("[Error] Invalid input.");
    }

    public void viewNext() {
        
    }

    public void editList() {

    }

    public void quit() {
        
    }

}
