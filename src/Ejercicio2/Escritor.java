package Ejercicio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Proceso hijo: recibe un número por su entrada estándar
 * y muestra por su salida estándar los 10 números siguientes.
 */

public class Escritor {

    public static void main(String[] args) {

        // Preparamos la lectura de la entrada estándar
        BufferedReader entradaEstandar = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Leemos el número recibido y lo convertimos a entero
            String lineaRecibida = entradaEstandar.readLine();
            int numeroRecibido = Integer.parseInt(lineaRecibida);

            // Mostramos los 10 números siguientes al recibido
            for (int i = numeroRecibido + 1; i <= numeroRecibido + 10; i++) {
                System.out.println("Número: " + i);
            }

            entradaEstandar.close();

        } catch (NumberFormatException e) {
            System.err.println("El dato recibido no es un número entero válido.");
        } catch (IOException ie) {
            System.err.println("Error al leer la entrada: " + ie.getMessage());
        }
    }
}

