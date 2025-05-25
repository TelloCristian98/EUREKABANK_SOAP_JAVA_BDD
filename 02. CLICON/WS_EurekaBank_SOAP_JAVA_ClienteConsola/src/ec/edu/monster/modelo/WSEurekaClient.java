/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.modelo;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.util.List;

public class WSEurekaClient {
    private final ec.edu.monster.ws.WSEureka port;

    public WSEurekaClient() throws Exception {
        URL url = new URL("http://localhost:8080/WS_Eureka/WSEureka?wsdl");
        QName qname = new QName("http://ws.monster.edu.ec/", "WSEureka");
        Service service = Service.create(url, qname);
        port = service.getPort(ec.edu.monster.ws.WSEureka.class);
    }

    public boolean login(String usuario, String contrasena) {
        return port.login(usuario, contrasena);
    }

    public List<ec.edu.monster.ws.Movimiento> traerMovimientos(String cuenta) {
        return port.traerMovimientos(cuenta);
    }

    public int regDeposito(String cuenta, double importe) {
        return port.regDeposito(cuenta, importe);
    }
}