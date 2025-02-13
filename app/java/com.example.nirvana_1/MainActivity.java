package com.example.nirvana_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etRegisterNumber;
    private Button btnProceed;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initializing UI components
        etRegisterNumber = findViewById(R.id.etRegisterNumber);
        btnProceed = findViewById(R.id.btnProceed);

        // Set click listener for proceed button
        btnProceed.setOnClickListener(v -> {
            String registerNumber = etRegisterNumber.getText().toString().trim();

            if (registerNumber.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter your register number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Store the register number in Firebase
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("registerNumber", registerNumber);

            db.collection("users").add(userDetails)
                    .addOnSuccessListener(aVoid -> {
                        Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                        startActivity(intent);
                        finish(); // Close the registration page
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(MainActivity.this, "Error saving registration number", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
