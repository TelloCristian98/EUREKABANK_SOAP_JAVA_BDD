package ec.edu.monster.prueba;

import ec.edu.monster.servicio.EurekaService;

public class Prueba03 {
    public static void main(String[] args){
        try{
            String cuenta="00100001";
            double importe=200;
            String codEmp="0001";
            EurekaService service=new EurekaService();
            service.regDeposito(cuenta, importe);
            System.out.println("Proceso ok");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

