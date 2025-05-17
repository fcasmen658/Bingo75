package org.programacion.iesalandalus.tareapresencial.utilidades;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.bson.Document;

public class MongoDB {

    //private static final String SERVIDOR = "cluster0.uyprv4n.mongodb.net";
    private static final String SERVIDOR = "cluster0.2bzztyn.mongodb.net";
    private static final int PUERTO = 27017; //TODO: 27017 no se utiliza
    private static final String BD = "bingo75";
    private static final String USUARIO = "fcasmen658";
    private static final String CONTRASENA = "27194575Ll**..";


    private static final String NOMBRE = "nombre";
    private static final String PREMIO = "premio";
    private static final String FECHA_PREMIO = "fecha";
    private static final String COLECCION = "premios";
    private static MongoCollection<Document> coleccionPremios;
    private static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static MongoClient conexion = null;

    private MongoDB() {
    }

    private static MongoDatabase getBD() {
        if (conexion == null) {
            establecerConexion();
        }
        return conexion.getDatabase(BD);
    }

    private static void establecerConexion() {
        MongoClientSettings settings;
        if (!SERVIDOR.equals("localhost")) {
            settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb+srv://"+USUARIO+":"+CONTRASENA+"@"+SERVIDOR+"/?retryWrites=true&w=majority&appName=Cluster0")).build();
        } else {
            settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb://"+USUARIO+":"+CONTRASENA+"@"+SERVIDOR+"/?retryWrites=true&w=majority&appName=Cluster0")).build();
        }
        conexion = MongoClients.create(settings);
        try {
            if (!SERVIDOR.equals("localhost")) {
                conexion.getDatabase(BD).runCommand(new Document("ping", 1));
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        System.out.println("Conexión a MongoDB realizada correctamente.");
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            conexion.close();
            conexion = null;
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }

    public static void insertar(String nombre, int premio) {
        String fechaHora = LocalDateTime.now().format(FORMATO_DIA_HORA);
        coleccionPremios = getBD().getCollection(COLECCION);
        coleccionPremios.insertOne(new Document().append(NOMBRE, String.valueOf(nombre)).append(PREMIO, Integer.valueOf(premio)).append(FECHA_PREMIO, fechaHora));
        cerrarConexion();
    }
}