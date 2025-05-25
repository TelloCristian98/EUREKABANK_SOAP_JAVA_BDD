package ec.edu.monster.prueba;

import ec.edu.monster.db.AccesoDB;
import java.sql.Connection;


public class PruebaConexionBD {
    
    public static void main(String[] args) {
        try {
            Connection cn = AccesoDB.getConnection();
            System.out.println("OK");
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
