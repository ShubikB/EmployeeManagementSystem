package models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class to manage and track employee performance details.
 * It holds attributes like monthly rating and letter status.
 */
public class Performance implements Serializable {
    private static final long serialVersionUID = 1L;

    private String employeeId;
    private String monthlyRating;
    private boolean hasWarningLetter;
    private boolean hasAppreciationLetter;
    private boolean hasBonus;
    private boolean hasFine;
    private LocalDate date;
    private String comments;

    public Performance(String employeeId, String monthlyRating) {
        this.employeeId = employeeId;
        this.monthlyRating = monthlyRating;
        this.hasWarningLetter = false;
        this.hasAppreciationLetter = false;
        this.hasBonus = false;
        this.hasFine = false;
        this.date = LocalDate.now();
        this.comments = "";
    }
    
    // Getters
    public String getEmployeeId() { return employeeId; }
    public String getMonthlyRating() { return monthlyRating; }
    public boolean hasWarningLetter() { return hasWarningLetter; }
    public boolean hasAppreciationLetter() { return hasAppreciationLetter; }
    public boolean hasBonus() { return hasBonus; }
    public boolean hasFine() { return hasFine; }
    public LocalDate getDate() { return date; }
    public String getComments() { return comments; }

    // Setters and action methods
    public void setMonthlyRating(String monthlyRating) {
        this.monthlyRating = monthlyRating;
    }

    public void issueWarningLetter(String reason) {
        this.hasWarningLetter = true;
        addComment("Warning Letter Issued: " + reason);
    }

    public void issueAppreciationLetter(String reason) {
        this.hasAppreciationLetter = true;
        addComment("Appreciation Letter Issued: " + reason);
    }

    public void awardBonus() {
        this.hasBonus = true;
        addComment("Bonus Awarded");
    }

    public void applyFine(String reason) {
        this.hasFine = true;
        addComment("Fine Applied: " + reason);
    }

    private void addComment(String comment) {
        if (!comments.isEmpty()) {
            comments += "; ";
        }
        comments += "[" + LocalDate.now() + "] " + comment;
    }
    
    @Override
    public String toString() {
        return String.format("Performance Record [Date: %s, Rating: %s, Warnings: %b, Appreciations: %b, Bonus: %b, Fine: %b]",
                date, monthlyRating, hasWarningLetter, hasAppreciationLetter, hasBonus, hasFine);
    }
}