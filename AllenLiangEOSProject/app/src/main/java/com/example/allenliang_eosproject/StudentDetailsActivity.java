package com.example.allenliang_eosproject;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDetailsActivity extends AppCompatActivity {

    private TextView tvStudentId, tvAge, tvGender, tvStudyHours, tvAssignmentCompletion, tvExamScore, tvCorrelation;
    private HashMap<Student, Integer> studentRankMap;
    private Student currentStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        tvStudentId = findViewById(R.id.tvStudentId);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvStudyHours = findViewById(R.id.tvStudyHours);
        tvAssignmentCompletion = findViewById(R.id.tvAssignmentCompletion);
        tvExamScore = findViewById(R.id.tvExamScore);
        tvCorrelation = findViewById(R.id.tvCorrelation);

        studentRankMap = CsvParser.parseStudents(this);

        String studentId = getIntent().getStringExtra("studentId");
        currentStudent = findStudentById(studentId);

        if (currentStudent != null) {
            displayStudentDetails();
            showCorrelation();
        } else {
            tvStudentId.setText("Student not found");
        }
    }

    private Student findStudentById(String id) {
        for (Student s : studentRankMap.keySet()) {
            if (s.getId().equalsIgnoreCase(id)) return s;
        }
        return null;
    }

    private void displayStudentDetails() {
        tvStudentId.setText("Student ID: " + currentStudent.getId());
        tvAge.setText("Age: " + currentStudent.getAge());
        tvGender.setText("Gender: " + currentStudent.getGender());
        tvStudyHours.setText("Study Hours/Week: " + currentStudent.getStudyHoursPerWeek());
        tvAssignmentCompletion.setText("Assignment Completion Rate: " + currentStudent.getAssignmentCompletionRate() + "%");
        tvExamScore.setText("Exam Score: " + currentStudent.getExamScore());
    }

    private void showCorrelation() {
        // Calculate correlation coefficient between studyHoursPerWeek and examScore for all students
        List<Integer> studyHoursList = new ArrayList<>();
        List<Integer> examScoreList = new ArrayList<>();

        for (Student s : studentRankMap.keySet()) {
            studyHoursList.add(s.getStudyHoursPerWeek());
            examScoreList.add(s.getExamScore());
        }

        double correlation = calculateCorrelation(studyHoursList, examScoreList);
        tvCorrelation.setText(String.format("Correlation between Study Hours and Exam Score: %.3f", correlation));
    }

    /**
     * Calculates Pearson correlation coefficient between two integer lists.
     */
    private double calculateCorrelation(List<Integer> xList, List<Integer> yList) {
        int n = xList.size();
        if (n == 0) return 0;

        double sumX = 0, sumY = 0, sumXY = 0;
        double sumX2 = 0, sumY2 = 0;

        for (int i = 0; i < n; i++) {
            double x = xList.get(i);
            double y = yList.get(i);
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
            sumY2 += y * y;
        }

        double numerator = n * sumXY - sumX * sumY;
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

        if (denominator == 0) return 0;

        return numerator / denominator;
    }
}
