package com.example.jjpeajar.proyecto_3a_josejulio.src.main.service;

import android.Manifest;
import android.app.IntentService;
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
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioMediciones;
import com.example.jjpeajar.proyecto_3a_josejulio.src.logica.LogicaNegocioNotification;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.GPSTracker;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.otro.Utilidades;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.CrearNotification;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Medicion;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.NotificationController;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.TramaIBeacon;

import java.math.BigInteger;
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
public class ServicioEscuharBeacons extends IntentService {

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    private static final String ETIQUETA_LOG = ">>>>";

    private long tiempoDeEspera = 10000;
    private String dispositivoBuscado = null;
    private LogicaNegocioMediciones logicaNegocio = new LogicaNegocioMediciones();
    //arrays
    public ArrayList<Medicion> mediciones = new ArrayList<Medicion>(); //aca guardamos mediciones
    public ArrayList<Integer> beaconsCO2 = new ArrayList<Integer>(); //aca guardamos beacons co2
    public ArrayList<Integer> beaconsTemp = new ArrayList<Integer>(); //aca guardamos beacons temp
    public ArrayList<Integer> beaconsHum = new ArrayList<Integer>(); //aca guardamos becaons hum
    public ArrayList<Double>  beaconsDistancia = new ArrayList<Double>();

    public boolean tamuerto;
    public boolean tatuerto;

