module com.example.trabajoacd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.desktop;

    opens com.example.trabajoacd.model.domain to java.xml.bind;


    opens com.example.trabajoacd to javafx.fxml;
    exports com.example.trabajoacd;
    exports com.example.trabajoacd.controller;
    opens com.example.trabajoacd.controller to javafx.fxml;
}