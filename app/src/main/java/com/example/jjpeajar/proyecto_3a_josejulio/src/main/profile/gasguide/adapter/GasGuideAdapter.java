package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.gasguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.gasguide.GasInformationActivity;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Gas;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Kuzmin
 * LogicaNecocioGases
 * 2021-12-14
 */

public class GasGuideAdapter extends RecyclerView.Adapter<GasGuideAdapter.GasGuideHolder>{

    //atributos
    private List<Gas> gases = new ArrayList<>();
    private Context context;

    //constructor
    public GasGuideAdapter(List<Gas> gases, Context context) {
        this.gases = gases;
        this.context = context;
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
    public GasGuideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_gas_guide_rv_item,parent,false);

        return new GasGuideHolder(v);
    }

    /**
     * La descripción de onBindViewHolder. Funcion rellena cada item del rv.
     *
     * @param holder holder ,  clase que llamamos para rellener cada item con sus atributos
     * @param position int , posicion del item en el rv
     */
    @Override
    public void onBindViewHolder(@NonNull GasGuideHolder holder, int position) {
        //get cada item de la lista
        Gas gasItem = gases.get(position);

        //set name
        holder.name_gas.setText(gasItem.getName());
        //set image
        String url = gasItem.getRoute_image();
        Log.d("pepe", "  RECIBIDO -------------------------------------  ");
        Log.d("url", "  CUERPO ->" + url+"");
        Picasso.get().load(url).into(holder.img_gas);

        //al pulsar sobre un gas
        holder.card_gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson= new Gson();
                String json = gson.toJson(gasItem);
                Intent i = new Intent(context, GasInformationActivity.class);
                i.putExtra("gas", json);
                context.startActivity(i);
            }
        });

    }

    /**
     * La descripción de getItemCount. Funcion devuelve el tamanyo de la lista de notificaciones
     *
     * @return int , devuelve el size de la lista de rv
     */
    @Override
    public int getItemCount() {
        return gases.size();
    }

    //clase Holder para el rv , el cual declara los atributos que ba a tener el item
    public static class GasGuideHolder extends RecyclerView.ViewHolder{

        protected TextView name_gas;
        protected MaterialCardView card_gas;
        protected ImageView img_gas;

        public GasGuideHolder(@NonNull View itemView) {
            super(itemView);

            name_gas = itemView.findViewById(R.id.gas_item_name);
            card_gas = itemView.findViewById(R.id.gas_item_card);
            img_gas = itemView.findViewById(R.id.gas_item_image);
        }
    }
}
