package com.example.jjpeajar.proyecto_3a_josejulio.src.main.notification.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Notification;

import java.util.ArrayList;
import java.util.List;

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

        holder.txt_time.setText(notificationItem.getDate());
        holder.txt_principal.setText(notificationItem.getMessage());

        String typeNotification= notificationItem.getType();
        if( typeNotification.equals("Warnning") ){
            holder.icon_back.setBackground( context.getDrawable(R.drawable.ic_profile_pasos_bg) );
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.icons_exclamation));
            holder.icon.setRotation(180);
        }else if( typeNotification.equals("Information") ){
            holder.icon_back.setBackground( context.getDrawable(R.drawable.ic_profile_info_bg) );
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.icons_info));
        }else if( typeNotification.equals("Danger") ){
            holder.icon_back.setBackground( context.getDrawable(R.drawable.ic_profile_logout_bc) );
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.icons_exclamation));

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
