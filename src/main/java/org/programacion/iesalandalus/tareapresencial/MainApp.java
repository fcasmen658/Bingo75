package org.programacion.iesalandalus.tareapresencial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.programacion.iesalandalus.tareapresencial.recursos.LocalizadorRecursos;

public class MainApp extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage escenarioPrincipal) throws Exception {
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/Bingo.fxml"));
			Parent raiz=fxmlLoader.load();


			Scene escena = new Scene(raiz);
			escenarioPrincipal.setResizable(false);
			escenarioPrincipal.getIcons().add(new javafx.scene.image.Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/bingo75.png")));
			escenarioPrincipal.setTitle("Bingo 75 Al-Ándalus");
			escenarioPrincipal.setScene(escena);
			escenarioPrincipal.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}