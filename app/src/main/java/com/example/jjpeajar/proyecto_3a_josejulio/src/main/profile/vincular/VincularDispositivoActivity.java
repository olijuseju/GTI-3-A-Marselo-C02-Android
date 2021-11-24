package com.example.jjpeajar.proyecto_3a_josejulio.src.main.profile.vincular;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincular_dispositivo);

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
                if(isStep1Done  && isStep3Done){ //si todos los pasos estas terminados
                    //guardamos los datos en el objeto machine


                    //cambio de actividad
                    finish();
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

                String[] strings= deviceModel.split("-");
                if(strings.length > 1){ //si es un qr de infinity crop
                    deviceModel = strings[1];
                    deviceCode = strings[0];
                    //comprobar si la maquina ya esta registrada o no

                }else{ //si no es nuestro qr
                    setSnackbar(findViewById(R.id.layout_vincular_dispositivo), getString(R.string.snack_qr_error));
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void step1StateActive(){
        step1State.setImageResource(R.drawable.icons_circulo); //change icon to active
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
        step3State.setImageResource(R.drawable.icons_circulo); //change icon to active
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
            step3_1_state.setImageResource(R.drawable.icons_circulo);
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

    //set snackbar method
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