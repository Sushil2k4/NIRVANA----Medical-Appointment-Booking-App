package com.example.nirvana_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText editRegisterNumber;
    Button btnProceed;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editRegisterNumber = findViewById(R.id.editRegisterNumber);
        btnProceed = findViewById(R.id.btnProceed);
        db = FirebaseFirestore.getInstance();

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registerNumber = editRegisterNumber.getText().toString();
                if (TextUtils.isEmpty(registerNumber)) {
                    Toast.makeText(RegisterActivity.this, "Enter your register number", Toast.LENGTH_SHORT).show();
                } else {
                    // Store the register number in Firebase
                    Map<String, Object> user = new HashMap<>();
                    user.put("registerNumber", registerNumber);

                    db.collection("users").document(registerNumber).set(user)
                            .addOnSuccessListener(aVoid -> {
                                // Proceed to next activity
                                Intent intent = new Intent(RegisterActivity.this, OptionsActivity.class);
                                startActivity(intent);
                            })
                            .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Error storing data", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
