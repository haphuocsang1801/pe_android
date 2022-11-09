package com.example.pe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class UpdateFragment extends Fragment {

    private static final String ARG_PARAM1 = "id";

    private String idEmployee;
    View view;
    RecyclerView rcv_emp;

    //callback khi mà có sự kiện update
    OnUpdateListener callback;

    public interface OnUpdateListener{
        void onUpdateClicked();
        void closeFragment();
    }
    public void setUpdateListener(OnUpdateListener callback){
        this.callback = callback;
    }
//    ---------------------------


    public UpdateFragment() {
    }

    //chỗ để truyền giá trị vào fragment
    public static UpdateFragment newInstance(String id) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    //cho để set giá trị
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idEmployee = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update, container, false);
        init();
        return view;
    }
    private void init(){
        setRcvEmployee();
        closeFragment();
        EditText id = view.findViewById(R.id.id_emp_update_fragment);
        EditText fullName = view.findViewById(R.id.fullname_emp_update_fragment);
        EditText age = view.findViewById(R.id.age_emp_update_fragment);

        //get giá trị của nhân viên hiện tại vừa vào id
        Employee employee = EmployeeDAO.getEmployeeById(view.getContext(),idEmployee);

        id.setText(employee.getId());
        fullName.setText(employee.getFullName());
        age.setText(String.valueOf(employee.getAge()));

        //bắt sự kiện click button update
        Button btn_update_fragment = view.findViewById(R.id.btn_update_fragment);
        btn_update_fragment.setOnClickListener(t -> {
            //check các input có empty không
            if (id.getText().toString().isEmpty() || fullName.getText().toString().isEmpty() || age.getText().toString().isEmpty()) {
                Toast.makeText(view.getContext(), "Field can not be empty", Toast.LENGTH_SHORT).show();
            } else {
                //gọi hàm insert trong EmplopyeeDao
                boolean check = EmployeeDAO.update(view.getContext(),
                        new Employee(id.getText().toString()
                                , fullName.getText().toString()
                                , Integer.parseInt(age.getText().toString())
                        ));
                if (check) {
                    //update lại ngoài màn hình hiển thị
                    callback.onUpdateClicked();
                    Toast.makeText(view.getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(view.getContext(), "Update fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setRcvEmployee() {
        rcv_emp = view.findViewById(R.id.rcv_emp);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),1);
        rcv_emp.setLayoutManager(gridLayoutManager);

        List<Employee> employeeList = EmployeeDAO.getAll(view.getContext());

        EmployeeAdapter adapter = new EmployeeAdapter(employeeList);

        rcv_emp.setAdapter(adapter);
    }

    private void closeFragment() {
        Button btn_update_close_fragment = view.findViewById(R.id.btn_update_close_fragment);
        btn_update_close_fragment.setOnClickListener(t->{
            callback.closeFragment();
        });
    }

}