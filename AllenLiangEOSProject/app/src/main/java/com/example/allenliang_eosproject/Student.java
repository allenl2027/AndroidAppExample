package com.example.allenliang_eosproject;
public class Student {
    private String id;
    private int age;
    private String gender;
    private int studyHoursPerWeek;
    private String preferredLearningStyle;
    private int onlineCoursesCompleted;
    private int assignmentCompletionRate;
    private int examScore;
    private int attendanceRate;
    public Student(String id, int age,String gender, int studyHoursPerWeek, String preferredLearningStyle, int onlineCoursesCompleted, int assignmentCompletionRate, int examScore, int attendanceRate) {
        this.id=id;
        this.age=age;
        this.gender=gender;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return id.equals(s.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
