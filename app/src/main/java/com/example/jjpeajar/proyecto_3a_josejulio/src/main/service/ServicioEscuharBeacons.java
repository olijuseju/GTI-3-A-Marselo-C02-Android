package com.example.jjpeajar.proyecto_3a_josejulio.src.main.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.jjpeajar.proyecto_3a_josejulio.R;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaFake;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioMediciones;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioNotification;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.otro.Utilidades;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Medicion;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.NotificationController;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.TramaIBeacon;

import java.util.ArrayList;
import java.util.List;

// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------

// --------------------------------------------------------------
/**
 * @author Jose Julio Peñaranda
 * ServicioEscucharBeacons
 * 2021-10-14
 */
// --------------------------------------------------------------
/**
 * Este servicio en segundo plano se encarga de recibir los beacons
 * Los beacons están filtrados por nombre para solo recibir los de nuestro arduino
 * Al recibir un beacon crea un objeto medicionCO2
 * Con este objeto llama a la logica para realizar la peticion
 */
public class ServicioEscuharBeacons  extends IntentService {

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    private static final String ETIQUETA_LOG = ">>>>";

    private long tiempoDeEspera = 10000;
    private String dispositivoBuscado = null;
    private LogicaNegocioMediciones logicaNegocio = new LogicaNegocioMediciones();
    public ArrayList<Medicion> medicionC02s= new ArrayList<Medicion>();

    //notificaciones
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    public TramaIBeacon tib;

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    private BluetoothLeScanner elEscanner;

    private ScanCallback callbackDelEscaneo = null;

    // --------------------------------------------------------------
    // --------------------------------------------------------------


