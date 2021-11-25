package com.example.jjpeajar.proyecto_3a_josejulio.src.main.notification.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {


    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_notification_rv_item,parent,false);

        return new NotificationHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
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
