package com.help.womensaftey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;



public class ProfileActivity extends AppCompatActivity {

    String phonenumber;
    Button save;
    EditText name, phone, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences prefs = getSharedPreferences("User_Details", MODE_PRIVATE);
        String name2 = prefs.getString("name", null);
        String phone2 = prefs.getString("phone", null);
        String email2 = prefs.getString("email", null);

        save=(Button)findViewById(R.id.save);

        name= (EditText)findViewById(R.id.name);
        phone= (EditText)findViewById(R.id.phone);
        email= (EditText)findViewById(R.id.email);

        name.setText(name2);
        phone.setText(phone2);
        email.setText(email2);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name1= name.getText().toString().trim();
                String Phone1 = phone.getText().toString().trim();
                String email1 = email.getText().toString().trim();

                SharedPreferences.Editor editor = getSharedPreferences("User_Details", MODE_PRIVATE).edit();
                editor.putString("name", Name1);
                editor.putString("phone", Phone1);
                editor.putString("email", email1);
                editor.apply();

                Intent intent = new Intent(ProfileActivity.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

            }
        });

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }
        });

    }
}
