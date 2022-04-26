module com.example.reactelephants {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.reactelephants to javafx.fxml;
    exports com.example.reactelephants;
}