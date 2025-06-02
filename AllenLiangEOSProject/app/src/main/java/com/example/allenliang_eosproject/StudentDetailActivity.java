package com.example.allenliang_eosproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDetailActivity extends AppCompatActivity {

    private TextView tvStudentId, tvAge, tvGender, tvStudyHours, tvAssignmentCompletionRate,
            tvExamScore, tvAttendanceRate, tvCorrelation, tvCorrelationInterpretation;
    private Button btnBack;
    private CSVDataManager dataManager;
    private HashMap<Student, Integer> studentsMap;
    private Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        tvStudentId = findViewById(R.id.tvStudentId);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvStudyHours = findViewById(R.id.tvStudyHours);
        tvAssignmentCompletionRate = findViewById(R.id.tvAssignmentCompletionRate);
        tvExamScore = findViewById(R.id.tvExamScore);
        tvAttendanceRate = findViewById(R.id.tvAttendanceRate);
        tvCorrelation = findViewById(R.id.tvCorrelation);
        btnBack = findViewById(R.id.btnBackToHome);

        dataManager = new CSVDataManager(this);
        studentsMap = dataManager.loadStudents();

        currentStudent = (Student) getIntent().getSerializableExtra("student");
        if (currentStudent != null) {
            displayStudentDetails(currentStudent);
            calculateAndDisplayCorrelation();
        }

        btnBack.setOnClickListener(v -> finish());
    }

    private void displayStudentDetails(Student s) {
        tvStudentId.setText("Student ID: " + s.getId());
        tvAge.setText("Age: " + s.getAge());
        tvGender.setText("Gender: " + s.getGender());
        tvStudyHours.setText("Study Hours/Week: " + s.getStudyHoursPerWeek());
        tvAssignmentCompletionRate.setText("Assignment Completion Rate: " + s.getAssignmentCompletionRate() + "%");
        tvExamScore.setText("Exam Score: " + s.getExamScore());
        tvAttendanceRate.setText("Attendance Rate: " + s.getAttendanceRate() + "%");
    }

    private void calculateAndDisplayCorrelation() {
        List<Student> allStudents = new ArrayList<>(studentsMap.keySet());
        if (allStudents.size() < 2) {
            tvCorrelation.setText("Correlation: Not enough data");
            return;
        }

        double meanStudyHours = allStudents.stream()
                .mapToDouble(Student::getStudyHoursPerWeek)
                .average().orElse(0.0);

        double meanExamScore = allStudents.stream()
                .mapToDouble(Student::getExamScore)
                .average().orElse(0.0);

        double numerator = 0, sumSquaredDiffX = 0, sumSquaredDiffY = 0;

        for (Student student : allStudents) {
            double diffX = student.getStudyHoursPerWeek() - meanStudyHours;
            double diffY = student.getExamScore() - meanExamScore;
            numerator += diffX * diffY;
            sumSquaredDiffX += diffX * diffX;
            sumSquaredDiffY += diffY * diffY;
        }

        double denominator = Math.sqrt(sumSquaredDiffX * sumSquaredDiffY);
        double correlation = denominator != 0 ? numerator / denominator : 0;

        tvCorrelation.setText(String.format("Correlation (Study Hours vs Exam Score): %.3f", correlation));
    }
}


