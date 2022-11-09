package com.example.pe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employeeList;

    public EmployeeAdapter(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeeAdapter.EmployeeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.id.setText(employeeList.get(position).getId());
        holder.fullname.setText(employeeList.get(position).getFullName());
        holder.age.setText(String.valueOf(employeeList.get(position).getAge()));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder{
        TextView id,fullname,age;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.emp_item_id);
            fullname = itemView.findViewById(R.id.emp_item_fullname);
            age = itemView.findViewById(R.id.emp_item_age);

        }
    }
}
