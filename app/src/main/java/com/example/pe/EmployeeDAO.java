package com.example.pe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    //Trong day se co tất các hàm CRUD
    public static List<Employee> getAll(Context context){
        List<Employee> employeeList = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cs = db.rawQuery("Select * from Employee",null);
        cs.moveToFirst();
        while(!cs.isAfterLast()){
            employeeList.add(
                    new Employee(cs.getString(0),
                            cs.getString(1),
                            cs.getInt(2)
                    ));
            cs.moveToNext();
        }
        db.close();
        dbHelper.close();
        return employeeList;
    }

    public static boolean delete(Context context, String idEmp) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        long row = db.delete("Employee",
                "ID=?", new String[]{idEmp});
        dbHelper.close();
        db.close();
        return row > 0;
    }

    public static Employee getEmployeeById(Context context, String id) {
        Employee employee = null;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Employee where ID =" + "'" + id + "'", null);

        if (cursor != null && cursor.moveToFirst()) {
            employee = new Employee();
            employee.setId(cursor.getString(0));
            employee.setFullName(cursor.getString(1));
            employee.setAge(cursor.getInt(2));
        }
        db.close();
        dbHelper.close();
        return employee;

    }
    public static boolean insert(Context context, Employee employee) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        //check xem employee id có tạo chưa
        Employee employeeExit = EmployeeDAO.getEmployeeById(context, employee.getId());
        if (employeeExit != null) {
            return false;
        } else {
            contentValues.put("ID", employee.getId());
            contentValues.put("FULLNAME", employee.getFullName());
            contentValues.put("AGE", employee.getAge());
            long row = db.insert("Employee", null, contentValues);
            db.close();
            dbHelper.close();
            return row > 0;
        }
    }

    public static boolean update(Context context, Employee employee) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", employee.getId());
        contentValues.put("FULLNAME", employee.getFullName());
        contentValues.put("AGE", employee.getAge());
        long row = db.update("Employee",
                contentValues,
                "ID=?", new String[]{
                        employee.getId()});
        dbHelper.close();
        db.close();
        return row > 0;
    }

}
