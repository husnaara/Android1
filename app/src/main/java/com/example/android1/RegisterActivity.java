
package com.example.android1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private Spinner deptSpinner;
    private String dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        EditText etUsername = findViewById(R.id.et_register_username);
        EditText etEmail = findViewById(R.id.et_register_email);
        EditText etPassword = findViewById(R.id.et_register_password);
        EditText etConfirmPassword = findViewById(R.id.et_register_confirm_password);
        EditText etMobile = findViewById(R.id.et_register_mobile);
        EditText etAge = findViewById(R.id.et_register_age);
        Button btnRegister = findViewById(R.id.btn_register);
        Button btnLogin = findViewById(R.id.btn_login);
        deptSpinner = findViewById(R.id.spinner);

        // Set up department spinner
        String[] items = new String[]{"Select Department", "CSE", "EEE", "ARCH", "CE", "BuA", "ENG", "LAW", "IS", "BNG", "THM", "PH"};
        deptSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items));

        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept = deptSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set up register button click listener
        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String mobile = etMobile.getText().toString().trim();
            String age = etAge.getText().toString().trim();

            if (validateInputs(username, email, password, confirmPassword, mobile, age, dept)) {
                // Insert user into DB
                DatabaseHelper dbHelper = new DatabaseHelper(RegisterActivity.this);
                boolean isInserted = dbHelper.insertUser(username, email, password, mobile, Integer.parseInt(age));

                if (isInserted) {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up login button click listener
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateInputs(String username, String email, String password, String confirmPassword, String mobile, String age, String dept) {
        // Username validation
        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Email validation
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Password validation
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Confirm password validation
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Mobile number validation
        if (mobile.isEmpty() || !Patterns.PHONE.matcher(mobile).matches()) {
            Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Age validation
        if (age.isEmpty()) {
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {
                int ageInt = Integer.parseInt(age);
                if (ageInt < 0 || ageInt > 100) {
                    Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number for age", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        // Department validation
        if (dept.equals("Select Department")) {
            Toast.makeText(this, "Please select a department", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
