package com.example.jjpeajar.proyecto_3a_josejulio.src.main.menu;

/**
 * @author Andrey Kuzmin
 * MenuMainActivity
 * 2021-11-17
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.graphic.GraphicFragment;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.home.HomeFragment;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.notification.NotificationFragment;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.ProfileFragment;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.service.ServicioEscuharBeacons;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuMainActivity extends AppCompatActivity {

    //palette
    private BottomNavigationView bottomNavigationView;
    //fragment actual que esta pulsado
    Fragment currentFragment = null;
    //transaction del fragment
    FragmentTransaction ft;
    //atributtes
    private static final String ETIQUETA_LOG = ">>>>";
    private static final int CODIGO_PETICION_PERMISOS = 11223344;
    private static final int SOLICITUD_PERMISO_LOCALIZACION = 1234;
    private Intent elIntentDelServicio = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        //pedir permiso
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            solicitarPermiso(Manifest.permission.ACCESS_FINE_LOCATION,
                    "Sin el permiso localización no puedo mostrar la distancia"+
                            " a los lugares.", SOLICITUD_PERMISO_LOCALIZACION, MenuMainActivity.this);
        }

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

    /**
     *
     * @param v indica si el usuario pulsó el botón
     */
    public void botonBuscarNuestroDispositivoBTLEPulsado() {
        Log.d(ETIQUETA_LOG, " boton nuestro dispositivo BTLE Pulsado" );
        //this.buscarEsteDispositivoBTLE( Utilidades.stringToUUID( "EPSG-GTI-PROY-3A" ) );
        if ( this.elIntentDelServicio != null ) {
            // ya estaba arrancado
            return;
        }

        Log.d("pepe", " MainActivity.constructor : voy a arrancar el servicio");

        this.elIntentDelServicio = new Intent(this, ServicioEscuharBeacons.class);

        this.elIntentDelServicio.putExtra("tiempoDeEspera", (long) 5000);
        //this.buscarEsteDispositivoBTLE( "EPSG-GTI-PROY-3A" );
        this.buscarEsteDispositivoBTLE( "josej" );

        Log.d(ETIQUETA_LOG, " boton arrancar servicio Pulsado" );



    } // ()

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    /**
     * Buscamos un dispositivo en concreto via Bluetooth
     * @param dispositivoBuscado id del dispositivo a buscar
     */
    private void buscarEsteDispositivoBTLE(final String dispositivoBuscado ) {
        Log.d(ETIQUETA_LOG, " buscarEsteDispositivoBTLE(): empieza ");

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): instalamos scan callback ");

        this.elIntentDelServicio.putExtra("dispositivoBuscado", dispositivoBuscado);

        startService(elIntentDelServicio);
    } // ()

    // --------------------------------------------------------------
    // --------------------------------------------------------------

    public static void solicitarPermiso(final String permiso, String
            justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }}).show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }
}