package com.example.nirvana_1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SpecialistActivity extends AppCompatActivity {

    Spinner doctorSpinner;
    Button btnSelectDate, btnBookAppointment;
    FirebaseFirestore db;
    Calendar calendar;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);

        doctorSpinner = findViewById(R.id.doctorSpinner);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnBookAppointment = findViewById(R.id.btnBookAppointment);
        db = FirebaseFirestore.getInstance();
        calendar = Calendar.getInstance();

        String specialist = getIntent().getStringExtra("specialist");

        // Handle date selection
        btnSelectDate.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(SpecialistActivity.this, (view, year1, monthOfYear, dayOfMonth) -> {
                selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                btnSelectDate.setText(selectedDate);
            }, year, month, day);

            datePickerDialog.show();
        });

        btnBookAppointment.setOnClickListener(v -> {
            String doctor = doctorSpinner.getSelectedItem().toString();

            if (selectedDate == null) {
                Toast.makeText(SpecialistActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> appointmentDetails = new HashMap<>();
            appointmentDetails.put("specialist", specialist);
            appointmentDetails.put("doctor", doctor);
            appointmentDetails.put("date", selectedDate);

            db.collection("bookings").add(appointmentDetails)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(SpecialistActivity.this, "Appointment booked successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SpecialistActivity.this, MainActivity.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(SpecialistActivity.this, "Error booking appointment", Toast.LENGTH_SHORT).show());
        });
    }
}
