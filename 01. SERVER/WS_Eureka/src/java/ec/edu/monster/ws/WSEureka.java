/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.ws;

import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.servicio.EurekaService;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

/**
 *
 * @author DELL
 */
@WebService(serviceName = "WSEureka")
public class WSEureka {

    /**
     * Web service operation
     * @param cuenta
     * @return Retorna la lista de movimientos de la cuenta
     */
    @WebMethod(operationName = "traerMovimientos")
    @WebResult(name = "movimiento")
    public List<Movimiento> traerMovimientos(@WebParam(name = "cuenta") String cuenta) {
        List<Movimiento> lista;
        try {
            // Recuperar movimientos
            EurekaService service = new EurekaService();
            lista = service.leerMovimientos(cuenta);
        } catch (Exception e) {
            // En caso de error, retorna una lista vacía
            lista = new ArrayList<>();
        }
        return lista;
    }

    /**
     * Web service operation
     * @param cuenta
     * @param importe
     * @return Estado 1 o -1
     */
    @WebMethod(operationName = "regDeposito")
    @WebResult(name = "estado")
    public int regDeposito(@WebParam(name = "cuenta") String cuenta, @WebParam(name = "importe") double importe) {
        int estado;
        String codEmp = "0001";
        try {
            EurekaService service = new EurekaService();
            service.registrarDepósito(cuenta, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            estado = -1;
        }
        return estado;
    }

    /**
     * Web service operation
     * @param usuario
     * @param contrasena
     * @return Retorna true si las credenciales son válidas, false en caso contrario
     */
    @WebMethod(operationName = "login")
    @WebResult(name = "resultadoLogin")
    public boolean login(@WebParam(name = "usuario") String usuario, @WebParam(name = "contrasena") String contrasena) {
        final String USUARIO_VALIDO = "Monster";
        final String CONTRASENA_VALIDA = "Monster9";
        return USUARIO_VALIDO.equals(usuario) && CONTRASENA_VALIDA.equals(contrasena);
    }

    /**
     * Web service operation
     * @param cuenta
     * @param importe
     * @return Estado 1 o -1
     */
    @WebMethod(operationName = "regRetiro")
    @WebResult(name = "estado")
    public int regRetiro(@WebParam(name = "cuenta") String cuenta, @WebParam(name = "importe") double importe) {
        int estado;
        String codEmp = "0001";
        try {
            EurekaService service = new EurekaService();
            service.registrarRetiro(cuenta, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            estado = -1;
        }
        return estado;
    }

    /**
     * Web service operation
     * @param cuentaOrigen
     * @param cuentaDestino
     * @param importe
     * @return Estado 1 o -1
     */
    @WebMethod(operationName = "regTransferencia")
    @WebResult(name = "estado")
    public int regTransferencia(@WebParam(name = "cuentaOrigen") String cuentaOrigen, 
                                 @WebParam(name = "cuentaDestino") String cuentaDestino, 
                                 @WebParam(name = "importe") double importe) {
        int estado;
        String codEmp = "0002";
        try {
            EurekaService service = new EurekaService();
            service.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            estado = -1;
        }
        return estado;
    }
}