package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.google.android.material.textfield.TextInputLayout;

public class EditUserActivity extends AppCompatActivity {

    private TextInputLayout txtInputLayout;
    private AutoCompleteTextView dropdowntxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        txtInputLayout = findViewById(R.id.input_town);
        dropdowntxt = findViewById(R.id.dropDown_txt);

        String[] items = new String[]{
                "Teulada",
                "Albaida",
                "Valencia"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                EditUserActivity.this,
                R.layout.dropdown_item,
                items
        );

        dropdowntxt.setAdapter(adapter);
    }
}