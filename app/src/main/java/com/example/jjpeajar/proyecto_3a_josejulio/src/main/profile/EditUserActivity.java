package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile;

/**
 * @author Belén Grande López
 * 2021-11-16
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.google.android.material.textfield.TextInputLayout;

public class EditUserActivity extends AppCompatActivity {

    // Atributos
    private TextInputLayout txtInputLayout;
    private AutoCompleteTextView dropdowntxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        txtInputLayout = findViewById(R.id.input_town);
        dropdowntxt = findViewById(R.id.dropDown_txt);

        // Array de strings de items para el desplegable
        String[] items = new String[]{
                "Teulada",
                "Albaida",
                "Valencia"
        };

        // Adapter para el array con el layout de los items suelos y el array de los items que contiene el desplegable
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                EditUserActivity.this,
                R.layout.dropdown_item,
                items
        );

        // Le asocio al input el adapter
        dropdowntxt.setAdapter(adapter);
    }
}