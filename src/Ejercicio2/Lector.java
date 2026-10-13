package Ejercicio2;

import org.iesalandalus.programacion.utilidades.Entrada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Proceso padre: pide un número al usuario, lanza el proceso Escritor,
 * le envía el número por su entrada estándar y muestra por pantalla
 * los 10 números siguientes que devuelve el Escritor.
 */

public class Lector {
    public static void main(String[] args) {

        // Pedimos el número al usuario con la clase Entrada
        System.out.println("Introduce un número: ");
        int numero = Entrada.entero();

        // Preparamos el proceso hijo indicando dónde están las clases compiladas
        String rutaClases = System.getProperty("java.class.path");
        ProcessBuilder constructorProceso = new ProcessBuilder("java", "-cp", rutaClases, Escritor.class.getName());

        try {
            Process procesoEscritor = constructorProceso.start();

            // Enviamos el número al hijo a través de su entrada estándar
            PrintStream entradaHijo = new PrintStream(procesoEscritor.getOutputStream());
            entradaHijo.println(numero);
            entradaHijo.flush();
            entradaHijo.close();

            // Leemos lo que el hijo escribe en su salida estándar y lo mostramos
            BufferedReader salidaHijo = new BufferedReader(new InputStreamReader(procesoEscritor.getInputStream()));
            String linea;
            while ((linea = salidaHijo.readLine()) != null) {
                System.out.println(linea);
            }
            salidaHijo.close();

            // Leemos los posibles errores del hijo y los mostramos
            BufferedReader erroresHijo = new BufferedReader(new InputStreamReader(procesoEscritor.getErrorStream()));
            while ((linea = erroresHijo.readLine()) != null) {
                System.err.println("Error del proceso hijo: " + linea);
            }
            erroresHijo.close();

            // Esperamos a que el hijo termine y comprobamos cómo ha finalizado
            int codigoSalida = procesoEscritor.waitFor();
            if (codigoSalida != 0) {
                System.err.println("El proceso Escritor ha finalizado con errores.");
            }

        } catch (IOException e) {
            System.err.println("Error al lanzar o comunicarse con el proceso Escritor.");
        } catch (InterruptedException e) {
            System.err.println("Se ha interrumpido la espera del proceso Escritor.");
        }
    }
}
