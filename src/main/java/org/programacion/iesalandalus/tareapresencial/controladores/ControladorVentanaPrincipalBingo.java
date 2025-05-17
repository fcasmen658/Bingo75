package org.programacion.iesalandalus.tareapresencial.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programacion.iesalandalus.tareapresencial.recursos.LocalizadorRecursos;
import org.programacion.iesalandalus.tareapresencial.utilidades.Dialogos;
import org.programacion.iesalandalus.tareapresencial.utilidades.Ficheros;
import org.programacion.iesalandalus.tareapresencial.utilidades.MongoDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControladorVentanaPrincipalBingo {

    private final int TAMANO_CARTON = 75;
    private final int MAXIMO_PREMIO = 500;
    private final int PERDIDA_POR_TIRADA = 2;

    public boolean hanCantadoLinea;
    private final List<Integer> numerosExtraidos = new ArrayList<>();
    private final List<Integer> numerosCarton = new ArrayList<>();

    private final List<Integer> fila0 = new ArrayList<>();
    private final List<Integer> fila1 = new ArrayList<>();
    private final List<Integer> fila2 = new ArrayList<>();
    private final List<Integer> fila3 = new ArrayList<>();
    private final List<Integer> fila4 = new ArrayList<>();


//region Botones

    @FXML private Button btn00;
    @FXML private Button btn10;
    @FXML private Button btn20;
    @FXML private Button btn30;
    @FXML private Button btn40;

    @FXML private Button btn01;
    @FXML private Button btn11;
    @FXML private Button btn21;
    @FXML private Button btn31;
    @FXML private Button btn41;

    @FXML private Button btn02;
    @FXML private Button btn12;
    @FXML private Button btn22;
    @FXML private Button btn32;
    @FXML private Button btn42;

    @FXML private Button btn03;
    @FXML private Button btn13;
    @FXML private Button btn23;
    @FXML private Button btn33;
    @FXML private Button btn43;

    @FXML private Button btn04;
    @FXML private Button btn14;
    @FXML private Button btn24;
    @FXML private Button btn34;
    @FXML private Button btn44;

    //endregion

    @FXML private Menu Aplicacion;
    @FXML private Label lbltitulo;
    @FXML private MenuItem miGeneraCarton;
    @FXML private Button btnSiguienteNumero;
    @FXML private GridPane gpJuego;
    @FXML private Label lblImporte;
    @FXML private Label lblNumeroExtraido;
    @FXML private TextField tfNickJugador;
    @FXML private MenuItem salir;
    private ActionEvent event;

    @FXML public void initialize() {
        inicializaControles();
    }

    private void inicializaControles() {
        this.btnSiguienteNumero.setDisable(true);
        this.numerosCarton.clear();
        this.numerosExtraidos.clear();
        this.fila0.clear();
        this.fila1.clear();
        this.fila2.clear();
        this.fila3.clear();
        this.fila4.clear();
        this.hanCantadoLinea = false;
        this.tfNickJugador.setText(Ficheros.leer());

        for (Node nodo : this.gpJuego.getChildren()) {
            if (nodo instanceof Button) {
                ((Button)nodo).setGraphic(null);
                ((Button)nodo).setText("");
                ((Button)nodo).setDisable(false);
            }
        }
    }

    private void generaPremio() {
        int premioGenerado = (int)(Math.random() * MAXIMO_PREMIO + 1);
        this.lblImporte.setText(String.valueOf(premioGenerado));
    }

    private boolean validoNick() {
        String nick = this.tfNickJugador.getText();
        return nick != null && !nick.trim().isEmpty();
    }

    private void escribeAFichero() {
        Ficheros.escribir(this.tfNickJugador.getText(), Integer.parseInt(this.lblImporte.getText()));
    }

    private void escribeABd() {
        MongoDB.insertar(this.tfNickJugador.getText(), Integer.parseInt(this.lblImporte.getText()));
    }


    @FXML
    private void nuevoCarton(ActionEvent event) {
        if (this.validoNick()) {
            //this.inicializaControles();
            this.generaPremio();
            this.creaCarton();
            this.btnSiguienteNumero.setDisable(false);

        } else {
            Dialogos.mostrarDialogoError("Bingo Al-Ándalus", "No se puede iniciar el juego hasta que escribas un nick válido.");
        }

    }

    @FXML
    private void creaCarton() {
        //this.inicializaControles();
        this.generaPremio();

        for (Node node : this.gpJuego.getChildren()) {
            if (node instanceof Button && node.getId() != null) {
                String id = node.getId();
                if (!id.equals("btn00") && !id.equals("btn11") && !id.equals("btn22") && !id.equals("btn33") && !id.equals("btn44")) {
                    int col = Integer.parseInt(id.substring(id.length() - 2, id.length() - 1));
                    int fila = Integer.parseInt(id.substring(id.length() - 1));
                    int numero = Integer.parseInt(this.generaNumeroEnFuncionDeColumna(col));
                    this.numerosCarton.add(numero);
                    this.anadeNumeroAFila(numero, fila);
                    ((Button) node).setText(String.valueOf(numero));
                } else if (id.equals("btn22")) {
                    ((Button) node).setText("");
                    node.setDisable(true);
                }
            }
        }
    }

    private String generaNumeroEnFuncionDeColumna(int columna) {
        int index;
        int numeroGenerado;
        switch(columna) {
            case 0:
                do {
                    numeroGenerado = (int)(Math.random() * 15.0D + 1.0D);
                    index = this.numerosCarton.indexOf(numeroGenerado);
                } while(index != -1);

                return String.valueOf(numeroGenerado);
            case 1:
                do {
                    numeroGenerado = (int)(Math.random() * 15.0D + 16.0D);
                    index = this.numerosCarton.indexOf(numeroGenerado);
                } while(index != -1);

                return String.valueOf(numeroGenerado);
            case 2:
                do {
                    numeroGenerado = (int)(Math.random() * 15.0D + 31.0D);
                    index = this.numerosCarton.indexOf(numeroGenerado);
                } while(index != -1);

                return String.valueOf(numeroGenerado);
            case 3:
                do {
                    numeroGenerado = (int)(Math.random() * 15.0D + 46.0D);
                    index = this.numerosCarton.indexOf(numeroGenerado);
                } while(index != -1);

                return String.valueOf(numeroGenerado);
            case 4:
                do {
                    numeroGenerado = (int)(Math.random() * 15.0D + 61.0D);
                    index = this.numerosCarton.indexOf(numeroGenerado);
                } while(index != -1);

                return String.valueOf(numeroGenerado);
            default:
                throw new IllegalArgumentException("Valor inesperado: " + columna);
        }
    }


    private void anadeNumeroAFila(Integer numero, int fila) {
        switch(fila) {
            case 0:
                this.fila0.add(numero);
                break;
            case 1:
                this.fila1.add(numero);
                break;
            case 2:
                this.fila2.add(numero);
                break;
            case 3:
                this.fila3.add(numero);
                break;
            case 4:
                this.fila4.add(numero);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + fila);
        }

    }

    @FXML
    private void nuevoNumero(ActionEvent event) {
        if (numerosExtraidos.size() == TAMANO_CARTON) {
            Dialogos.mostrarDialogoAdvertencia("Advertencia", "Ya se han extraído todos los números.");
            return;
        }

        int nuevoNumero;
        do {
            nuevoNumero = (int) (Math.random() * TAMANO_CARTON) + 1;
        } while (numerosExtraidos.contains(nuevoNumero));

        numerosExtraidos.add(nuevoNumero);
        lblNumeroExtraido.setText(String.valueOf(nuevoNumero));

        int premioActual = Integer.parseInt(lblImporte.getText());
        premioActual = premioActual - PERDIDA_POR_TIRADA;
        if (premioActual < 0) {
            premioActual = 0;
        }
        lblImporte.setText(String.valueOf(premioActual));

        compruebaNumeroExtraidoEnCarton();
    }

    private void compruebaNumeroExtraidoEnCarton() {
        int numero = Integer.parseInt(lblNumeroExtraido.getText());

        for (Node nodo : gpJuego.getChildren()) {
            if (nodo instanceof Button boton) {
                if (boton.getText() != null && boton.getText().equals(String.valueOf(numero))) {
                    Integer fila = GridPane.getRowIndex(boton);
                    Integer col = GridPane.getColumnIndex(boton);
                    if (fila == null) fila = 0;
                    if (col == null) col = 0;

                    if (!(fila == 2 && col == 2)) {
                        ImageView imagen = new ImageView(Objects.requireNonNull(LocalizadorRecursos.class.getResource("imagenes/iconoCerveza.png")).toExternalForm());
                        imagen.setFitWidth(30);
                        imagen.setFitHeight(30);
                        boton.setGraphic(imagen);
                        boton.setDisable(true);

                        compruebaFila(fila, numero);
                        break;
                    }
                }
            }
        }
    }

    private void compruebaFila(int fila, Integer numero) {
        switch(fila) {
            case 0:
                this.fila0.remove(numero);
                if (this.fila0.isEmpty()) {
                    Dialogos.mostrarDialogoInformacion("Bingo Al-Ándalus. ", " FILA, HAN CANTADO FILA....FILA " + (fila+1));
                    finalizaPartida();
                }
                break;
            case 1:
                this.fila1.remove(numero);
                if (this.fila1.isEmpty()) {
                    Dialogos.mostrarDialogoInformacion("Bingo Al-Ándalus. ", " FILA, HAN CANTADO FILA....FILA " + (fila+1));
                    finalizaPartida();
                }
                break;
            case 2:
                this.fila2.remove(numero);
                if (this.fila2.isEmpty()) {
                    Dialogos.mostrarDialogoInformacion("Bingo Al-Ándalus. ", " FILA, HAN CANTADO FILA....FILA " + (fila+1));
                    finalizaPartida();
                }
                break;
            case 3:
                this.fila3.remove(numero);
                if (this.fila3.isEmpty()) {
                    Dialogos.mostrarDialogoInformacion("Bingo Al-Ándalus. ", " FILA, HAN CANTADO FILA....FILA " + (fila+1));
                    finalizaPartida();
                }
                break;
            case 4:
                this.fila4.remove(numero);
                if (this.fila4.isEmpty()) {
                    Dialogos.mostrarDialogoInformacion("Bingo Al-Ándalus. ", " FILA, HAN CANTADO FILA....FILA " + (fila+1));
                    finalizaPartida();
                }
                break;
            case 5:
                if (this.fila0.isEmpty() && this.fila1.isEmpty() && this.fila2.isEmpty() && this.fila3.isEmpty() && this.fila4.isEmpty()) {
                    Dialogos.mostrarDialogoInformacion("Bingo Al-Ándalus. ", " BINGO, HAN CANTADO BINGO....", null);
                    finalizaPartida();
                }
                break;
            default:
                throw new IllegalArgumentException("Valor no esperado: " + (fila+1));
        }
    }

    private void finalizaPartida() {
        this.btnSiguienteNumero.setDisable(true);
        this.hanCantadoLinea = true;
        this.escribeAFichero();
        this.escribeABd();

    }

    @FXML
    void salir(ActionEvent event) {
        if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", (Stage)null)) {
            System.exit(0);
        }

    }


}