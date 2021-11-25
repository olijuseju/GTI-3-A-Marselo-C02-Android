package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.vincular;

/**
 * @author Andrey Kuzmin
 * VincularDispositivoActivity
 * 2021-11-24
 */

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioUsarios;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserInformationController;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class VincularDispositivoActivity extends AppCompatActivity {

    //-----poner aca attributes etc... -----//
    //cards
    private MaterialCardView step1Card;
    private MaterialCardView step3Card;
    //state buttons cards
    private FloatingActionButton step1State;
    private FloatingActionButton step3State;
    //bools
    private  boolean isStep1Done = false;
    private  boolean isStep3Done = false;
    //back button
    private ConstraintLayout backButton;
    //confirm create machine button
    private ConstraintLayout confirmButton;
    //step 3 bottom sheet
    private ConstraintLayout quit_step3_bottom_sheet;
    private ConstraintLayout confirm_step3_bottom_sheet;
    private TextView step3_model_text;
    private ImageView step3_1_state;
    //string , int ....
    private String deviceModel;
    private String deviceCode;
    private String nameDevice;
    private String access_token;

    // Logica
    private LogicaNegocioUsarios logicaNegocioUsarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincular_dispositivo);

        //coockies
        SharedPreferences shared= getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , MODE_PRIVATE);

        //si ya ha iniciado sesion
        access_token = (shared.getString("access_token", null));

        Log.d("pepeupdate", "  RECIBIDO -------------------------------------  ");
        Log.d("pepeupdate", "  access ->" + access_token+"");

        logicaNegocioUsarios = new LogicaNegocioUsarios();

        //findById elements
        step1Card=findViewById(R.id.step1_card);
        step3Card=findViewById(R.id.step3_card);
        step1State=findViewById(R.id.step1_state);
        step3State=findViewById(R.id.step3_state);
        backButton=findViewById(R.id.back_newDevice);
        confirmButton=findViewById(R.id.confirm_create_device);

        //call methods
        ClearAllState(); //all to default

        //onclick methods
        backButton.setOnClickListener(new View.OnClickListener() { //btn volver atras
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        step1Card.setOnClickListener(new View.OnClickListener() { //step1 card click
            @Override
            public void onClick(View v) {
                QrScan();
            }
        });
        step3Card.setOnClickListener(new View.OnClickListener() { //step1 card click
            @Override
            public void onClick(View v) {
                initStep3BottomSheet();
            }
        });




        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("pepe", access_token);
                int serial;

                if(isStep1Done  && isStep3Done){ //si todos los pasos estas terminados
                    //guardamos los datos en el objeto machine


                    try{

                        serial = Integer.parseInt(deviceModel);

                    }catch (Exception e){
                        serial = 000;
                    }

                    Log.d("pepe",serial+"");
                    logicaNegocioUsarios.vincularDispoitivo(serial, access_token, new LogicaNegocioUsarios.VinculateDeviceCallback() {
                        @Override
                        public void onCompletedVinculateDevice(String serial) {
                            SharedPreferences shared= getSharedPreferences(
                                    "com.example.jjpeajar.proyecto_3a_josejulio"
                                    , MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("serial_device", serial);
                            editor.commit();
                            //cambio de actividad
                            finish();
                        }

                        @Override
                        public void onFailedVinculateDevice(boolean resultado) {
                            setSnackbar(findViewById(R.id.layout_vincular_dispositivo), "El codigo no es correcto");
                            step1StateInactive();
                        }
                    });

                }else{ //faltan pasos por hacer
                    setSnackbar(findViewById(R.id.layout_vincular_dispositivo), getString(R.string.snack_steps_remain));
                }
            }
        });
    }

    //init qr scan
    private void QrScan(){
        IntentIntegrator integrator= new IntentIntegrator(VincularDispositivoActivity.this);
        integrator.setBeepEnabled(false);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt(getResources().getString(R.string.step1Text_newDeviceQR));
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                deviceModel ="";
                deviceCode ="";
                step1StateInactive();
            } else {
                deviceModel =result.getContents().toString(); //get the machine code
                step1StateActive();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void step1StateActive(){
        step1State.setImageResource(R.drawable.icons_active_state); //change icon to active
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            step1State.setImageTintList(ColorStateList.valueOf(getColor(R.color.check_verde))); //change color to active
        }
        isStep1Done=true;
    }
    private void step1StateInactive(){
        step1State.setImageResource(R.drawable.icons_circle_inactive); //change icon to Inactive
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            step1State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
        }
        isStep1Done=false;
        //cuando hay 1 paso incompleto el paso 3 automaticamente pasa a incompleto
        step3StateInactive();
    }
    private void step3StateActive(){
        step3State.setImageResource(R.drawable.icons_active_state); //change icon to active
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            step3State.setImageTintList(ColorStateList.valueOf(getColor(R.color.check_verde))); //change color to active
        }
        isStep3Done=true;
    }
    private void step3StateInactive(){
        step3State.setImageResource(R.drawable.icons_circle_inactive); //change icon to Inactive
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            step3State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
        }
        isStep3Done=false;
    }

    private void ClearAllState(){
        deviceModel =""; //clear el codigo escaneado
        deviceCode =""; //clear el codigo escaneado
        nameDevice =""; //clear el nombre puesto
        //clear todos los iconos a incompleto
        step1State.setImageResource(R.drawable.icons_circle_inactive);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            step1State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
        }
        step3State.setImageResource(R.drawable.icons_circle_inactive);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            step3State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
        }
    }

    private void initStep3BottomSheet(){
        //set the bottom sheet
        BottomSheetDialog verificationBottomSheet = new BottomSheetDialog(VincularDispositivoActivity.this);
        //set the layout of the bottom sheet
        verificationBottomSheet.setContentView(R.layout.activity_vincular_dispositivo_step3);
        //findbyid del bottom sheet
        quit_step3_bottom_sheet=verificationBottomSheet.findViewById(R.id.quit_step3_bottom_sheet);
        confirm_step3_bottom_sheet=verificationBottomSheet.findViewById(R.id.confirm_step3_bottom_sheet);
        step3_model_text=verificationBottomSheet.findViewById(R.id.qr_code_step3);
        step3_1_state=verificationBottomSheet.findViewById(R.id.state_1_step3);
        //onclick methods
        //quit bottomsheet
        quit_step3_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationBottomSheet.dismiss();
            }
        });
        //confirm check button onclick
        confirm_step3_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStep1Done){ //si estan hechos los pasos 1 y 2
                    step3StateActive(); //call function
                }else{ //sino
                    step3StateInactive();
                }
                verificationBottomSheet.dismiss(); //cerramos bottom sheet
            }
        });
        //si el paso 1 esta completado
        if(isStep1Done){
            String modelResult=getString(R.string.step3_modelText_newDevice) + " "+ deviceModel;
            //inserto su string en el textview
            step3_model_text.setText(modelResult);
            //hacer el texto bold
            step3_model_text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            //cambio de color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                step3_model_text.setTextColor(getColor(R.color.black));
            }
            //set activate img state
            step3_1_state.setImageResource(R.drawable.icons_active_state);
            //change color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                step3_1_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.check_verde)));
            }
        }else{
            //inserto string default en el textview
            step3_model_text.setText(getString(R.string.step3_empty));
            //hacer el texto normal
            step3_model_text.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //set inactive img state
            step3_1_state.setImageResource(R.drawable.icons_circle_inactive);
            //change color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                step3_1_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
            }
        }
        verificationBottomSheet.show();
    }

    /**
     * La descripción de setSnackbar. Funcion que muestra en pantalla un mensaje que acción.
     *
     * @param snackBarText String con el mensaje que queremos mostrar en pantalla.
     *
     */
    public void setSnackbar(View actualActivity, String snackBarText){
        Snackbar snackBar = Snackbar.make(actualActivity, snackBarText,Snackbar.LENGTH_LONG);
        snackBar.setActionTextColor(Color.CYAN);
        snackBar.setAction("Cerrar", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }
}