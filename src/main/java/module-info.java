module com.example.salonchik {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;


    opens com.example.salonchik to javafx.fxml;
    exports com.example.salonchik;
}