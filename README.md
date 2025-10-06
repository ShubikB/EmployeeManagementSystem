# Employee Management System (EMS)

This project is a text-based Employee Management System created for the ICT711 Programming and Algorithms course at King's Own Institute. It is a group project designed to demonstrate core Java programming and object-oriented principles.

## Project Overview

The Employee Management System (EMS) is a command-line application that allows a company administrator to manage employee records. The system handles operations such as adding, viewing, updating, and deleting employees, as well as managing their monthly performance and salary details. All data is persisted by reading from and writing to text files.

## Features

The system implements the following functionalities:

- **Load Data:** Loads initial employee records from a `.txt` or `.csv` file.
- **Add Employees:** Adds new employee records to the system and saves the updated list to a new file.
- **View/Query Employees:** Searches for and displays employee information based on ID, name, or performance rating.
- **Update Employees:** Modifies existing employee information.
- **Delete Employees:** Removes an employee record from the system.
- **Manage Performance:** Allows an admin to manage monthly performance details, such as awarding bonuses, applying fines, or issuing warning/appreciation letters.
- **Interactive UI:** A clear and intuitive text-based user interface for easy navigation and operation.

## Core Concepts Demonstrated

This project applies several key computer science and programming concepts as required by the assessment:

- **Object-Oriented Programming (OOP):**
  - **Inheritance:** A base `Employee` class is extended by more specialized classes like `Manager`, `Intern`, and `Regular` to promote code reusability.
  - **Polymorphism:** The system treats different employee types uniformly, yet they exhibit different behaviors, especially in methods like `calculateSalary()`.
  - **Abstraction:** Abstract classes and methods are used to hide complex implementation details and define a common interface for all employee types.
  - **Encapsulation:** Data is protected within classes, and access is controlled through public methods.
- **Java Collections Framework:**
  - Uses collections like `ArrayList` or `LinkedList` to efficiently store and manage the list of employee objects.
- **File Handling:**
  - Reads employee data from a `.csv` or `.txt` file to populate the system on startup.
  - Writes the current state of employee data back to a file after any modifications.
- **Exception Handling:**
  - Implements `try-catch` blocks to gracefully handle potential runtime errors, such as `FileNotFoundException` or invalid user input, preventing the application from crashing.

## Technologies Used

- **Java**

## Setup and Usage

1.  **Prerequisites:**

    - Java Development Kit (JDK) 8 or higher must be installed.

2.  **Clone the Repository:**

    ```bash
    git clone https://github.com/ShubikB/EmployeeManagementSystem.git
    ```

3.  **Download JUnit (for testing):**

    - Download `junit-platform-console-standalone-1.10.2.jar` from [this link](https://search.maven.org/remotecontent?filepath=org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar).
    - Create a `lib` folder in the project's root directory.
    - Place the downloaded `.jar` file inside the `lib` folder.

4.  **Compile the Project:**
    From the root directory of the project, run:

    ```bash
    javac -d bin src/models/*.java src/service/*.java src/ui/*.java src/util/*.java src/Main.java
    ```

    This command compiles all Java source files and places the compiled classes in the `bin` directory.

5.  **Run the Application:**
    After compilation, run:

    ```bash
    java -cp bin Main
    ```

    This command runs the main application class, which will prompt you to choose between the Text-Based Interface (TBI) and the Graphical User Interface (GUI).

6.  **Compile and Run Tests:**
    To ensure the application's logic is working correctly, you can compile and run the unit tests.

    **a. Compile Tests:**

    ```bash
    javac -d bin -cp "bin;lib/junit-platform-console-standalone-1.10.2.jar" src/models/*.java src/util/*.java src/service/*.java test/service/*.java
    ```

    **b. Run Tests:**

    ```bash
    java -jar lib/junit-platform-console-standalone-1.10.2.jar --class-path bin --scan-class-path
    ```

    You should see output indicating that all tests have passed successfully.

## Contributors

- Shubik Bhatt
- Binit Shrestha
- Saurabh Shah
- Prashid Bhusal
- Saurav Dangol
