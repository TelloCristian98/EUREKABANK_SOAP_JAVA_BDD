/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws_eurekabank_soap_java_clienteconsola;

/**
 *
 * @author Juanr
 */
import ec.edu.monster.controlador.EurekaControlador;

public class WSEurekaConsola {
    public static void main(String[] args) {
        try {
            new EurekaControlador().iniciar();
        } catch (Exception e) {
            System.err.println("Error al iniciar el sistema: " + e.getMessage());
        }
    }
}
