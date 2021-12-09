package com.example.jjpeajar.proyecto_3a_josejulio.src.main.notification.adapter;

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

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_notification_rv_item,parent,false);

        return new NotificationHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        // sacar el pojo de la lista
        final Notification notificationItem= notificationList.get(position);

        String convertedTime = notificationItem.getDate();
        if(convertedTime != null){
            holder.txt_time.setText(convertedTime);
        }

        holder.txt_principal.setText(notificationItem.getMessage());

        String typeNotification= notificationItem.getType();
        if( typeNotification.equals("Warnning") ){
            holder.icon_back.setBackground( context.getDrawable(R.drawable.yellow_rectangle) );
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.icons_exclamation));
            holder.icon.setImageTintList(context.getResources().getColorStateList(R.color.naranja_borde));
            holder.icon.setRotation(180);
        }else if( typeNotification.equals("Information") ){
            holder.icon_back.setBackground( context.getDrawable(R.drawable.blue_rectangle) );
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.icons_info));
            holder.icon.setImageTintList(context.getResources().getColorStateList(R.color.azul_borde));
        }else if( typeNotification.equals("Danger") ){
            holder.icon_back.setBackground( context.getDrawable(R.drawable.red_rectangle) );
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.icons_exclamation));
            holder.icon.setImageTintList(context.getResources().getColorStateList(R.color.rojo_borde));

        }

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationHolder extends RecyclerView.ViewHolder{

        protected ConstraintLayout icon_back;
        protected ImageView icon;
        protected TextView txt_principal;
        protected TextView txt_time;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);

            icon_back=itemView.findViewById(R.id.notification_item_icon_back);
            icon=itemView.findViewById(R.id.notification_item_icon);
            txt_principal=itemView.findViewById(R.id.notification_item_txt);
            txt_time=itemView.findViewById(R.id.notification_item_time);
        }
    }
}
