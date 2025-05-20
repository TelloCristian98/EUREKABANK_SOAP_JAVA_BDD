package ec.edu.monster.ws;

import ec.edu.monster.controlador.Login;
import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.servicio.EurekaService;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

@WebService(serviceName = "WSEureka")
public class WSEureka {
    @WebMethod(operationName = "traerMovimientos")
    @WebResult(name = "movimiento")
    public List<Movimiento> traerMovimientos(@WebParam(name = "cuenta") String cuenta) {
        List<Movimiento> lista;
        try {
            EurekaService service = new EurekaService();
            lista = service.leerMovimientos(cuenta);
        } catch (Exception e) {
            lista = new ArrayList<>();
        }
        return lista;
    }

    @WebMethod(operationName = "regDeposito")
    @WebResult(name = "resultado")
    public int regDeposito(@WebParam(name = "cuenta") String cuenta, @WebParam(name = "importe") double importe) {
        if (cuenta == null || cuenta.isEmpty() || importe <= 0) return -1;
        String codEmp = "0001";
        try {
            EurekaService service = new EurekaService();
            service.registrarDeposito(cuenta, importe, codEmp);
            return 1;
        } catch (Exception e) {
            System.err.println("Error al registrar el depósito: " + e.getMessage());
            return -1;
        }
    }

    @WebMethod(operationName = "login")
    @WebResult(name = "login")
    public int iniciarSesion(@WebParam(name = "usuario") String usuario, @WebParam(name = "contrasena") String contrasena) {
        try {
            Login service = new Login();
            return service.IniciarSesion(usuario, contrasena) ? 1 : -1;
        } catch (Exception e) {
            System.err.println("Error al iniciar sesión: " + e.getMessage());
            return -1;
        }
    }
}