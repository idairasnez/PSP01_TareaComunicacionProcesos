package Ejercicio1;

import java.io.*;

public class Tuberia {

    /**
     * Ejecutará dos comandos MS-DOS, cada uno en un proceso hijo con sus argumentos.
     * La salida estándar del primero será la entrada estándar del segundo.
     * El proceso padre mostrará por pantalla el resultado del segundo hijo
     * y, al terminar, el mensaje "Finalizada la ejecución".
     */

    public static void main(String[] args) {
        Process proceso1;
        Process proceso2;
        String palabraABuscar = "src";

        try {
            // Lanzamos los dos procesos
            proceso1 = new ProcessBuilder("CMD", "/C", "DIR").start();
            proceso2 = new ProcessBuilder("CMD", "/C", "FINDSTR", "/I", palabraABuscar).start();
            InputStream salidaHijo1 = proceso1.getInputStream();
            OutputStream entradaHijo2 = proceso2.getOutputStream();

            // Creamos el lector de la SALIDA del hijo 1

            BufferedReader lectorSalida1 = new BufferedReader(new InputStreamReader(salidaHijo1));
            String linea;

            // Creamos el escritor hacia la ENTRADA del hijo 2
            BufferedWriter escritorHijo2 = new BufferedWriter(new OutputStreamWriter(entradaHijo2));

            // Bucle: leemos cada línea del hijo 1 y la escribimos en el hijo 2
            while ((linea = lectorSalida1.readLine()) != null) {
                escritorHijo2.write(linea);
                escritorHijo2.newLine();
            }

            // Cerramos el lector del hijo 1 y el escritor del hijo 2
            lectorSalida1.close();
            escritorHijo2.close();

            // Leemos la salida del hijo 2 y la vamos a mostrar por pantalla
            InputStream salidaHijo2 = proceso2.getInputStream();
            BufferedReader lectorSalida2 = new BufferedReader(new InputStreamReader(salidaHijo2));

            while ((linea = lectorSalida2.readLine()) != null) {
                System.out.println(linea);
            }

            lectorSalida2.close();

            // Leemos y mostramos los errores de los dos hijos
            BufferedReader erroresHijo1 = new BufferedReader(new InputStreamReader(proceso1.getErrorStream()));
            while ((linea = erroresHijo1.readLine()) != null) {
                System.err.println("Error en el hijo 1: " + linea);
            }
            erroresHijo1.close();

            BufferedReader erroresHijo2 = new BufferedReader(new InputStreamReader(proceso2.getErrorStream()));
            while ((linea = erroresHijo2.readLine()) != null) {
                System.err.println("Error en el hijo 2: " + linea);
            }
            erroresHijo2.close();

            // Esperamos a que terminen los dos hijos
            proceso1.waitFor();
            proceso2.waitFor();

            // Mostramos el mensaje final
            System.out.println("Finalizada la ejecución");

        } catch (InterruptedException e) {
            System.err.println("Se ha interrumpido la espera de los procesos.");
        } catch (IOException e) {
            System.err.println("Error al ejecutar o comunicarse con los procesos: " + e.getMessage());
        }
    }
}
