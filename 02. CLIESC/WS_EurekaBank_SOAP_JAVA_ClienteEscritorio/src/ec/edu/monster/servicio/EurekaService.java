package ec.edu.monster.servicio;

public class EurekaService {

    public int regDeposito(String cuenta, double importe) {
        wseurekaclient.WSEureka_Service service = new wseurekaclient.WSEureka_Service();
        wseurekaclient.WSEureka port = service.getWSEurekaPort();
        return port.regDeposito(cuenta, importe);
    }

    public java.util.List<wseurekaclient.Movimiento> traerMovimientos(String cuenta) {
        wseurekaclient.WSEureka_Service service = new wseurekaclient.WSEureka_Service();
        wseurekaclient.WSEureka port = service.getWSEurekaPort();
        return port.traerMovimientos(cuenta);
    }
   
}
