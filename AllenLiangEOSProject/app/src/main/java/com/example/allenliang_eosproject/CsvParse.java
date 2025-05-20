package com.example.allenliang_eosproject;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CsvParse {
    public static HashMap<Student, Integer> parseStudents(Context context) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(context.getAssets().open("students.csv")))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 9) continue;
                Student student = new Student(
                        fields[0].trim(),                      // id
                        Integer.parseInt(fields[1].trim()),   // age
                        fields[2].trim(),                      // gender
                        Integer.parseInt(fields[3].trim()),   // studyHoursPerWeek
                        fields[4].trim(),                      // preferredLearningStyle
                        Integer.parseInt(fields[5].trim()),   // onlineCoursesCompleted
                        Integer.parseInt(fields[6].trim()),   // assignmentCompletionRate
                        Integer.parseInt(fields[7].trim()),   // examScore
                        Integer.parseInt(fields[8].trim())    // attendanceRate
                );
                students.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort students by examScore descending
        students.sort((a, b) -> Integer.compare(b.getExamScore(), a.getExamScore()));

        // Map student to rank (1-based)
        HashMap<Student, Integer> studentRankMap = new LinkedHashMap<>();
        int rank = 1;
        for (Student s : students) {
            studentRankMap.put(s, rank++);
        }
        return studentRankMap;
    }
}
