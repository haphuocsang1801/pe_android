package com.example.pe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv_employee;
    List<Employee> employeeList;
    ArrayAdapter adapter;
    Dialog dialog_detial,dialog_insert,dialog_update;

    //dung de count double click
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //khoi tạo list view
        lv_employee = findViewById(R.id.listview_employee);
        employeeList = EmployeeDAO.getAll(this);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, employeeList);

        lv_employee.setAdapter(adapter);

        //set sự kiên long click
        lv_employee.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee = employeeList.get(position);
//                showDialogDetail(employee);

                showDialogUpdate(employee);

                return false;
            }
        });

//        lv_employee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Employee employee = employeeList.get(position);
//                showDialogDetail(employee);
//            }
//        });
        //double click
        lv_employee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                count++;
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if (count == 1) {
                    } else if (count == 2) {
                        Employee employee = employeeList.get(position);
                        showDialogDetail(employee);
                    }
                    count = 0;
                }, 500);
            }
        });


        Button btn_insert_main = findViewById(R.id.btn_insert_main);

        btn_insert_main.setOnClickListener(t->{
            showDialogInsert();
        });
    }
    //update
    private void showDialogUpdate(Employee employee){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_update, null);

        builder.setView(view);
        dialog_update = builder.create();

        dialog_update.show();
        updateEmployee(view,employee);
    }
    private void updateEmployee(View view, Employee employee){
//query tất cả input trong view vd:view -> dialog_add
        EditText update_id = view.findViewById(R.id.id_emp_update);
        EditText update_fullname = view.findViewById(R.id.fullname_emp_update);
        EditText update_age = view.findViewById(R.id.age_emp_update);

        //set text cho input
        update_id.setText(employee.getId());
        update_fullname.setText(employee.getFullName());
        update_age.setText(String.valueOf(employee.getAge()));


        Button btn_update = view.findViewById(R.id.btn_update);

        //set event click vào button add
        btn_update.setOnClickListener(t -> {
            //check các input có empty không
            if (update_id.getText().toString().isEmpty() || update_fullname.getText().toString().isEmpty() || update_age.getText().toString().isEmpty()) {
                Toast.makeText(this, "Field can not be empty", Toast.LENGTH_SHORT).show();
            } else {
                //gọi hàm insert trong EmplopyeeDao
                boolean check = EmployeeDAO.update(this,
                        new Employee(update_id.getText().toString()
                                , update_fullname.getText().toString()
                                , Integer.parseInt(update_age.getText().toString())
                        ));
                if (check) {
                    //update lại ngoài màn hình hiển thị
                    refreshListView();
                    Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Update fail", Toast.LENGTH_SHORT).show();
                }
            }
            //tắt dialog add employee
            dialog_update.dismiss();
        });

    }


    //insert
    private void showDialogInsert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_insert, null);

        builder.setView(view);
        dialog_insert = builder.create();

        dialog_insert.show();
        createEmployee(view);
    }
    private void createEmployee(View view) {
        //query tất cả input trong view vd:view -> dialog_add
        EditText add_id = view.findViewById(R.id.id_emp_insert);
        EditText add_fullname = view.findViewById(R.id.fullname_emp_insert);
        EditText add_age = view.findViewById(R.id.age_emp_insert);
        Button btn_add = view.findViewById(R.id.btn_insert);

        //set event click vào button add
        btn_add.setOnClickListener(t -> {
            //check các input có empty không
            if (add_id.getText().toString().isEmpty() || add_fullname.getText().toString().isEmpty() || add_age.getText().toString().isEmpty()) {
                Toast.makeText(this, "Field can not be empty", Toast.LENGTH_SHORT).show();
            } else {
                //gọi hàm insert trong EmplopyeeDao
                boolean check = EmployeeDAO.insert(this,
                        new Employee(add_id.getText().toString()
                        , add_fullname.getText().toString()
                        , Integer.parseInt(add_age.getText().toString())
                ));
                if (check) {
                    //update lại ngoài màn hình hiển thị
                    refreshListView();
                    Toast.makeText(this, "insert successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Employee already exists", Toast.LENGTH_SHORT).show();
                }
            }
            //tắt dialog add employee
            dialog_insert.dismiss();
        });
    }

    //detail
    private void showDialogDetail(Employee employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_detail, null);

        builder.setView(view);
        dialog_detial = builder.create();

        dialog_detial.show();
        setValueToDetail(employee,view);
        deleteDetail(view,employee.getId());
    }
    private void setValueToDetail(Employee employee,View view){
        TextView id = view.findViewById(R.id.id_emp_detail);
        TextView fullname = view.findViewById(R.id.fullname_emp_detail);
        TextView age = view.findViewById(R.id.age_emp_detail);

        id.setText(employee.getId());
        fullname.setText(employee.getFullName());
        age.setText(String.valueOf(employee.getAge()));
    }

    //delete
    private void deleteDetail(View view, String id){
        Button btn_delete_detail = view.findViewById(R.id.btn_delete_detail);
        btn_delete_detail.setOnClickListener(t->{
            //confirm dialog when you wana delete
            new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("Do you want to Delete")

                    .setPositiveButton("Delete", (dialog, whichButton) -> {
                        boolean check = EmployeeDAO.delete(view.getContext(),id);
                        if(check){
                            dialog_detial.dismiss();
                            refreshListView();
                            Toast.makeText(this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(this, "Delete faild", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();

        });
    }

    private void refreshListView(){
        adapter.clear();
        employeeList.clear();
        employeeList = EmployeeDAO.getAll(this);
        adapter.addAll(employeeList);

        adapter.notifyDataSetChanged();
    }

}