package ec.edu.monster.prueba;

import ec.edu.monster.servicio.EurekaService;

public class PruebaDeposito {
    
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            //datos
            String cuenta ="00100001";
            double importe = 200;
            String codEmp = "0001";
            //proceso
            EurekaService service = new EurekaService();
            service.registrarDepósito(cuenta, importe, codEmp);
            System.out.println("Proceso ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
