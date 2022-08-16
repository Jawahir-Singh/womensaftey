package com.help.womensaftey;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.help.womensaftey.Adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    MyRecyclerViewAdapter adapter;

    List<Contact> contacts;
    ArrayList<String> animalNames;
    RecyclerView recyclerView;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Log.d("Insert: ", "Inserting ..----2");

        db = new DatabaseHandler(this);

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
   contacts = db.getAllContacts();
   animalNames = new ArrayList<>();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " +
                    cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);

            animalNames.add(log);
        }

        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, contacts);
        recyclerView.setAdapter(adapter);


    }

    public void Save_no(View view){

        EditText name =(EditText)findViewById(R.id.name);
        EditText phone =(EditText)findViewById(R.id.phone);

        DatabaseHandler db = new DatabaseHandler(this);

        if(name.getText().toString().trim().equals("")) {
            // Inserting Contacts
            Log.d("Enter Name", "---------");
            Toast.makeText(getApplication(), "Enter Name", Toast.LENGTH_SHORT).show();


        }else {
            if (phone.getText().toString().trim().equals("")) {
                // Inserting Contacts
                Log.d("Enter Phone Number", "---------");
                Toast.makeText(getApplication(), "Enter phone  Number", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(name.getText().toString().trim(), phone.getText().toString().trim());
                db.addContact(new Contact(name.getText().toString().trim(), phone.getText().toString().trim()));

                contacts.clear();
                animalNames.clear();
                contacts = db.getAllContacts();
                animalNames = new ArrayList<>();


                for (Contact cn : contacts) {
                    String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " +
                            cn.getPhoneNumber();
                    // Writing Contacts to log
                    Log.d("Name: ", log);

                    animalNames.add(log);
                }


                adapter = new MyRecyclerViewAdapter(this, contacts);
                //  adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);

                name.setText("");
                phone.setText("");

            }
        }

    }


    public void edit_pop(final Context context, final String idd, String name2, String phone2){
        LayoutInflater factory = LayoutInflater.from(this);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.recycle_update);

        TextView text = (TextView) dialog.findViewById(R.id.tit);
        text.setText("Update");

        final Button cancel_Button = (Button) dialog.findViewById(R.id.cancel);
        FloatingActionButton update = (FloatingActionButton) dialog.findViewById(R.id.update);

        final EditText name1 = (EditText) dialog.findViewById(R.id.name);
        final EditText phone1 = (EditText) dialog.findViewById(R.id.phone);

        name1.setText(name2);
        phone1.setText(phone2);

        final DatabaseHandler db = new DatabaseHandler(this);


        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Perfome Action
                if(name1.getText().toString().trim().equals("")) {
                    // Inserting Contacts
                    Log.d("Enter Name", "---------");
                    Toast.makeText(getApplication(), "Enter Name", Toast.LENGTH_SHORT).show();

                }else {
                    if (phone1.getText().toString().trim().equals("")) {
                        // Inserting Contacts
                        Log.d("Enter Phone Number", "---------");
                        Toast.makeText(getApplication(), "Enter phone number", Toast.LENGTH_SHORT).show();
                    } else {

                        int chk = db.updateContact(idd, name1.getText().toString().trim(),phone1.getText().toString().trim());

                        if(chk>0){

                            dialog.dismiss();

// Reload..................................................
                            contacts.clear();
                            animalNames.clear();
                            contacts = db.getAllContacts();
                            animalNames = new ArrayList<>();
                            for (Contact cn : contacts) {
                                String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " +
                                        cn.getPhoneNumber();
                                // Writing Contacts to log
                                Log.d("Name: ", log);
                                animalNames.add(log);
                            }
                            adapter = new MyRecyclerViewAdapter(context, contacts);
                            recyclerView.setAdapter(adapter);

                            //...................................................
                        }
                    }}


            }
        });
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void Delete_pop(final String idd, String name2, String phone2){

        final DatabaseHandler db = new DatabaseHandler(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm ");
        builder.setMessage("Are you sure you want to delete");
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                db.deleteContact(idd);


// Reload..................................................
                contacts.clear();
                animalNames.clear();
                contacts = db.getAllContacts();
                animalNames = new ArrayList<>();
                for (Contact cn : contacts) {
                    String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " +
                            cn.getPhoneNumber();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                    animalNames.add(log);
                }
                adapter = new MyRecyclerViewAdapter(MainActivity2.this, contacts);
                recyclerView.setAdapter(adapter);

                //...................................................
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();

    }

    public void Go_Home(View view){
        Intent intent = new Intent(MainActivity2.this, emergency_sms.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}