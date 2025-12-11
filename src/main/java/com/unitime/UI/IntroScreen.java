package com.unitime.UI;

public class IntroScreen {
    //set color for introscreen
    private static final String ANSI_RESET = "\u001b[0m";
    private static final String ANSI_BOLD = "\u001b[1m";
    private static final String ANSI_GREEN = "\u001b[38;5;46m";
    private static final String ANSI_YELLOW = "\u001b[38;5;226m";
    private static final String[] ANSI_COLORS = {
        "\u001b[38;5;196m",
        "\u001b[38;5;208m",
        "\u001b[38;5;220m", 
        "\u001b[38;5;46m", 
        "\u001b[38;5;51m",  
        "\u001b[38;5;93m"   
    };
    private static final int NUM_COLORS = ANSI_COLORS.length;
    private static final int WIDTH = 80;

    public static void start() {
        printBorderedBox();

        System.out.println("=".repeat(WIDTH + 4));
        System.out.println(ANSI_YELLOW + "                      Press [ENTER] to Start UniTime_Solver." + ANSI_RESET);
        System.out.println("=".repeat(WIDTH + 4));

        //waiting user's enter
    }

    private static void printBorderedBox() {
        String h_border = "+-" + "-".repeat(WIDTH) + "-+";
        String emptyLine = "| " + " ".repeat(WIDTH) + " |";

        System.out.println(h_border);

        String title = " ♬  UniTime-Solver";
        int currentLength = title.length();
        int padding = WIDTH - currentLength; 

        System.out.println("| " + ANSI_BOLD + title + ANSI_RESET + " ".repeat(padding) + " |");
        System.out.println(h_border);

        String status = " Status: [ *READY to START* ]"; 
        int statusPadding = WIDTH - status.length();


        System.out.println("| " + ANSI_GREEN + status + ANSI_RESET + " ".repeat(statusPadding) + " |");
        System.out.println(h_border);

        String[] logo = {
            "  _   _       _ _____ _                     ____        _                   ",

            " | | | |_ __ (_)_  _ (_)_ __ ___   ___     / ___|  ___ | |_   ______ __ ",

            " | | | | '_ \\| | | | | | '_ ` _ \\ / _ \\____\\___ \\ / _ \\| \\ \\ / /_ \\ '__|",

            " | |_| | | | | | | | | | | | | | |  __/_____|__) | (_) | |\\ V / __/ |  ",

            " \\____/|_| |_|_| |_| |_|_| |_| |_|\\___|    |____/ \\___/|_| \\_/\\___|_|  "
        };

        final int MAX_LOGO_WIDTH = 72;
        
        System.out.println(emptyLine);

        for (int i = 0; i < logo.length; i++) {
            String line = logo[i];
            
            int leftPadding = (WIDTH - MAX_LOGO_WIDTH) / 2;
            
            System.out.print("| " + " ".repeat(leftPadding));

            int printableWidth = Math.min(line.length(), MAX_LOGO_WIDTH);
            
            for (int j = 0; j < printableWidth; j++) {
                char c = line.charAt(j);
                
                int colorIndex = (i + j) % NUM_COLORS; 
                
                
                System.out.print(ANSI_COLORS[colorIndex] + c + ANSI_RESET);
            }

            int rightPadding = WIDTH - leftPadding - printableWidth;
            System.out.println(" ".repeat(rightPadding) + " |");
        }

        System.out.println(emptyLine);
        System.out.println(h_border);
    }
}