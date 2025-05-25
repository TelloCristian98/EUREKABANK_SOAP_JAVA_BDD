/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.pruebas;

import ec.edu.monster.modelo.WSEurekaClient;
import ec.edu.monster.ws.Movimiento;
import java.util.List;

public class Pruebas {
    public static void main(String[] args) {
        try {
            // Crear una instancia del cliente
            WSEurekaClient cliente = new WSEurekaClient();

            // Prueba de Login
            System.out.println("Prueba de Login:");
            String usuario = "Monster";
            String contrasena = "Monster9";
            boolean loginExitoso = cliente.login(usuario, contrasena);
            System.out.println("Login con usuario '" + usuario + "': " + (loginExitoso ? "Exitoso" : "Fallido"));

            // Prueba de consulta de movimientos
            System.out.println("\nPrueba de Consulta de Movimientos:");
            String cuenta = "00100001";
            List<Movimiento> movimientos = cliente.traerMovimientos(cuenta);
            if (movimientos != null && !movimientos.isEmpty()) {
                System.out.println("Movimientos encontrados para la cuenta " + cuenta + ":");
                movimientos.forEach(mov -> System.out.printf("Fecha: %s | Tipo: %s | Acción: %s | Importe: %.2f%n",
                        mov.getFecha(), mov.getTipo(), mov.getAccion(), mov.getImporte()));
            } else {
                System.out.println("No se encontraron movimientos para la cuenta " + cuenta + ".");
            }

            // Prueba de registro de depósito
            System.out.println("\nPrueba de Registro de Depósito:");
            double importe = 150.75;
            int estadoDeposito = cliente.regDeposito(cuenta, importe);
            System.out.println("Intentando registrar un depósito de " + importe + " en la cuenta " + cuenta + ": " +
                    (estadoDeposito == 1 ? "Exitoso" : "Fallido"));

            // Prueba de validación de formato decimal
            System.out.println("\nPrueba de Validación de Decimales:");
            String[] testDecimales = {"123.45", "678,90", "invalid", "123"};
            for (String input : testDecimales) {
                try {
                    double valor = Double.parseDouble(input.replace(",", "."));
                    System.out.println("Entrada '" + input + "' válida como decimal: " + valor);
                } catch (NumberFormatException e) {
                    System.out.println("Entrada '" + input + "' no válida como decimal.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error durante las pruebas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

