package sample.maps;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Created by sillybird on 21.05.2016.
 */
public class GoogleMaps extends Parent{
    private Stage stage;
    private WebView webView;
    private WebEngine webEngine;
    @FXML
    AnchorPane pane;
    private boolean ready;

    public void setStage (Stage stage) {
        this.stage = stage;
    }

    public GoogleMaps () {
        init();
        Platform.runLater(()-> pane.getChildren().add(webView));
    }

    public void init() {
        webView = new WebView();
        webEngine = webView.getEngine();
        webView.setStyle("-fx-min-width: 900px; -fx-min-height: 750px; -fx-max-width: 900px; -fx-max-height: 750px;");
        webView.setLayoutY(55);
        webEngine.load(getClass().getResource("map.html").toExternalForm());
        ready = false;
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(final ObservableValue<? extends Worker.State> observableValue,
                                    final Worker.State oldState,
                                    final Worker.State newState) {
                    if (newState == Worker.State.SUCCEEDED) {
                        ready = true;
                    }
                }
            });
        }
    }
