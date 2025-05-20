package com.example.allenliang_eosproject;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashMap;
import java.util.List;

public class TopPerformersAdapter extends RecyclerView.Adapter<TopPerformersAdapter.ViewHolder> {

    private List<Student> students;
    private HashMap<Student, Integer> rankMap;

    public TopPerformersAdapter(List<Student> students, HashMap<Student, Integer> rankMap) {
        this.students = students;
        this.rankMap = rankMap;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student s = students.get(position);
        holder.tv1.setText((rankMap.get(s)) + ". ID: " + s.getId());
        holder.tv2.setText("Exam Score: " + s.getExamScore());
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, StudentDetailsActivity.class);
            intent.putExtra("studentId", s.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(android.R.id.text1);
            tv2 = itemView.findViewById(android.R.id.text2);
        }
    }
}
