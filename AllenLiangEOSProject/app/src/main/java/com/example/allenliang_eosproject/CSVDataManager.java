package com.example.allenliang_eosproject;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CSVDataManager {
    private Context context;

    public CSVDataManager(Context context) {
        this.context = context;
    }

    // Load students from CSV and return HashMap<Student, Rank>
    public HashMap<Student, Integer> loadStudents() {
        HashMap<Student, Integer> studentsMap = new HashMap<>();
        List<Student> studentsList = new ArrayList<>();

        AssetManager assets = context.getAssets();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        CSVReader csvReader = null;

        try {
            inputStream = assets.open("student_performance_large_dataset.csv"); // Use your actual CSV file name

            inputStreamReader = new InputStreamReader(inputStream);
            csvReader = new CSVReader(inputStreamReader);

            String[] nextRecord;
            boolean isHeader = true;

            // Read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip header row
                    continue;
                }
                if (nextRecord.length < 9) continue;

                Student student = new Student(
                        nextRecord[0],
                        Integer.parseInt(nextRecord[1]),
                        nextRecord[2],
                        Integer.parseInt(nextRecord[3]),
                        nextRecord[4],
                        Integer.parseInt(nextRecord[5]),
                        Integer.parseInt(nextRecord[7]),
                        Integer.parseInt(nextRecord[8]),
                        Integer.parseInt(nextRecord[9])
                );
                studentsList.add(student);
            }

            // Sort by examScore descending
            Collections.sort(studentsList, new Comparator<Student>() {
                @Override
                public int compare(Student s1, Student s2) {
                    return Integer.compare(s2.getExamScore(), s1.getExamScore());
                }
            });

            // Assign rank based on examScore
            for (int i = 0; i < studentsList.size(); i++) {
                studentsMap.put(studentsList.get(i), i + 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (csvReader != null) csvReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return studentsMap;
    }


    // Get top N performers
    public List<Student> getTopPerformers(HashMap<Student, Integer> studentsMap, int count) {
        List<Student> sortedStudents = new ArrayList<>(studentsMap.keySet());
        Collections.sort(sortedStudents, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Float.compare(s2.getExamScore(), s1.getExamScore());
            }
        });
        return sortedStudents.subList(0, Math.min(count, sortedStudents.size()));
    }

    // Search student by ID
    public Student searchStudentById(HashMap<Student, Integer> studentsMap, String id) {
        for (Student s : studentsMap.keySet()) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // Calculate average exam scores by learning style
    public HashMap<String, Float> calculateAverageScoresByLearningStyle(HashMap<Student, Integer> studentsMap) {
        HashMap<String, List<Integer>> groupedScores = new HashMap<>();
        for (Student s : studentsMap.keySet()) {
            String style = s.getPreferredLearningStyle();
            if (!groupedScores.containsKey(style)) {
                groupedScores.put(style, new ArrayList<Integer>());
            }
            groupedScores.get(style).add(s.getExamScore());
        }

        HashMap<String, Float> averages = new HashMap<>();
        for (String style : groupedScores.keySet()) {
            List<Integer> scores = groupedScores.get(style);
            float avg = 0f;
            if (!scores.isEmpty()) {
                int sum = 0;
                for (int score : scores) sum += score;
                avg = (float) sum / scores.size();
            }
            averages.put(style, avg);
        }
        return averages;
    }
}
