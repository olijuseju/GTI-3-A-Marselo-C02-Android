package com.example.jjpeajar.proyecto_3a_josejulio.src.main.notification.adapter;

/**
 * @author Andrey Kuzmin
 * NotificationAdapter
 * 2021-11-27
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Notification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {


    private Context context; //contexto
    private List<Notification> notificationList = new ArrayList<>();;

    //costructor del adapter
    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    /**
     * La descripción de onCreateViewHolder. Funcion inicia el layout de cada item del rv.
     *
     * @param parent ViewGroup ,
     * @param viewType int ,
     * @return NotificationHolder , devuelve la clase con los atributos del layout del item del rv
     */
    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_notification_rv_item,parent,false);

        return new NotificationHolder(v);
    }

    /**
     * La descripción de onBindViewHolder. Funcion rellena cada item del rv.
     *
     * @param holder holder ,  clase que llamamos para rellener cada item con sus atributos
     * @param position int , posicion del item en el rv
     */
    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        // sacar el pojo de la lista
        final Notification notificationItem= notificationList.get(position);
        //obtener fecha
        String convertedTime = notificationItem.getDate();
        if(convertedTime != null){  //si no es null
            holder.txt_time.setText(convertedTime);
        }

        //rellenamos con el mensaje de la notificacion
        holder.txt_principal.setText(notificationItem.getMessage());

        //obtenemos el tipo de la notificacion
        String typeNotification= notificationItem.getType();
        if( typeNotification.equals("Warnning") ){ // si es de alerta
            //cambiar el icono y el color
            holder.icon_back.setBackground( context.getDrawable(R.drawable.yellow_rectangle) );
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.icons_exclamation));
            holder.icon.setImageTintList(context.getResources().getColorStateList(R.color.naranja_borde));
            holder.icon.setRotation(180);
        }else if( typeNotification.equals("Information") ){ //si es de informacion
            holder.icon_back.setBackground( context.getDrawable(R.drawable.blue_rectangle) );
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.icons_info));
            holder.icon.setImageTintList(context.getResources().getColorStateList(R.color.azul_borde));
        }else if( typeNotification.equals("Danger") ){
            holder.icon_back.setBackground( context.getDrawable(R.drawable.red_rectangle) );
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.icons_exclamation));
            holder.icon.setImageTintList(context.getResources().getColorStateList(R.color.rojo_borde));

        }

    }

    /**
     * La descripción de getItemCount. Funcion devuelve el tamanyo de la lista de notificaciones
     *
     * @return int , devuelve el size de la lista de rv
     */
    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    //clase Holder para el rv , el cual declara los atributos que ba a tener el item
    public static class NotificationHolder extends RecyclerView.ViewHolder{
        //palette
        protected ConstraintLayout icon_back;
        protected ImageView icon;
        protected TextView txt_principal;
        protected TextView txt_time;
        //construcor
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            //findById
            icon_back=itemView.findViewById(R.id.notification_item_icon_back);
            icon=itemView.findViewById(R.id.notification_item_icon);
            txt_principal=itemView.findViewById(R.id.notification_item_txt);
            txt_time=itemView.findViewById(R.id.notification_item_time);
        }
    }
}
