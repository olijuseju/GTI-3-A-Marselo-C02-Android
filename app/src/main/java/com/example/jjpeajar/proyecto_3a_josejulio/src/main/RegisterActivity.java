package com.example.jjpeajar.proyecto_3a_josejulio.src.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout txtInputLayout;
    private AutoCompleteTextView dropdowntxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtInputLayout = findViewById(R.id.input_town);
        dropdowntxt = findViewById(R.id.dropDown_txt);

        String[] items = new String[]{
               "Teulada",
                "Albaida",
                "Valencia"

        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                RegisterActivity.this,
                R.layout.dropdown_item,
                items
        );

        dropdowntxt.setAdapter(adapter);


    }
}