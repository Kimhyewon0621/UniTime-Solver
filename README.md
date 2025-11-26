# 📅 UniTime-Solver

> **UniTime-Solver** is a Java-based automatic timetable generator for university students.
> We help students find the best schedule combinations without the headache of manual arrangement.

## 📖 Project Overview
Every semester, university students suffer from arranging their timetables. Balancing required major courses with desired electives while avoiding time conflicts is a complex constraint satisfaction problem

**UniTime-Solver** acts as your smart assistant. It doesn't just give you a fixed result; it collaborates with you.
1. It analyzes your course list and suggests **5 valid schedule options**.
2. If you don't like them, you can **Add** or **Remove** courses instantly via the terminal menu.
3. The system **regenerates** optimal schedules based on your feedback in real-time.

## ✨ Key Features
* **Terminal Input System:** Easy-to-use terminal interface to input course details (Course Name, Course Credit , Time).
* **Conflict Detection:** Automatically checks if two courses overlap in time.
* **Schedule Permutation Algorithm:** Calculates all possible combinations and filters valid schedules.
* **Console Output:** Displays the generated timetables in a readable text format.
* **Robust Error Handling:** Handles edge cases like "All courses conflict" or "Empty input" gracefully.

## 🛠 Tech Stack
* **Language:** Java (JDK 17+)
* **Build Tool:** Maven
* **IDE:** VS Code

## 🚀 Getting Started

### Prerequisites
* Java Development Kit (JDK) 17 or higher installed.
* Git installed.
