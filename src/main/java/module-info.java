module com.example.accountdatagui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.accountdatagui to javafx.fxml;
    exports com.example.accountdatagui;
}