module me.twintailedfoxxx.itlabs {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens me.twintailedfoxxx.itlabs to javafx.fxml;
    exports me.twintailedfoxxx.itlabs;
}