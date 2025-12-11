# ⏰UniTime-Solver⏰
> **UniTime-Solver** is a Java-based automatic timetable generator for university students.
> We help students find the best schedule combinations without the headache of manual arrangement.
  

### Project Overview 
>Every semester, university students suffer from arranging their timetables. Balancing required major courses with desired electives while avoiding time conflicts is a complex constraint satisfaction problem.
**UniTime-Solver** acts as your smart assistant. It doesn't just give you a fixed result; it collaborates with you.
  
### Key Features
>- **Input from User:** Allows users to clearly segment and input mandatory and optional courses.
>- **Backtracking:** Algorithm Uses DFS-based recursion to generate all valid timetable combinations.
>- **User Interface (UI):** Provides a highly readable timetable list in the console environment using ANSI codes, including **pagination** for large result sets.
>- **Editor** Allows the user to edit the result timetables by modifying course lists.
  
### Tech Stack
>Language: Java (JDK 17+)
>Build Tool Gradle 
>Testing JUnit 5 

### Installation Instructions
>1. Check Environment Requirements
>Ensure **Java Development Kit (JDK) 17 or higher** is installed on your system to build and run the project.
>2. Clone Repository
>Use Git to clone the project repository to your local machine   
>3. Build Project  
>Use the Gradle Wrapper to build the project and create the executable **`.jar` file**.
   
### Usage Examples
>1. **Launching the Application**  
>  `java -jar build/libs/UniTime-Solver.jar`
>2. **Course Input Format**  
>   `[Course Name] / [Credit] / [Day] [Start HH:MM] [End HH:MM]`  
>   *(Example: Data Structure / 3 / Mon 12:30 14:00)*
>4. **Timetable Generation and Suggestion**  
>   The system will process the input and display the first batch of 5 timetables.  
>5. **User Selection and Commands**  
>   Interact with the displayed results using one of the following commands:
>   - `next` : Views the next batch of 5 recommended timetables.
>   - `quit` : Terminates the program and saves the 5 currently displayed timetables to an output file.
>   - `edit` : **Enters Edit Mode** to allow detailed modification of course constraints. This provides a menu to **add/remove courses**, and **change the Goal Credit** before regenerating new timetables.

### Examples Images
<img width="205" height="400" alt="image" src="https://github.com/user-attachments/assets/da546467-55c7-4332-afb9-2b5528665ec1" />
<img width="150" height="100" alt="image" src="https://github.com/user-attachments/assets/9df457af-bd02-4346-be4c-7457fdb7debf" />



### Contribution
>Ayeong Kwon ( @AyeongKwon )  
>Mingyeong Kim ( @mingyeonggg)  
>Hyewon Kim ( @Kimhyewon0621 )  
>Jiseop Lee ( @ljseop1030 )  

## License
>This project is licensed under the MIT License.

