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
    opens main to javafx.fxml;
    exports main;
}