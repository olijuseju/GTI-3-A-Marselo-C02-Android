package com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.graphic.GraphicFragment;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.home.HomeFragment;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.notification.NotificationFragment;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuMainActivity extends AppCompatActivity {

    //palette
    private BottomNavigationView bottomNavigationView;
    //fragment actual que esta pulsado
    Fragment currentFragment = null;
    //transaction del fragment
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        //fragment control configuration
        ft = getSupportFragmentManager().beginTransaction();
        //cargamos al inicio del activity el fragment principal , HomeFragment
        currentFragment = new HomeFragment();
        ft.replace(R.id.content, currentFragment); //remplazar por el que hay (en este caso null)
        ft.commit(); //just do it

        //findById
        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        //actions

        //set menu navigation
        bottomNavigationView.setBackground(null); //le quito el borde ya que se ve unas lineas de fondo , nuse porque
        //desactivamos el item del menu numero 2 (od.placeholder)
        //ya que esta vacio para hacer hueco y que se quede en el medio para el boton de mapa
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        //detectar pulsado
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) { //si el id del item pulsado (se encuentra en la carpeta menu/bottom_nav_menu.xml)
                    case R.id.myHome:
                        currentFragment = new HomeFragment(); //fragment que voy a cargar
                        ft = getSupportFragmentManager().beginTransaction(); //movimiento del fragment
                        ft.replace(R.id.content, currentFragment); //remplazar
                        ft.commit(); //just do it
                        return true; //salir del switch
                    case R.id.myGraphics:  // -> lo mismo para lo demás
                        currentFragment = new GraphicFragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content, currentFragment);
                        ft.commit();
                        return true;
                    case R.id.myNotifications:
                        currentFragment = new NotificationFragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content, currentFragment);
                        ft.commit();
                        return true;
                    case R.id.myProfile:
                        currentFragment = new ProfileFragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content, currentFragment);
                        ft.commit();
                        return true;
                }

                return false;
            }
        });

    }
}