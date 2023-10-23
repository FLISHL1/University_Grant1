module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    requires java.sql;
    requires jbcrypt;
    requires simplecaptcha;
    requires java.desktop;
    requires javafx.swing;


    opens main.controller to javafx.fxml;
    exports main.controller;
    exports main.logic;
    opens main.logic to javafx.fxml;

}