/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.vista;

import java.util.Scanner;

public class ConsolaVista {
    private final Scanner scanner = new Scanner(System.in);

    public String solicitarTexto(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine().trim();
    }

    public double solicitarDecimal(String mensaje) {
        while (true) {
            try {
                System.out.println(mensaje);
                String input = scanner.nextLine().trim().replace(",", ".");
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número válido.");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarMenu() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Consultar movimientos");
        System.out.println("2. Registrar depósito");
        System.out.println("3. Salir");
    }

    public int solicitarOpcion() {
        while (true) {
            try {
                System.out.println("Ingrese su opción:");
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 3) {
                    return opcion;
                } else {
                    System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente de nuevo.");
            }
        }
    }
}

