package org.programacion.iesalandalus.tareapresencial.utilidades;

import java.io.*;
import java.time.LocalDateTime;

public class Ficheros {
   private static final String NOMBRE_FICHERO = "datos/premios.dat";
   private static final String SEPARADOR = " - ";
   private static final String NOMBRE = "nombre";
   private static final String PREMIO = "premio";

   private static final String FECHA = LocalDateTime.now().toString();

   public Ficheros() {
   }

   public static void escribir(String nombre, int premio) {
      File fichero = new File("datos/premios.dat");
      LocalDateTime fecha = LocalDateTime.now();
      try {
         if (!fichero.exists()) {
            fichero.createNewFile();
         }

         FileOutputStream fileout = new FileOutputStream(fichero);
         DataOutputStream dataOS = new DataOutputStream(fileout);
         dataOS.writeUTF(nombre);
         dataOS.writeInt(premio);
         dataOS.writeUTF(fecha.toString());
         dataOS.close();
         System.out.println("Fichero escrito correctamente");
      } catch (FileNotFoundException e) {
         System.out.println("Fichero no encontrado");
         e.getMessage();
      } catch (IOException e) {
         System.out.println("Error de I/O");
         e.getMessage();
      }

   }

   public static String leer() {
      File fichero = new File(NOMBRE_FICHERO);
      String nombre = "";

      try {
         FileInputStream filein = new FileInputStream(fichero);
         DataInputStream dataIS = new DataInputStream(filein);

         try {
            while(true) {
               nombre = dataIS.readUTF();
               int premio = dataIS.readInt();
               String fecha = dataIS.readUTF();
            }
         } catch (EOFException e) {
            System.out.println("Fichero leído correctamente");
            dataIS.close();
         }
      } catch (FileNotFoundException e) {
         System.out.println("Fichero no encontrado");
         e.getMessage();
      } catch (IOException e) {
         System.out.println("Error de I/O");
         e.getMessage();
      }

      return nombre;
   }
}