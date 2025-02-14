package com.example.nirvana_1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SpecialistActivity extends AppCompatActivity {

    private Spinner doctorSpinner;
    private Button btnSelectDate, btnBookAppointment;
    private FirebaseFirestore db;
    private Calendar calendar;
    private String selectedDate;
    private String specialist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);

        initializeUI();

        specialist = getIntent().getStringExtra("specialist");

        btnSelectDate.setOnClickListener(v -> showDatePicker());

        btnBookAppointment.setOnClickListener(v -> bookAppointment());
    }

    private void initializeUI() {
        doctorSpinner = findViewById(R.id.doctorSpinner);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnBookAppointment = findViewById(R.id.btnBookAppointment);
        db = FirebaseFirestore.getInstance();
        calendar = Calendar.getInstance();
        
        btnBookAppointment.setEnabled(false); // Disable booking button until date is selected
    }

    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
            SpecialistActivity.this, 
            (view, selectedYear, selectedMonth, selectedDay) -> {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

                if (selectedCalendar.before(Calendar.getInstance())) {
                    Toast.makeText(this, "Please select a future date", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                selectedDate = sdf.format(selectedCalendar.getTime());
                btnSelectDate.setText(selectedDate);
                btnBookAppointment.setEnabled(true); // Enable booking button after selecting a date
            }, 
            year, month, day
        );

        datePickerDialog.show();
    }

    private void bookAppointment() {
        String doctor = doctorSpinner.getSelectedItem().toString();

        if (selectedDate == null) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> appointmentDetails = new HashMap<>();
        appointmentDetails.put("specialist", specialist);
        appointmentDetails.put("doctor", doctor);
        appointmentDetails.put("date", selectedDate);

        db.collection("bookings").add(appointmentDetails)
            .addOnSuccessListener(documentReference -> {
                Toast.makeText(this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
                navigateToMain();
            })
            .addOnFailureListener(e -> {
                Toast.makeText(this, "Error booking appointment: " + e.getMessage(), Toast.LENGTH_LONG).show();
            });
    }

    private void navigateToMain() {
        Intent intent = new Intent(SpecialistActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
