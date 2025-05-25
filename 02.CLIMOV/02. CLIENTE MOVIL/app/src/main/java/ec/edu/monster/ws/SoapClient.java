package ec.edu.monster.ws;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import ec.edu.monster.modelo.Movimiento;

public class SoapClient {

    private static final String NAMESPACE = "http://ws.monster.edu.ec/";
    private static final String URL = "http://192.168.100.23:8080/WS_Eureka/WSEureka?wsdl";

    private static final String METHOD_NAME_TRAER_MOVIMIENTOS = "traerMovimientos";
    private static final String METHOD_NAME_REG_DEPOSITO = "regDeposito";

    // Nuevos métodos
    private static final String METHOD_NAME_REG_RETIRO = "regRetiro";
    private static final String METHOD_NAME_REG_TRANSFERENCIA = "regTransferencia";

    // Acciones SOAP
    private static final String SOAP_ACTION_TRAER_MOVIMIENTOS = NAMESPACE + METHOD_NAME_TRAER_MOVIMIENTOS;
    private static final String SOAP_ACTION_REG_DEPOSITO = NAMESPACE + METHOD_NAME_REG_DEPOSITO;
    private static final String SOAP_ACTION_REG_RETIRO = NAMESPACE + METHOD_NAME_REG_RETIRO;
    private static final String SOAP_ACTION_REG_TRANSFERENCIA = NAMESPACE + METHOD_NAME_REG_TRANSFERENCIA;

    public static List<Movimiento> traerMovimientos(String cuenta) throws Exception {
        List<Movimiento> movimientos = new ArrayList<>();

        // Crear la solicitud SOAP
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_TRAER_MOVIMIENTOS);
        request.addProperty("cuenta", cuenta);

        // Configurar el sobre SOAP
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        // Configurar el transporte
        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        try {
            // Llamar al servicio
            httpTransport.call(SOAP_ACTION_TRAER_MOVIMIENTOS, envelope);

            // Procesar la respuesta
            Object response = envelope.getResponse();

            if (response instanceof Vector) {
                Vector<?> responseVector = (Vector<?>) response;
                for (Object obj : responseVector) {
                    if (obj instanceof SoapObject) {
                        movimientos.add(parseMovimiento((SoapObject) obj));
                    }
                }
            } else if (response instanceof SoapObject) {
                movimientos.add(parseMovimiento((SoapObject) response));
            }

        } catch (Exception e) {
            throw new Exception("Error al obtener movimientos: " + e.getMessage(), e);
        }

        return movimientos;
    }
    public static boolean regDeposito(String cuenta, double importe) throws Exception {
        // Crear la solicitud SOAP para registrar un depósito
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_REG_DEPOSITO);
        request.addProperty("cuenta", cuenta);

        // Convertir el importe a String antes de agregarlo a la solicitud
        request.addProperty("importe", String.valueOf(importe));

        // Configurar el sobre SOAP
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        // Configurar el transporte
        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        try {
            // Llamar al servicio para registrar el depósito
            httpTransport.call(SOAP_ACTION_REG_DEPOSITO, envelope);

            // Procesar la respuesta del servicio
            Object response = envelope.getResponse();
            Log.d("SoapClient", "Response: " + response.toString());

            if (response instanceof SoapPrimitive) {
                int estado = Integer.parseInt(response.toString());
                Log.d("SoapClient", "Estado: " + estado);
                return estado == 1;  // Si el estado es 1, la operación fue exitosa
            } else {
                Log.d("SoapClient", "Unexpected response type: " + response.getClass().getName());
            }
        } catch (Exception e) {
            Log.e("SoapClient", "Error al registrar el depósito: " + e.getMessage(), e);
            throw new Exception("Error al registrar el depósito: " + e.getMessage(), e);
        }

        return false;
    }

    // Método para registrar un retiro
    public static boolean regRetiro(String cuenta, double importe) throws Exception {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_REG_RETIRO);
        request.addProperty("cuenta", cuenta);
        request.addProperty("importe", String.valueOf(importe));

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        try {
            httpTransport.call(SOAP_ACTION_REG_RETIRO, envelope);
            Object response = envelope.getResponse();

            if (response instanceof SoapPrimitive) {
                int estado = Integer.parseInt(response.toString());
                return estado == 1;
            }
        } catch (Exception e) {
            Log.e("SoapClient", "Error al registrar el retiro: " + e.getMessage(), e);
            throw new Exception("Error al registrar el retiro: " + e.getMessage(), e);
        }
        return false;
    }

    // Método para registrar una transferencia
    public static boolean regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) throws Exception {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_REG_TRANSFERENCIA);
        request.addProperty("cuentaOrigen", cuentaOrigen);
        request.addProperty("cuentaDestino", cuentaDestino);
        request.addProperty("importe", String.valueOf(importe));

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        try {
            httpTransport.call(SOAP_ACTION_REG_TRANSFERENCIA, envelope);
            Object response = envelope.getResponse();

            if (response instanceof SoapPrimitive) {
                int estado = Integer.parseInt(response.toString());
                return estado == 1;
            }
        } catch (Exception e) {
            Log.e("SoapClient", "Error al registrar la transferencia: " + e.getMessage(), e);
            throw new Exception("Error al registrar la transferencia: " + e.getMessage(), e);
        }
        return false;
    }

    private static Movimiento parseMovimiento(SoapObject soapObject) {
        Movimiento movimiento = new Movimiento();

        movimiento.setCuenta(soapObject.getProperty("cuenta").toString());
        movimiento.setTipo(soapObject.getProperty("tipo").toString());
        movimiento.setAccion(soapObject.getProperty("accion").toString());
        movimiento.setImporte(Double.parseDouble(soapObject.getProperty("importe").toString()));

        // Convertir la fecha si está presente
        if (soapObject.hasProperty("fecha")) {
            String fechaString = soapObject.getProperty("fecha").toString();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                movimiento.setFecha(sdf.parse(fechaString));
            } catch (Exception e) {
                movimiento.setFecha(null); // Manejo básico en caso de error
            }
        }

        return movimiento;
    }
}
