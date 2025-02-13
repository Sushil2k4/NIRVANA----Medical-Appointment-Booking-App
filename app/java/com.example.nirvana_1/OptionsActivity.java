package com.example.nirvana_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button btnBookAppointment = findViewById(R.id.btnBookAppointment);
        Button btnHealthCheck = findViewById(R.id.btnHealthCheck);
        Button btnConsultOnline = findViewById(R.id.btnConsultOnline);
        Button btnBuyMedicine = findViewById(R.id.btnBuyMedicine);
        Button btnViewHealthRecord = findViewById(R.id.btnViewHealthRecord);

        btnBookAppointment.setOnClickListener(v -> startActivity(new Intent(OptionsActivity.this, AppointmentActivity.class)));
        // Add similar listeners for other buttons
    }
}
