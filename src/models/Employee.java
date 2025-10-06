package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class for all employee types.
 * It encapsulates common attributes and defines an abstract method for calculating salary,
 * demonstrating the concepts of abstraction and inheritance.
 */
public abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String id;
    private String department;
    private double baseSalary;
    private String performanceRating;
    private List<Performance> performanceHistory;
    private double bonus;
    private double fine;

    public Employee(String id, String name, String department, double baseSalary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
        this.performanceRating = "N/A";
        this.performanceHistory = new ArrayList<>();
        this.bonus = 0.0;
        this.fine = 0.0;
    }

    // Getters
    public String getName() { return name; }
    public String getId() { return id; }
    public String getDepartment() { return department; }
    public double getBaseSalary() { return baseSalary; }
    public String getPerformanceRating() { return performanceRating; }
    public List<Performance> getPerformanceHistory() { 
        return new ArrayList<>(performanceHistory); 
    }
    public double getBonus() { return bonus; }
    public double getFine() { return fine; }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setBaseSalary(double baseSalary) {
        if (baseSalary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.baseSalary = baseSalary;
    }

    public void setPerformanceRating(String performanceRating) {
        this.performanceRating = performanceRating;
    }

    public void addPerformanceRecord(Performance performance) {
        performanceHistory.add(performance);
    }

    public void addBonus(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Bonus cannot be negative");
        }
        this.bonus += bonus;
    }

    public void addFine(double fine) {
        if (fine < 0) {
            throw new IllegalArgumentException("Fine cannot be negative");
        }
        this.fine += fine;
    }

    /**
     * Abstract method to calculate the yearly salary.
     * This method must be implemented by all subclasses,
     * demonstrating polymorphism.
     * @return The calculated yearly salary including bonus and fine.
     */
    public abstract double calculateSalary();

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Department: %s, Total Salary: $%.2f, Performance Rating: %s",
                id, name, department, calculateSalary(), performanceRating);
    }
}