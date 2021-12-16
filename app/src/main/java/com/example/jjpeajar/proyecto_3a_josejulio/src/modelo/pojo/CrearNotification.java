package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

/**
 * @author Andrey Kuzmin
 * CrearNotification
 * 2021-12-08
 *
 * Clase que se encarga de lanzar una notificacion.
 *
 */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.jjpeajar.proyecto_3a_josejulio.R;

public class CrearNotification {

    //notificaciones
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    private Context context;

    //constructor de la clase
    public CrearNotification(Context context) {
        this.context = context;
    }

    /**
     * La descripción de initNotificationChannel. Funcion -> inicializar canal para poder mostrar diferentes notificaciones
     */
    public void initNotificationChannel(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    /**
     * La descripción de initNotificationChannel. Funcion -> inicializar notificacion
     */
    public void initNotification(String titulo , String mensaje){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(titulo);
        builder.setContentText(mensaje);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

    /**
     * La descripción de initNotificationChannel. Funcion -> comprobar si las notificaciones estan activas o no
     * @return Boolean , devuelve true si hay una notificacion activa
     */
    public Boolean isNotificationActive(){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        return notificationManagerCompat.areNotificationsEnabled();
    }
}
