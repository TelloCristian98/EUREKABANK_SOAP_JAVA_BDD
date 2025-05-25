/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.cliente;

import ec.edu.monster.vistas.LoginView;

/**
 *
 * @author Juanr
 */
public class WSEurekaEscritorio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Crear y mostrar la ventana de LoginVista
        java.awt.EventQueue.invokeLater(() -> {
            LoginView loginview = new LoginView();
            loginview.setVisible(true);
        });
    }
}