    //GPSTracker
    GPSTracker gpsTracker;
    //latitud & longitud
    private double latitud;
    private double longitud;
    //booleanos
    private boolean isInformation = false;
    private boolean isAlerta = false;
    private boolean isPeligro = false;
    private boolean isErrorSensor = false;
    private boolean isSensorRoto = false;
    private boolean isReadBeacons= false;

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
    public ServicioEscuharBeacons() {
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

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled());

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState());

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");

        this.elEscanner = bta.getBluetoothLeScanner();

        if (this.elEscanner == null) {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Socorro: NO hemos obtenido escaner btle  !!!!");

        }

    } // ()


    // --------------------------------------------------------------
    // --------------------------------------------------------------

    /**
     * Mostramos la informacion del resultado del escaneo
     * @param resultado Resultado del escaneo Bluetooth
     */

    private void mostrarInformacionDispositivoBTLE(ScanResult resultado) {

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
        Log.d(ETIQUETA_LOG, " rssi = " + rssi);

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
    public void parar() {

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() ");


        if (this.seguir == false) {
            return;
        }


        if (this.callbackDelEscaneo == null) {
            return;
        }

        //---------------------------------------------
        //---------------------------------------------
        //parar busqueda gps
        if(gpsTracker != null){
            if(gpsTracker.getIsGPSTrackingEnabled()){ //comprobar si esta activo
                gpsTracker.updateGPSCoordinates(); //actualizar localizacion
                gpsTracker.stopUsingGPS(); //detener busqueda
                Log.d("pepe", "GPSTracking DETENIDO --> STOP SUCCEFUL");
            }
        }
        //---------------------------------------------
        //---------------------------------------------

        this.elEscanner.stopScan(this.callbackDelEscaneo);
        this.callbackDelEscaneo = null;

        this.seguir = false;
        this.stopSelf();

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() : acaba ");

    }


    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public void onDestroy() {

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onDestroy() ");


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

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: empieza : thread=" + Thread.currentThread().getId());

        //init preferences
        SharedPreferences shared = getSharedPreferences(
                "com.example.jjpeajar.proyecto_3a_josejulio"
                , MODE_PRIVATE);

        //sacamos el id del device que tiene vinculado, sino lo pongo como -1
        int device_id = shared.getInt("id_device", -1);
        //sacamos el token del user registrado, si no existe es null
        String access_token = shared.getString("access_token", null);
        //sacamos el id del user
        int id_user = Integer.parseInt(shared.getString("user_id", null));

        //si detecta el beacon indicado ejecuta ->
        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult resultado) {
                super.onScanResult(callbackType, resultado);

                if (device_id != -1) { // si tiene un dispositivo vinculado

                    isReadBeacons = true;

                    // -----------------------------------------------------------------
                    //inicializar el gps par obtener la longitud y la latitud
                    //IMPORTANTE -> Al acceder a su info va a dar null, hay que llamar primero al metodo
                    // ---> gpsTracker.updateGPSCoordinates();
                    gpsTracker=  new GPSTracker(getApplicationContext());
                    // -----------------------------------------------------------------

                    //---------------------------------------------------------------
                    //---------------------------------------------------------------

                    Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanResult() ");
                    mostrarInformacionDispositivoBTLE(resultado);
                    byte[] bytes = resultado.getScanRecord().getBytes();
                    TramaIBeacon tramaIBeacon = new TramaIBeacon(bytes);

                    //rrsi
                    int rssi = resultado.getRssi();
                    //paso el TxPower a
                    int power = tib.getTxPower();

                    int data; // valor del gas
                    int numTipoDato = Utilidades.bytesToInt(tramaIBeacon.getMajor());
                    //tipo de medicion
                    String tipoDato;

                    //identificamos los beacons que nos llegan
                    if (numTipoDato == 11) { //co2
                        tipoDato = "CO2";
                        data = Utilidades.bytesToInt(tramaIBeacon.getMinor());
                        //---------------------------------------------------------------
                        // guardamos el beacon en la lista de beacons de ese gas
                        beaconsCO2.add(data);
                        //---------------------------------------------------------------

                    } else if (numTipoDato == 12) { //temp
                        tipoDato = "Temperatura";
                        data = Utilidades.bytesToInt(tramaIBeacon.getMinor());
                        //---------------------------------------------------------------
                        // guardamos el beacon en la lista de beacons de ese gas
                        beaconsTemp.add(data);
                        //---------------------------------------------------------------

                    } else if (numTipoDato == 10) { //humedad
                        tipoDato = "Humedad";
                        data = Utilidades.bytesToInt(tramaIBeacon.getMinor());
                        //---------------------------------------------------------------
                        // guardamos el beacon en la lista de beacons de ese gas
                        beaconsHum.add(data);
                        //---------------------------------------------------------------

                    } else {  // hijole el sensor esta dañado
                        tipoDato = "Otros";
                        data = Utilidades.bytesToInt(tramaIBeacon.getMinor());
                        //---------------------------------------------------------------
                        // sensor dañado , de mometo nada
                        //---------------------------------------------------------------

                    }

                    //si es alguna medicion
                    if(numTipoDato == 11 || numTipoDato == 10 || numTipoDato == 12){
                        //calculo la distancia
                        double distancia =  calculateDistance(power , rssi);
                        //guardo en la lista
                        beaconsDistancia.add(distancia);
                    }

                    // algoritmo para sacar la media y guardar la medicion
                    //---------------------------------------------------------------
                    //---------------------------------------------------------------

                    Log.d("pepe", "SIZE CO2  --> " + beaconsCO2.size());
                    //comprobamos si los beacons de un gas llegan al limite de beacons indicado
                    if(beaconsCO2.size() == 15){

                        //hacer sumatorio de los valores de CO2
                        int sumatorio=0; //sumatorio
                        double media=0; // media final
                        int tamanyo= beaconsCO2.size(); //size lista
                        //for que recorre cada Integer de la lista
                        for(Integer dato: beaconsCO2){
                            sumatorio= sumatorio + dato;    // sumatorio
                        }

                        //media de co2
                        media = (double) sumatorio / (double) tamanyo;
                        // 2 decimales
                        media=Math.round(media*100.0)/100.0;

                        Log.d("pepe", "MEDIA CO2  --> " + media);

                        //llamamos al metodo que rellena la medicion con los datos necesarios y
                        //llama a la logica
                        guardarMedicion(id_user , access_token, device_id, tipoDato, media);

                        //comprobar si hace falta lanzar notificacion
                        lanzarNotificacionCO2(id_user , access_token , media);

                        //limpiamos la lista
                        beaconsCO2.clear();
                        Log.d("pepe", "LIMPIAR LISTA CO2  --> " + beaconsCO2.size());
                    }

                    //---------------------------------------------------------------
                    //---------------------------------------------------------------

                    Log.d("pepe", "MEDIA TEMPERATURA  --> " + beaconsTemp.size());
                    //comprobamos si los beacons de un gas llegan al limite de beacons indicado
                    if(beaconsTemp.size() == 15){

                        //hacer sumatorio de los valores de temperatura
                        int sumatorio=0; //sumatorio
                        double media=0; // media final
                        int tamanyo= beaconsTemp.size(); //size lista
                        //for que recorre cada Integer de la lista
                        for(Integer dato: beaconsTemp){
                            sumatorio= sumatorio + dato;    // sumatorio
                        }

                        //media de co2
                        media = (double) sumatorio / (double) tamanyo;
                        // 2 decimales
                        media=Math.round(media*100.0)/100.0;

                        Log.d("pepe", "MEDIA TEMPERATURA  --> " + media);

                        //llamamos al metodo que rellena la medicion con los datos necesarios y
                        //llama a la logica
                        guardarMedicion(id_user , access_token, device_id, tipoDato, media);

                        //limpiamos la lista
                        beaconsTemp.clear();
                        Log.d("pepe", "LIMPIAR LISTA TEMPERATURA  --> " + beaconsTemp.size());
                    }
                    //---------------------------------------------------------------
                    //---------------------------------------------------------------

                    Log.d("pepe", "MEDIA HUMEDAD  --> " + beaconsHum.size());
                    //comprobamos si los beacons de un gas llegan al limite de beacons indicado
                    if(beaconsHum.size() == 15){

                        //hacer sumatorio de los valores de temperatura
                        int sumatorio=0; //sumatorio
                        double media=0; // media final
                        int tamanyo= beaconsHum.size(); //size lista
                        //for que recorre cada Integer de la lista
                        for(Integer dato: beaconsHum){
                            sumatorio= sumatorio + dato;    // sumatorio
                        }

                        //media de co2
                        media = (double) sumatorio / (double) tamanyo;
                        // 2 decimales
                        media=Math.round(media*100.0)/100.0;

                        Log.d("pepe", "MEDIA HUMEDAD  --> " + media);

                        //llamamos al metodo que rellena la medicion con los datos necesarios y
                        //llama a la logica
                        guardarMedicion(id_user , access_token, device_id, tipoDato, media);

                        //limpiamos la lista
                        beaconsHum.clear();

                        Log.d("pepe", "LIMPIAR LISTA HUMEDAD  --> " + beaconsHum.size());
                    }
                    //---------------------------------------------------------------
                    //---------------------------------------------------------------

                    Log.d("pepe", "MEDIA DISTANCIA  --> " + beaconsDistancia.size());
                    //comprobamos si los beacons de un gas llegan al limite de beacons indicado
                    if(beaconsDistancia.size() == 15){

                        //hacer sumatorio de los valores de temperatura
                        double sumatorio=0; //sumatorio
                        double media=0; // media final
                        int tamanyo= beaconsDistancia.size(); //size lista
                        //for que recorre cada Integer de la lista
                        for(double dato: beaconsDistancia){
                            sumatorio= sumatorio + dato;    // sumatorio
                            Log.d("pepe", "DATO DISTANCIA  --> " + dato);
                        }

                        //media de la distancia
                        media = sumatorio / (double) tamanyo;
                        // 2 decimales
                        media=Math.round(media*100.0)/100.0;

                        Log.d("pepe", "MEDIA DISTANCIA  --> " + media);

                        //almacenar distancia en los preferences
                        //guardar los valores del la distancia en las preferencias
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("media_distancia", String.valueOf(media));
                        editor.commit();

                        //limpiamos la lista
                        beaconsDistancia.clear();

                        Log.d("pepe", "LIMPIAR LISTA DISTANCIA  --> " + beaconsDistancia.size());
                    }

                }


            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onBatchScanResults() ");
                isReadBeacons = false;

            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanFailed() ");
                isReadBeacons = false;

            }
        };

        ScanFilter sf = new ScanFilter.Builder().setDeviceName(dispositivoBuscado).build();
        List<ScanFilter> filters = new ArrayList<>();
        ScanSettings.Builder scan = new ScanSettings.Builder();
        filters.add(sf);
        this.elEscanner.startScan(filters, scan.build(), callbackDelEscaneo);
        Log.d(ETIQUETA_LOG, "  servicioEscucharBeacons(): empezamos a escanear buscando: " + dispositivoBuscado);


        try {

            while (this.seguir) {
                Thread.sleep(tiempoDeEspera);
                Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: tras la espera:  " + contador);
                contador++;

                if(contador >= 5 && isReadBeacons == false){
                    //sensor dañado
                    lanzarNotificacionSensorRoto(id_user , access_token);
                }

            }

            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent : tarea terminada ( tras while(true) )");


        } catch (InterruptedException e) {
            // Restore interrupt status.
            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: problema con el thread");

            Thread.currentThread().interrupt();
        }

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: termina");

    }

    /**
     * Descripcion de lanzarNotificacionCO2. Funcion que comprueba si se necesita lanzar una notificacion
     *  y que tipo de notificacion
     *
     * @param id_user , id del usuario
     * @param access_token , token del usuario que ha iniciado sesion
     * @param media , media de la medicion
     *
     */

    private void lanzarNotificacionCO2(int id_user , String access_token , double media){
        String message = "";
        String tittle = "";
        String type = "";
        //datos erroneos
        if(media >= 800 || media <= 0){
            if(isErrorSensor == false){
                //saltar notificacion datos erroneos del sensor
                message= "El sensor emite datos erróneos o mal calibrados";
                tittle= "Datos erróneos";
                CrearNotification crearNotification = new CrearNotification(getApplicationContext());
                crearNotification.initNotificationChannel();
                crearNotification.initNotification(tittle , message);

                //call to logica method
                //callToLogicaToCrearNotificacion(id_user , tittle , message , access_token);

                isErrorSensor = true;
            }
        }else{  //los datos son correctos
            isErrorSensor= false;
            //actions
            //init la clase CrearNotificacion
            CrearNotification crearNotification = new CrearNotification(getApplicationContext());
            crearNotification.initNotificationChannel();
            //ifs de los umbrales
            //los bool son para no enviar 2 notificacion del mismo tipo
            Log.d("pepe", "NOTIFICACION UMBRAL" + media);

            if(media <= 35 && isInformation == false){
                //calidad de aire interior alta
                message= "La calidad de aire es buena";
                tittle= "Información";
                type= "Information";
                //crear notificacion
                crearNotification.initNotification(tittle , message);
                //reiniciar bools
                isInformation = true;
                isAlerta = false;
                isPeligro = false;
                isSensorRoto= false;
            }else if(media > 35 && media <= 75 && isAlerta == false){
                //calidad de aire interior moderada
                message= "La calidad de aire es moderada";
                tittle= "Alerta";
                type= "Warnning";
                crearNotification.initNotification(tittle , message);

                isAlerta = true;
                isInformation = false;
                isPeligro = false;
                isSensorRoto= false;
            }else if(media > 75 && media <= 185 && isAlerta == false){
                //calidad de aire interior baja
                message= "La calidad de aire es baja";
                tittle= "Alerta";
                type= "Warnning";
                crearNotification.initNotification(tittle , message);

                isAlerta = true;
                isInformation = false;
                isPeligro = false;
                isSensorRoto= false;
            }else if(media > 185 && isPeligro == false){
                //calidad de aire interior mala
                message= "La calidad de aire es muy mala";
                tittle= "Peligro";
                type= "Danger";
                crearNotification.initNotification(tittle , message);
                isPeligro = true;
                isInformation = false;
                isAlerta = false;
                isSensorRoto= false;
            }

            //call to logica method
            callToLogicaToCrearNotificacion(id_user , type , message , access_token);
        }
    }

    /**
     * Descripcion de lanzarNotificacionSensorRoto. Funcion que comprueba si se necesita lanzar una notificacion de sensor roto
     *
     * @param id_user , id del usuario
     * @param access_token , token del usuario que ha iniciado sesion
     *
     */
    private void lanzarNotificacionSensorRoto(int id_user , String access_token){
        String message = "";
        String tittle = "";
        String type = "";

        if(isErrorSensor == false){
            //actions
            CrearNotification crearNotification = new CrearNotification(getApplicationContext());
            crearNotification.initNotificationChannel();
            //sensor roto
            message= "Tiempo maximo alcanzado, sensor sin conexion";
            tittle= "Sensor sin conexión";
            type= "Device";
            crearNotification.initNotification(tittle , message);
            //call to logica method
            callToLogicaToCrearNotificacion(id_user , type , message , access_token);

            //parar busqueda de beacons
            onDestroy();
            //lanzar mensaje al user para que sepa que se ha detenido la busqueda

            isErrorSensor = true;
        }
    }

    /**
     * Descripcion de callToLogicaToCrearNotificacion. Funcion que llama a la logica de notificaciones
     *
     * @param user_id , id del usuario
     * @param type , tipo de notificacion
     * @param message , mensaje de la notificacion
     * @param access_token , token del usuario que ha iniciado sesion
     *
     */
    private void callToLogicaToCrearNotificacion(int user_id , String type, String message, String access_token) {

        Log.d("pepe", " DENGUEEEEEEEEEEEEEEEEEEEEEEEEEEEEE -------------------------------------  ");
        Log.d("pepe", "  CUERPO -> " + user_id + " " + message + " " + type);
        //llamar a la logica
        LogicaNegocioNotification logicaNegocioNotification = new LogicaNegocioNotification();
        logicaNegocioNotification
                .crearNotificacion(access_token, user_id, message, type, new LogicaNegocioNotification.CrearNotificacionCallback() {
                    @Override
                    public void onCompletedCrearNotificacionCallback(NotificationController notificationController) {
                    }

                    @Override
                    public void onFailedCrearNotificacionCallback(boolean res) {

                    }
                });

    }

    /**
     * Descripcion de guardarMedicion. Funcion que llama a la logica de medicion
     *
     * @param id_user , id del usuario
     * @param access_token , token del usuario que ha iniciado sesion
     * @param device_id , id del dispositivo
     * @param tipo , tipo de medicion
     * @param media , media de la medicion
     *
     */
    private void guardarMedicion(int id_user , String access_token , int device_id , String tipo , double media ){
        Log.d("pepe", "guardarMedicion  ->" +"ENTROOO");

        //obtener la latitud y la longitud
        if(obtenerLocalizacionActual()){ // si se ha obtenido la latitud y longitud
            Log.d("pepe", "obtenerLocalizacionActual()  ->" +"ENTROOO y da TRUEEE");
            //llamar a la logica

            logicaNegocio.publicarMedicion(access_token, id_user, device_id, media, latitud, longitud, tipo, new LogicaNegocioMediciones.PublicarMedicionesCallback() {
                @Override
                public void onCompletedPublicarMediciones(int success) {
                    Log.d("pepe", "publicarMedicion()  ->" +"resultado -> " + success);
                }

                @Override
                public void onFailedPublicarMediciones(boolean res) {
                    Log.d("pepe", "publicarMedicion()  ->" +"resultado FALLIDO -> " + res);

                }
            });

        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Asegúrese de activar el gps", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
            toast.show(); // display the Toast
        }

    }

    /**
     * Descripcion de obtenerLocalizacionActual. Funcion que consigue los datos del gps del usario
     * @return boolean , bool que define si se ha podido localizar o no
     *
     */
    private boolean obtenerLocalizacionActual() {
        Log.d("pepe", "GPS  ->" +"se llama");

        //si tiene los permisos necesarios
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //obtener las coordenadas mas recientes
            gpsTracker.updateGPSCoordinates();
            //sacar lat y long llamando al getter
            latitud=gpsTracker.getLatitude();
            longitud=gpsTracker.getLongitude();

            Log.d("pepe", "GPS  ->" +latitud +" " + longitud );

            return true;
        }
        return false;
    }

    // Usa esto para medir la distancia
    /**
     * Descripcion de obtenerLocalizacionActual. Funcion que medir la distancia del sensor a la aplicacion
     * @return double , distancia del sensor
     *
     */
    public static double calculateDistance(int txPower,int rssi){
        // La señal vale el valor absoluto.
        int absRssi = Math.abs(rssi);

        double power = (absRssi - txPower)/(10 * 4.0);
        return Math.pow(10,power);
    }

} // class
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
