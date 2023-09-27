module com.example.trabajoacd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.desktop;

    opens model to java.xml.bind;


    opens com.example.trabajoacd to javafx.fxml;
    exports com.example.trabajoacd;
}