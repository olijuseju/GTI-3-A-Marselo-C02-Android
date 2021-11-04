package com.example.jjpeajar.proyecto_3a_josejulio;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

// --------------------------------------------------------------
/**
 * @author Jose Julio Peñaranda
 * 2021-10-14
 */
// --------------------------------------------------------------
public class Utilidades {
    // -------------------------------------------------------------------------------
    /**
     * Este método recibe un string de texto y devuelve una lista de bytes, a modo de utilidad
     *
     * @param {String} texto - Texto a convertir.
     *
     * @returns {Lista<Byte></>} Lista de bytes transformada.
     */
    // -------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public static byte[] stringToBytes ( String texto ) {
        return texto.getBytes();
        // byte[] b = string.getBytes(StandardCharsets.UTF_8); // Ja
    } // ()

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------

    /**
     * Este método recibe un string de texto y devuelve un UUID, a modo de utilidad
     *
     * @param uuid Texto a convertir
     * @return {UUID} res texto convertido en UUID
     */
    public static UUID stringToUUID( String uuid ) {
        if ( uuid.length() != 16 ) {
            throw new Error( "stringUUID: string no tiene 16 caracteres ");
        }
        byte[] comoBytes = uuid.getBytes();

        String masSignificativo = uuid.substring(0, 8);
        String menosSignificativo = uuid.substring(8, 16);
        UUID res = new UUID( Utilidades.bytesToLong( masSignificativo.getBytes() ), Utilidades.bytesToLong( menosSignificativo.getBytes() ) );

        // Log.d( MainActivity.ETIQUETA_LOG, " \n\n***** stringToUUID *** " + uuid  + "=?=" + Utilidades.uuidToString( res ) );

        // UUID res = UUID.nameUUIDFromBytes( comoBytes ); no va como quiero

        return res;
    } // ()

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------

    /**
     * Este método recibe un UUID y devuelve un texto, a modo de utilidad
     *
     * @param uuid UUID a convertir
     * @return {String} texto convertido
     */
    public static String uuidToString ( UUID uuid ) {
        return bytesToString( dosLongToBytes( uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() ) );
    } // ()

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------


    /**
     * Este método recibe un UUID y devuelve un texto, a modo de utilidad
     *
     * @param uuid UUID a convertir
     * @return {HexString} texto convertido
     */
    public static String uuidToHexString ( UUID uuid ) {
        return bytesToHexString( dosLongToBytes( uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() ) );
    } // ()

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------

    /**
     * Este método recibe un array de bytes y devuelve una String, a modo de utilidad
     *
     * @param {Lista<Byte>} bytes - Lista de bytes  a convertir.
     *
     * @returns {String} Texto convertido.
     */
    public static String bytesToString( byte[] bytes ) {
        if (bytes == null ) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append( (char) b );
        }
        return sb.toString();
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /**
     * Este método recibe dos numeros long que son los bytes menos y mas significativo
     * y los convierte en una lista de bytes, a modo de utilidad
     *
     * @param masSignificativos - Lista de bytes  a convertir.
     * @param menosSignificativos - Lista de bytes  a convertir.
     *
     * @returns {Lista<Byte>} Lista de bytes transformada.
     */
    public static byte[] dosLongToBytes( long masSignificativos, long menosSignificativos ) {
        ByteBuffer buffer = ByteBuffer.allocate( 2 * Long.BYTES );
        buffer.putLong( masSignificativos );
        buffer.putLong( menosSignificativos );
        return buffer.array();
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /**
     * Este método recibe una lista de bytes y devuelve su valor como un numero entero,
     * a modo de utilidad
     *
     * @param bytes Lista de bytes  a convertir.
     *
     * @returns {int} Valor  valor numérico.
     */
    public static int bytesToInt( byte[] bytes ) {
        return new BigInteger(bytes).intValue();
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /**
     * Este método recibe una lista de bytes y devuelve su valor como un numero long,
     * a modo de utilidad
     *
     * @param bytes Lista de bytes  a convertir.
     *
     * @returns {long} Valor  valor numérico en long.
     */
    public static long bytesToLong( byte[] bytes ) {
        return new BigInteger(bytes).longValue();
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /**
     * Este método recibe una lista de bytes y devuelve su valor como un numero entero,
     * a modo de utilidad
     *
     * @param bytes Lista de bytes  a convertir.
     *
     * @returns {int} Valor  valor numérico.
     */
    public static int bytesToIntOK( byte[] bytes ) {
        if (bytes == null ) {
            return 0;
        }

        if ( bytes.length > 4 ) {
            throw new Error( "demasiados bytes para pasar a int ");
        }
        int res = 0;



        for( byte b : bytes ) {
           /*
           Log.d( MainActivity.ETIQUETA_LOG, "bytesToInt(): byte: hex=" + Integer.toHexString( b )
                   + " dec=" + b + " bin=" + Integer.toBinaryString( b ) +
                   " hex=" + Byte.toString( b )
           );
           */
            res =  (res << 8) // * 16
                    + (b & 0xFF); // para quedarse con 1 byte (2 cuartetos) de lo que haya en b
        } // for

        if ( (bytes[ 0 ] & 0x8) != 0 ) {
            // si tiene signo negativo (un 1 a la izquierda del primer byte
            res = -(~(byte)res)-1; // complemento a 2 (~) de res pero como byte, -1
        }
       /*
        Log.d( MainActivity.ETIQUETA_LOG, "bytesToInt(): res = " + res + " ~res=" + (res ^ 0xffff)
                + "~res=" + ~((byte) res)
        );
        */

        return res;
    } // ()

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /**
     * Este método recibe una lista de bytes y devuelve su valor como una String hexadecimal,
     * a modo de utilidad
     *
     * @param {Lista<Byte>} bytes Lista de bytes  a convertir.
     *
     * @returns {HexString} Valor Texto HexString transformado.
     */
    public static String bytesToHexString( byte[] bytes ) {

        if (bytes == null ) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
            sb.append(':');
        }
        return sb.toString();
    } // ()
} // class
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------


