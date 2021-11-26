package com.example.jjpeajar.proyecto_3a_josejulio.src.main.notification;

/**
 * @author Andrey Kuzmin
 * NotificationFragment
 * 2021-11-23
 */

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioNotification;
import com.example.jjpeajar.proyecto_3a_josejulio.src.main.notification.adapter.NotificationAdapter;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Notification;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //atributtes
    private List<Notification> notificationList = new ArrayList<>();
    private String access_token;
    private ConstraintLayout btn_clear;

    //logica
    LogicaNegocioNotification logicaNegocioNotification;

    //rv
    private RecyclerView rv_notifications;
    //adapter
    private NotificationAdapter notificationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_notification, container, false);

        //init logica
        logicaNegocioNotification = new LogicaNegocioNotification();

        //get access_token from signed user
        //coockies
        SharedPreferences shared= this.getActivity().getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , getContext().MODE_PRIVATE);

        //si ya ha iniciado sesion
        access_token = (shared.getString("access_token", null));

        //findByid
        rv_notifications=v.findViewById(R.id.notification__rv_notifications);
        btn_clear=v.findViewById(R.id.fragment_notification_btn_clear);

        //methods
        initRvNotifications();
        getNotificationItem();

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAlertaClear();
            }
        });


        return v;
    }

    private void initRvNotifications(){
        //defino que el rv no tenga fixed size
        rv_notifications.setHasFixedSize(false);
        rv_notifications.setNestedScrollingEnabled(false);
        //manejador para declarar la direccion de los items del rv
        rv_notifications.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    //metodo que rellena la lista de elementos
    private void getNotificationItem(){
        notificationList.clear();

        logicaNegocioNotification.obtenerNotificacionesByIdUser(access_token, new LogicaNegocioNotification.ObtenerNotificacionesByIdUserCallback() {
            @Override
            public void onCompletedObtenerNotificacionesByIdUserCallback(List<Notification> notifications) {
                notificationList = notifications;

                notificationAdapter= new NotificationAdapter(getContext() , notificationList);
                rv_notifications.setAdapter(notificationAdapter);
            }

            @Override
            public void onFailedObtenerNotificacionesByIdUserCallback(boolean res) {

            }
        });
    }

    private void initAlertaClear(){
        MaterialAlertDialogBuilder alertDialogBuilder= new MaterialAlertDialogBuilder(getContext());
        alertDialogBuilder.setMessage(R.string.clearText_dialog);
        //alertDialogBuilder.setMessage("");
        alertDialogBuilder.setNegativeButton(R.string.cancelButton_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.setPositiveButton(R.string.clearConfirmButton_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                limpiarNotificaciones();
            }
        });
        alertDialogBuilder.show();
    }

    private void limpiarNotificaciones(){
        logicaNegocioNotification.DeleteNotificacionesByIdUser(access_token, new LogicaNegocioNotification.DeleteNotificacionesByIdUserCallback() {
            @Override
            public void onCompletedDeleteNotificacionesByIdUserCallback(String message) {
                getNotificationItem();
            }

            @Override
            public void onFailedDeleteNotificacionesByIdUserCallback(boolean res) {

            }
        });
    }
}