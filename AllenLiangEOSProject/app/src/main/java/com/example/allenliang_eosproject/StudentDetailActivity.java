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
        tvCorrelationInterpretation = findViewById(R.id.tvCorrelationInterpretation);
        btnBack = findViewById(R.id.btnBackToHome);

        dataManager = new CSVDataManager(this);
        studentsMap = dataManager.loadStudents();

        Student student = (Student) getIntent().getSerializableExtra("student");
        if (student != null) {
            displayStudentDetails(student);
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
        double corr = Student.calculateCorrelation(allStudents);
        tvCorrelation.setText(String.format("Correlation (Study Hours vs Exam Score): %.2f", corr));

        String interpretation;
        if (corr > 0.7) interpretation = "Strong positive correlation";
        else if (corr > 0.3) interpretation = "Moderate positive correlation";
        else if (corr > -0.3) interpretation = "Weak or no correlation";
        else if (corr > -0.7) interpretation = "Moderate negative correlation";
        else interpretation = "Strong negative correlation";

        tvCorrelationInterpretation.setText(interpretation);
    }
}

