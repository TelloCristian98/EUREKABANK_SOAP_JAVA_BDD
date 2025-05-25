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
 * @author Juanr
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
            //recuperar movimientos
            EurekaService service =new EurekaService();
            lista = service.leerMovimientos(cuenta);
        } catch (Exception e){
            //en caso de error retorne una lista vacia
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
        //TODO write your implementation code here:
        int estado;
        
        //proceso
        String codEmp = "0001";
        try {
            EurekaService service = new EurekaService();
            service.registrarDepósito(cuenta, importe, codEmp);
            estado = 1;
        } catch (Exception e){
            estado = -1;
        }
        
        //retorno
        return estado;
    }
}
