package com.example.trabajoacd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    // Método para cambiar la raíz de la escena
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    @Override
    public void start(Stage stage) throws IOException {

        // Cargar la escena
        scene = new Scene(loadFXML("User"), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Método para cargar un archivo FXML
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch();
    }
}

