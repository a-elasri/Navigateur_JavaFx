package presentation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationJavaFX extends Application
{
    TabPane root;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent browser = FXMLLoader.load(getClass().getResource("main.fxml"));
        Tab browserTab = new Tab("Nouvel onglet", browser);
        browserTab.getStyleClass().add("browserTab");
        Tab addTab = new Tab("", null);
        FontAwesomeIconView font = new FontAwesomeIconView();
        font.setGlyphName("PLUS");
        font.setSize("20");
        font.fillProperty().setValue(Paint.valueOf("white"));
        addTab.setGraphic(font);
        addTab.getStyleClass().add("addTab");
        addTab.setClosable(false);
        addTab.setOnSelectionChanged(new EventHandler<Event>()
        {
            @Override
            public void handle(Event event) {
                addNewTab("main.fxml");
            }
        });
        root = new TabPane(browserTab, addTab);
        Scene scene = new Scene(root);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        scene.getStylesheets().add("presentation/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addNewTab(String s) {
        try {
            Parent browser = FXMLLoader.load(getClass().getResource(s));
            Tab browserTab = new Tab("Nouvel onglet", browser);
            root.getTabs().add(root.getTabs().size() - 1, browserTab);
            root.getSelectionModel().select(browserTab);
        } catch (Exception ex) {
            Logger.getLogger(ApplicationJavaFX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args)
    {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        launch(args);
    }
}
