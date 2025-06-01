package com.example.allenliang_eosproject;

import java.io.Serializable;
import java.util.List;

public class Student implements Serializable {
    private String id;
    private int age;
    private String gender;
    private int studyHoursPerWeek;
    private String preferredLearningStyle;
    private int onlineCoursesCompleted;
    private int assignmentCompletionRate;
    private int examScore;
    private int attendanceRate;

    public Student(String id, int age, String gender, int studyHoursPerWeek,
                   String preferredLearningStyle, int onlineCoursesCompleted,
                   int assignmentCompletionRate, int examScore, int attendanceRate) {
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.studyHoursPerWeek = studyHoursPerWeek;
        this.preferredLearningStyle = preferredLearningStyle;
        this.onlineCoursesCompleted = onlineCoursesCompleted;
        this.assignmentCompletionRate = assignmentCompletionRate;
        this.examScore = examScore;
        this.attendanceRate = attendanceRate;
    }

    // Getters
    public String getId() { return id; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public int getStudyHoursPerWeek() { return studyHoursPerWeek; }
    public String getPreferredLearningStyle() { return preferredLearningStyle; }
    public int getOnlineCoursesCompleted() { return onlineCoursesCompleted; }
    public int getAssignmentCompletionRate() { return assignmentCompletionRate; }
    public int getExamScore() { return examScore; }
    public int getAttendanceRate() { return attendanceRate; }

    // Calculate Pearson correlation coefficient between studyHoursPerWeek and examScore for a list of students
    public static double calculateCorrelation(List<Student> students) {
        int n = students.size();
        if (n < 2) return 0.0;

        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0;

        for (Student s : students) {
            double x = s.getStudyHoursPerWeek();
            double y = s.getExamScore();
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
            sumY2 += y * y;
        }

        double numerator = n * sumXY - sumX * sumY;
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

        if (denominator == 0) return 0.0;

        return numerator / denominator;
    }
}

