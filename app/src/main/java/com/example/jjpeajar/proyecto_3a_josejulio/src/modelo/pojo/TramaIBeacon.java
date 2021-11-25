package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

import java.io.Serializable;
import java.util.Arrays;

// --------------------------------------------------------------
/**
 * @author Jose Julio Peñaranda
 * TramaIBeacon
 * 2021-10-14
 */
// --------------------------------------------------------------
public class TramaIBeacon implements Serializable {
    private byte[] prefijo = null; // 9 bytes
    private byte[] uuid = null; // 16 bytes
    private byte[] major = null; // 2 bytes
    private byte[] minor = null; // 2 bytes
    private byte txPower = 0; // 1 byte

    private byte[] losBytes;

    private byte[] advFlags = null; // 3 bytes
    private byte[] advHeader = null; // 2 bytes
    private byte[] companyID = new byte[2]; // 2 bytes
    private byte iBeaconType = 0 ; // 1 byte
    private byte iBeaconLength = 0 ; // 1 byte

    /**
     * GETTERS Y SETTERS
     */

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte[] getPrefijo() {
        return prefijo;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte[] getUUID() {
        return uuid;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte[] getMajor() {
        return major;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte[] getMinor() {
        return minor;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte getTxPower() {
        return txPower;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte[] getLosBytes() {
        return losBytes;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte[] getAdvFlags() {
        return advFlags;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte[] getAdvHeader() {
        return advHeader;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte[] getCompanyID() {
        return companyID;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte getiBeaconType() {
        return iBeaconType;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public byte getiBeaconLength() {
        return iBeaconLength;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------

    /**
     * Constructor de la clase. Segmentamos el array bytes
     *
     * @param bytes bytes a segmentar
     */

    public TramaIBeacon(byte[] bytes ) {
        this.losBytes = bytes;

        prefijo = Arrays.copyOfRange(losBytes, 0, 8+1 ); // 9 bytes
        uuid = Arrays.copyOfRange(losBytes, 9, 24+1 ); // 16 bytes
        major = Arrays.copyOfRange(losBytes, 25, 26+1 ); // 2 bytes
        minor = Arrays.copyOfRange(losBytes, 27, 28+1 ); // 2 bytes
        txPower = losBytes[ 29 ]; // 1 byte

        advFlags = Arrays.copyOfRange( prefijo, 0, 2+1 ); // 3 bytes
        advHeader = Arrays.copyOfRange( prefijo, 3, 4+1 ); // 2 bytes
        companyID = Arrays.copyOfRange( prefijo, 5, 6+1 ); // 2 bytes
        iBeaconType = prefijo[ 7 ]; // 1 byte
        iBeaconLength = prefijo[ 8 ]; // 1 byte

    } // ()
} // class
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