    private boolean seguir = true;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * ServicioEscucharBeacons()
     * constructor
     */
    public ServicioEscuharBeacons( ) {
        super("HelloIntentService");

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.constructor: termina");
    }

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    /**
     * inicializarBlueTooth()
     * Inicializamos el escaner Bluetooth
     *
     */
    private void inicializarBlueTooth() {
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos adaptador BT ");

        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitamos adaptador BT ");

        bta.enable();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled() );

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState() );

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");

        this.elEscanner = bta.getBluetoothLeScanner();

        if ( this.elEscanner == null ) {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Socorro: NO hemos obtenido escaner btle  !!!!");

        }

    } // ()


    // --------------------------------------------------------------
    // --------------------------------------------------------------
    /**
     * Mostramos la informacion del resultado del escaneo
     * @param resultado Resultado del escaneo Bluetooth
     */

    private void mostrarInformacionDispositivoBTLE( ScanResult resultado ) {

        BluetoothDevice bluetoothDevice = resultado.getDevice();
        byte[] bytes = resultado.getScanRecord().getBytes();
        int rssi = resultado.getRssi();

        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " ****** DISPOSITIVO DETECTADO BTLE ****************** ");
        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " nombre = " + bluetoothDevice.getName());
        Log.d(ETIQUETA_LOG, " toString = " + bluetoothDevice.toString());

        /*
        ParcelUuid[] puuids = bluetoothDevice.getUuids();
        if ( puuids.length >= 1 ) {
            //Log.d(ETIQUETA_LOG, " uuid = " + puuids[0].getUuid());
           // Log.d(ETIQUETA_LOG, " uuid = " + puuids[0].toString());
        }*/

        Log.d(ETIQUETA_LOG, " dirección = " + bluetoothDevice.getAddress());
        Log.d(ETIQUETA_LOG, " rssi = " + rssi );

        Log.d(ETIQUETA_LOG, " bytes = " + new String(bytes));
        Log.d(ETIQUETA_LOG, " bytes (" + bytes.length + ") = " + Utilidades.bytesToHexString(bytes));

        tib = new TramaIBeacon(bytes);


        Log.d(ETIQUETA_LOG, " ----------------------------------------------------");
        Log.d(ETIQUETA_LOG, " prefijo  = " + Utilidades.bytesToHexString(tib.getPrefijo()));
        Log.d(ETIQUETA_LOG, "          advFlags = " + Utilidades.bytesToHexString(tib.getAdvFlags()));
        Log.d(ETIQUETA_LOG, "          advHeader = " + Utilidades.bytesToHexString(tib.getAdvHeader()));
        Log.d(ETIQUETA_LOG, "          companyID = " + Utilidades.bytesToHexString(tib.getCompanyID()));
        Log.d(ETIQUETA_LOG, "          iBeacon type = " + Integer.toHexString(tib.getiBeaconType()));
        Log.d(ETIQUETA_LOG, "          iBeacon length 0x = " + Integer.toHexString(tib.getiBeaconLength()) + " ( "
                + tib.getiBeaconLength() + " ) ");
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToHexString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " major  = " + Utilidades.bytesToHexString(tib.getMajor()) + "( "
                + Utilidades.bytesToInt(tib.getMajor()) + " ) ");
        Log.d(ETIQUETA_LOG, " minor  = " + Utilidades.bytesToHexString(tib.getMinor()) + "( "
                + Utilidades.bytesToInt(tib.getMinor()) + " ) ");
        Log.d(ETIQUETA_LOG, " txPower  = " + Integer.toHexString(tib.getTxPower()) + " ( " + tib.getTxPower() + " )");
        Log.d(ETIQUETA_LOG, " ****************************************************");

    } // ()

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     *  parar()
     *  Paramos el servicio
     */
    public void parar () {

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() " );


        if ( this.seguir == false ) {
            return;
        }


        if ( this.callbackDelEscaneo == null ) {
            return;
        }

        this.elEscanner.stopScan( this.callbackDelEscaneo );
        this.callbackDelEscaneo = null;

        this.seguir = false;
        this.stopSelf();

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() : acaba " );

    }


    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public void onDestroy() {

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onDestroy() " );


        this.parar(); // posiblemente no haga falta, si stopService() ya se carga el servicio y su worker thread
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    /**
     *
     * @param intent Actividad de la que obtenemos el contexto y el tiempo de espera
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        inicializarBlueTooth();

        this.dispositivoBuscado = intent.getStringExtra("dispositivoBuscado");

        this.tiempoDeEspera = intent.getLongExtra("tiempoDeEspera", /* default */ 50000);
        this.seguir = true;

        // esto lo ejecuta un WORKER THREAD !

        long contador = 1;

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: empieza : thread=" + Thread.currentThread().getId() );

        SharedPreferences shared= getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , MODE_PRIVATE);

        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult( int callbackType, ScanResult resultado ) {
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanResult() ");
                mostrarInformacionDispositivoBTLE( resultado );
                byte[] bytes = resultado.getScanRecord().getBytes();
                TramaIBeacon tramaIBeacon = new TramaIBeacon(bytes);
                int data;
                int numTipoDato = Utilidades.bytesToInt(tramaIBeacon.getMajor());
                String tipoDato;


                if(numTipoDato==11){
                    tipoDato="CO2";
                    int datoBruto = Utilidades.bytesToInt(tramaIBeacon.getMinor());
                    data = (datoBruto/65535)*100;
                }else if(numTipoDato==12){
                    tipoDato="Temperatura";
                    data = Utilidades.bytesToInt(tramaIBeacon.getMinor());

                }else if(numTipoDato==10){
                    tipoDato="Humedad";
                    data = Utilidades.bytesToInt(tramaIBeacon.getMinor());

                }else{
                    tipoDato="Otros";
                    data = Utilidades.bytesToInt(tramaIBeacon.getMinor());

                }

                int device_id = shared.getInt("id_device", -1);
                if(device_id!=-1){
                    medicionC02s.add(new Medicion(Integer.parseInt(shared.getString("user_id", null)),device_id, 30,30, tipoDato,data, 20201020));
                    Log.d("clienterestandroid", medicionC02s.size()+"");
                    if(medicionC02s.size()==20){
                        Log.d("clienterestandroid", "llamamos a la logica");

                        String access_token=shared.getString("access_token", null);
                        for (Medicion medicion:medicionC02s) {
                            logicaNegocio.publicarMedicion(shared.getString("access_token", null), medicion.getUser_id(), medicion.getDevice_id(), medicion.getValue(), medicion.getLatitude(), medicion.getLongitude(), medicion.getType_read(), medicion.getDate(), new LogicaNegocioMediciones.PublicarMedicionesCallback() {
                                @Override
                                public void onCompletedPublicarMediciones(int success) {

                                }

                                @Override
                                public void onFailedPublicarMediciones(boolean res) {

                                }
                            });
                            if(medicion.getType_read().equals("CO2")){


                                if(medicion.getValue() > 0 ){
                                    callToLogicaToCrearNotificacion("Danger","El O2 es muy alta!" , access_token  , medicion);
                                }

                            }else if(medicion.getType_read().equals("Temperatura")){

                                if(medicion.getValue() >= 21 ){
                                    callToLogicaToCrearNotificacion("Danger","La temperatura es muy alta!" ,access_token , medicion);
                                } else if(medicion.getValue() < 13) {
                                    callToLogicaToCrearNotificacion("Warnning","La temperatura es muy baja, ponte un abrigo!" ,access_token , medicion);

                                }

                            }else if(medicion.getType_read().equals("Humedad")){

                                if(medicion.getValue() >= 56 ){
                                    callToLogicaToCrearNotificacion("Danger","La humedad es muy alta!" ,access_token , medicion);
                                }else if(medicion.getValue() < 10) {
                                    callToLogicaToCrearNotificacion("Warnning","La humedad es muy baja!" ,access_token , medicion);

                                }

                            }
                        }
                        medicionC02s.clear();
                    }
                }


            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onBatchScanResults() ");

            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanFailed() ");

            }
        };

        ScanFilter sf = new ScanFilter.Builder().setDeviceName( dispositivoBuscado ).build();
        List<ScanFilter> filters = new ArrayList<>();
        ScanSettings.Builder scan = new ScanSettings.Builder();
        filters.add(sf);
        this.elEscanner.startScan(filters, scan.build(), callbackDelEscaneo);
        Log.d(ETIQUETA_LOG, "  servicioEscucharBeacons(): empezamos a escanear buscando: " + dispositivoBuscado );


        try {

            while ( this.seguir ) {
                Thread.sleep(tiempoDeEspera);
                Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: tras la espera:  " + contador );
                contador++;
            }

            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent : tarea terminada ( tras while(true) )" );


        } catch (InterruptedException e) {
            // Restore interrupt status.
            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: problema con el thread");

            Thread.currentThread().interrupt();
        }

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: termina");

    }

    private void callToLogicaToCrearNotificacion(String type ,String message , String access_token , Medicion medicion){

        SharedPreferences shared= getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , MODE_PRIVATE);
        int user_id=Integer.valueOf(shared.getString("user_id", null));
        String date=String.valueOf(medicion.getDate());

        Log.d("pepe", " RRECIBIDO -------------------------------------  ");
        Log.d("pepe", "  CUERPO ->" + medicion.getValue()+"");

        Log.d("pepe", " DENGUEEEEEEEEEEEEEEEEEEEEEEEEEEEEE -------------------------------------  ");
        Log.d("pepe", "  CUERPO -> " + user_id +" "+  date +" "+  message +" "+ type);

        try{
            LogicaNegocioNotification logicaNegocioNotification= new LogicaNegocioNotification();
            logicaNegocioNotification
                    .crearNotificacion(access_token, user_id , date , message, type, new LogicaNegocioNotification.CrearNotificacionCallback() {
                        @Override
                        public void onCompletedCrearNotificacionCallback(NotificationController notificationController) {
                            createNotificationChannel();
                            createNotification( notificationController.getNotification().getType(), notificationController.getNotification().getMessage());
                        }

                        @Override
                        public void onFailedCrearNotificacionCallback(boolean res) {

                        }
                    });
        }catch (Exception e){

        }

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(String titulo , String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.icons_exclamation);
        builder.setContentTitle(titulo);
        builder.setContentText(message);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setGroup("GROUP_KEY_WORK_EMAIL");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
} // class
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
