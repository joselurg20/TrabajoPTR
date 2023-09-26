module com.example.trabajoacd {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trabajoacd to javafx.fxml;
    exports com.example.trabajoacd;
}