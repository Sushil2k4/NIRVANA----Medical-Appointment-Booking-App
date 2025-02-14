package com.example.nirvana_1;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AppointmentActivity extends AppCompatActivity {

    EditText editName, editAge, editPhoneNumber, editSex, editBloodGroup;
    Spinner specialistSpinner;
    Button btnSubmit;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editSex = findViewById(R.id.editSex);
        editBloodGroup = findViewById(R.id.editBloodGroup);
        specialistSpinner = findViewById(R.id.specialistSpinner);
        btnSubmit = findViewById(R.id.btnSubmit);
        db = FirebaseFirestore.getInstance();

        // Set up spinner with specialists
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.specialists_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialistSpinner.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String age = editAge.getText().toString();
                String phoneNumber = editPhoneNumber.getText().toString();
                String sex = editSex.getText().toString();
                String bloodGroup = editBloodGroup.getText().toString();
                String specialist = specialistSpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age) || TextUtils.isEmpty(phoneNumber) ||
                        TextUtils.isEmpty(sex) || TextUtils.isEmpty(bloodGroup)) {
                    Toast.makeText(AppointmentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Store user details in Firebase
                Map<String, Object> appointment = new HashMap<>();
                appointment.put("name", name);
                appointment.put("age", age);
                appointment.put("phoneNumber", phoneNumber);
                appointment.put("sex", sex);
                appointment.put("bloodGroup", bloodGroup);
                appointment.put("specialist", specialist);

                db.collection("appointments").add(appointment)
                        .addOnSuccessListener(aVoid -> {
                            Intent intent = new Intent(AppointmentActivity.this, SpecialistActivity.class);
                            intent.putExtra("specialist", specialist);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> Toast.makeText(AppointmentActivity.this, "Error storing data", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
