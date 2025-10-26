/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author talha
 */
package Model.accesscontrol;

/**
 * GradeCalculator utility class
 * Provides methods for grade calculations and conversions
 */
public class GradeCalculator {
    
    /**
     * Calculate letter grade from percentage score
     * @param percentage Score as a percentage (0-100)
     * @return Letter grade (A, A-, B+, etc.)
     */
    public static String calculateLetterGrade(double percentage) {
        if (percentage >= 93) return "A";
        else if (percentage >= 90) return "A-";
        else if (percentage >= 87) return "B+";
        else if (percentage >= 83) return "B";
        else if (percentage >= 80) return "B-";
        else if (percentage >= 77) return "C+";
        else if (percentage >= 73) return "C";
        else if (percentage >= 70) return "C-";
        else if (percentage >= 67) return "D+";
        else if (percentage >= 60) return "D";
        else return "F";
    }
    
    /**
     * Convert letter grade to GPA on 4.0 scale
     * @param letterGrade Letter grade (A, A-, B+, etc.)
     * @return GPA value (0.0 - 4.0)
     */
    public static double letterGradeToGPA(String letterGrade) {
        switch (letterGrade) {
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D": return 1.0;
            case "F": return 0.0;
            default: return -1.0; // Invalid grade
        }
    }
    
    /**
     * Calculate weighted total from assignment, midterm, and final scores
     * Default weights: Assignments 30%, Midterm 30%, Final 40%
     * @param assignmentScore Assignment score (0-100)
     * @param midtermScore Midterm score (0-100)
     * @param finalScore Final exam score (0-100)
     * @return Weighted total percentage
     */
    public static double calculateWeightedTotal(double assignmentScore, double midtermScore, double finalScore) {
        return (assignmentScore * 0.30) + (midtermScore * 0.30) + (finalScore * 0.40);
    }
    
    /**
     * Calculate weighted total with custom weights
     * @param assignmentScore Assignment score (0-100)
     * @param midtermScore Midterm score (0-100)
     * @param finalScore Final exam score (0-100)
     * @param assignmentWeight Weight for assignments (0.0-1.0)
     * @param midtermWeight Weight for midterm (0.0-1.0)
     * @param finalWeight Weight for final exam (0.0-1.0)
     * @return Weighted total percentage
     */
    public static double calculateWeightedTotal(double assignmentScore, double midtermScore, 
                                                double finalScore, double assignmentWeight, 
                                                double midtermWeight, double finalWeight) {
        // Validate weights sum to 1.0
        if (Math.abs((assignmentWeight + midtermWeight + finalWeight) - 1.0) > 0.001) {
            throw new IllegalArgumentException("Weights must sum to 1.0");
        }
        
        return (assignmentScore * assignmentWeight) + 
               (midtermScore * midtermWeight) + 
               (finalScore * finalWeight);
    }
    
    /**
     * Calculate class average from array of scores
     * @param scores Array of student scores
     * @return Average score
     */
    public static double calculateAverage(double[] scores) {
        if (scores == null || scores.length == 0) {
            return 0.0;
        }
        
        double sum = 0;
        for (double score : scores) {
            sum += score;
        }
        
        return sum / scores.length;
    }
    
    /**
     * Calculate GPA from letter grades and credits
     * @param letterGrades Array of letter grades
     * @param credits Array of credit hours for each course
     * @return Calculated GPA
     */
    public static double calculateGPA(String[] letterGrades, int[] credits) {
        if (letterGrades == null || credits == null || 
            letterGrades.length != credits.length || letterGrades.length == 0) {
            return 0.0;
        }
        
        double totalPoints = 0.0;
        int totalCredits = 0;
        
        for (int i = 0; i < letterGrades.length; i++) {
            double gpa = letterGradeToGPA(letterGrades[i]);
            if (gpa >= 0) { // Valid grade
                totalPoints += gpa * credits[i];
                totalCredits += credits[i];
            }
        }
        
        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }
    
    /**
     * Validate if a score is within valid range (0-100)
     * @param score Score to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidScore(double score) {
        return score >= 0 && score <= 100;
    }
    
    /**
     * Determine academic standing based on GPA
     * @param gpa Current GPA
     * @return Academic standing (Good Standing, Warning, Probation, Suspension)
     */
    public static String determineAcademicStanding(double gpa) {
        if (gpa >= 3.0) {
            return "Good Standing";
        } else if (gpa >= 2.5) {
            return "Academic Warning";
        } else if (gpa >= 2.0) {
            return "Academic Probation";
        } else {
            return "Academic Suspension Risk";
        }
    }
    
    /**
     * Calculate the median score from an array of scores
     * @param scores Array of scores
     * @return Median score
     */
    public static double calculateMedian(double[] scores) {
        if (scores == null || scores.length == 0) {
            return 0.0;
        }
        
        double[] sortedScores = scores.clone();
        java.util.Arrays.sort(sortedScores);
        
        int middle = sortedScores.length / 2;
        
        if (sortedScores.length % 2 == 0) {
            return (sortedScores[middle - 1] + sortedScores[middle]) / 2.0;
        } else {
            return sortedScores[middle];
        }
    }
    
    /**
     * Calculate standard deviation of scores
     * @param scores Array of scores
     * @return Standard deviation
     */
    public static double calculateStandardDeviation(double[] scores) {
        if (scores == null || scores.length == 0) {
            return 0.0;
        }
        
        double mean = calculateAverage(scores);
        double sumSquaredDiff = 0;
        
        for (double score : scores) {
            double diff = score - mean;
            sumSquaredDiff += diff * diff;
        }
        
        return Math.sqrt(sumSquaredDiff / scores.length);
    }
}
