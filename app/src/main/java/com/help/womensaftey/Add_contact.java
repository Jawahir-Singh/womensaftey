package com.help.womensaftey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Add_contact extends AppCompatActivity {
    Button send;

    EditText  send1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        SharedPreferences prefs = getSharedPreferences("User_Details", MODE_PRIVATE);
        String phone3 = prefs.getString("phone", null);
        send=(Button)findViewById(R.id.send);
        send1= (EditText)findViewById(R.id.send1);
        send1.setText(phone3);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Phone4 = send1.getText().toString().trim();
                SharedPreferences.Editor editor = getSharedPreferences("User_Details", MODE_PRIVATE).edit();
                editor.putString("phone", Phone4);
                editor.apply();

                Intent intent = new Intent(Add_contact.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

            }
        });



    }
}
