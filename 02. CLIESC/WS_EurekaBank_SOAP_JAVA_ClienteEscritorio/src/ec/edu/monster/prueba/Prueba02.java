
package ec.edu.monster.prueba;

import ec.edu.monster.servicio.EurekaService;
import java.util.List;
import wseurekaclient.Movimiento;

public class Prueba02 {
    public static void main(String[] args){
        try{
            String cuenta="00100001";
            EurekaService service=new EurekaService();
            List<Movimiento> lista=service.traerMovimientos(cuenta);
            for(Movimiento r:lista){
                System.out.println(r.getNromov()+" - " + r.getAccion() + " - "+r.getImporte());
            }     
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
