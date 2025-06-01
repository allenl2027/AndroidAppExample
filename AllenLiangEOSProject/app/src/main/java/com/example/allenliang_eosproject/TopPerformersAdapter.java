package com.example.allenliang_eosproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopPerformersAdapter extends RecyclerView.Adapter<TopPerformersAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Student student);
    }

    private List<Student> students;
    private OnItemClickListener listener;

    public TopPerformersAdapter(List<Student> students, OnItemClickListener listener) {
        this.students = students;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopPerformersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPerformersAdapter.ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.tvRank.setText("#" + (position + 1));
        holder.tvStudentId.setText("ID: " + student.getId());
        holder.tvExamScore.setText("Score: " + student.getExamScore());
        holder.tvLearningStyle.setText(student.getPreferredLearningStyle());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(student));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank, tvStudentId, tvExamScore, tvLearningStyle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.tvRank);
            tvStudentId = itemView.findViewById(R.id.tvStudentId);
            tvExamScore = itemView.findViewById(R.id.tvExamScore);
            tvLearningStyle = itemView.findViewById(R.id.tvLearningStyle);
        }
    }
}

