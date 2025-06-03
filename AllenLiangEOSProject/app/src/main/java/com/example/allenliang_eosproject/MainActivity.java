package com.example.allenliang_eosproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText etSearchStudent;
    private Button btnSearchStudent, btnShowCharts;
    private CSVDataManager dataManager;
    private HashMap<Student, Integer> studentsMap;
    private TopPerformersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewTopPerformers);
        etSearchStudent = findViewById(R.id.etSearchStudent);
        btnSearchStudent = findViewById(R.id.btnSearchStudent);
        btnShowCharts = findViewById(R.id.btnShowCharts);

        dataManager = new CSVDataManager(this);
        studentsMap = dataManager.loadStudents();

        setupRecyclerView();
        setupSearchButton();
        setupChartButton();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Student> topStudents = dataManager.getTopPerformers(studentsMap, 10);
        adapter = new TopPerformersAdapter(topStudents, new TopPerformersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Student student) {
                openStudentDetails(student);
            }
        });
        recyclerView.setAdapter(adapter);
    }
    //Line 55-80 was written by Perplexity AI
    private void setupSearchButton() {
        btnSearchStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etSearchStudent.getText().toString().trim();
                if (id.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a Student ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                Student foundStudent = null;
                for (Student student : studentsMap.keySet()) {
                    if (student.getId().equalsIgnoreCase(id)) {
                        foundStudent = student;
                        break;
                    }
                }

                if (foundStudent != null) {
                    openStudentDetails(foundStudent);
                } else {
                    Toast.makeText(MainActivity.this, "Student not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupChartButton() {
        btnShowCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openStudentDetails(Student student) {
        Intent intent = new Intent(MainActivity.this, StudentDetailActivity.class);
        intent.putExtra("student", student);
        startActivity(intent);
    }
}