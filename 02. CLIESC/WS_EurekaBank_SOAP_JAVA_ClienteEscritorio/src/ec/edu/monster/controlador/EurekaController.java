package ec.edu.monster.controlador;

import ec.edu.monster.servicio.EurekaService;
import java.util.List;
import wseurekaclient.Movimiento;


public class EurekaController {
    public List<Movimiento> traerMovimientos(String cuenta) {
        EurekaService service = new EurekaService();
        return service.traerMovimientos(cuenta);
    }

    public int regDepósito(String cuenta, double importe) {
        EurekaService service = new EurekaService();
        return service.regDeposito(cuenta, importe);
    }
}

