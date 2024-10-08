package com.example.ambucare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        final EditText editTextFullName  = findViewById(R.id.editTextFullName);
        final EditText editTextLastName  = findViewById(R.id.editTextLastName); // Username or email
        final EditText editTextPhoneNumber  = findViewById(R.id.editTextPhoneNumber);
        final EditText editTextLicenseNumber  = findViewById(R.id.editTextLicenseNumber);
        final EditText editTextLicenseExpiryDate  = findViewById(R.id.editTextLicenseExpiryDate);
        final EditText VehicleRegistrationNumber  = findViewById(R.id.editTextVehicleRegNumber);
        final EditText VehicleType  = findViewById(R.id.editTextVehicleType);
        final EditText editTextNewField = findViewById(R.id.editTextNewField); // Password field
        final Button buttonRegister = findViewById(R.id.buttonRegister);
        final TextView loginnow = findViewById(R.id.loginnow);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String editTextFullNameTxt = editTextFullName.getText().toString();
                final String editTextLastNameTxt = editTextLastName.getText().toString(); // Should be unique (username or email)
                final String editTextPhoneNumberTxt = editTextPhoneNumber.getText().toString();
                final String editTextLicenseNumberTxt = editTextLicenseNumber.getText().toString();
                final String editTextLicenseExpiryDatetxt = editTextLicenseExpiryDate.getText().toString();
                final String VehicleRegistrationNumbertxt = VehicleRegistrationNumber.getText().toString();
                final String VehicleTypeTxt = VehicleType.getText().toString();
                final String editTextNewFieldTxt = editTextNewField.getText().toString(); // Password

                if (editTextFullNameTxt.isEmpty() || editTextLastNameTxt.isEmpty() || editTextPhoneNumberTxt.isEmpty() || editTextLicenseNumberTxt.isEmpty() || editTextLicenseExpiryDatetxt.isEmpty() || VehicleRegistrationNumbertxt.isEmpty() || VehicleTypeTxt.isEmpty() || editTextNewFieldTxt.isEmpty()) {
                    Toast.makeText(register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child(editTextLastNameTxt).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(register.this, "User already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                // Storing user details in the database
                                databaseReference.child(editTextLastNameTxt).child("fullName").setValue(editTextFullNameTxt);
                                databaseReference.child(editTextLastNameTxt).child("phoneNumber").setValue(editTextPhoneNumberTxt);
                                databaseReference.child(editTextLastNameTxt).child("licenseNumber").setValue(editTextLicenseNumberTxt);
                                databaseReference.child(editTextLastNameTxt).child("licenseExpiryDate").setValue(editTextLicenseExpiryDatetxt);
                                databaseReference.child(editTextLastNameTxt).child("vehicleRegistrationNumber").setValue(VehicleRegistrationNumbertxt);
                                databaseReference.child(editTextLastNameTxt).child("vehicleType").setValue(VehicleTypeTxt);
                                databaseReference.child(editTextLastNameTxt).child("password").setValue(editTextPhoneNumberTxt);

                                Toast.makeText(register.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                                // Pass data to the next activity
                                Intent intent = new Intent(register.this, login.class); // Assume MainActivity hosts profileFragment
                                intent.putExtra("name", editTextFullNameTxt);
                                intent.putExtra("email", editTextLastNameTxt);
                                intent.putExtra("phoneNumber", editTextPhoneNumberTxt);
                                intent.putExtra("license", editTextLicenseNumberTxt);
                                intent.putExtra("expiry", editTextLicenseExpiryDatetxt);
                                intent.putExtra("password", editTextPhoneNumberTxt);

                                startActivity(intent); // Start the activity that hosts the profileFragment
                                finish(); // Close register activity
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle possible errors
                        }
                    });
                }
            }
        });

        loginnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login click
                startActivity(new Intent(register.this, login.class));
            }
        });
    }
}
