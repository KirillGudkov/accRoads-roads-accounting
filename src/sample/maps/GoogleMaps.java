package sample.maps;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

/**
 * Created by sillybird on 21.05.2016.
 */
public class GoogleMaps extends Parent{
    private Stage stage;
    private WebView webView;
    private WebEngine webEngine;
    private JSObject doc;
    private EventHandler<MapEvent> onMapLatLngChanged;
    @FXML
    AnchorPane pane;
    private boolean ready;

    public void setStage (Stage stage) {
        this.stage = stage;
    }

    public GoogleMaps (String side) {
        init(side);
        initCommunication();
        Platform.runLater(() -> pane.getChildren().add(webView));
    }

    public void init(String side) {
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
    private void initCommunication() {
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            @Override
            public void changed(final ObservableValue<? extends Worker.State> observableValue,
                                final Worker.State oldState,
                                final Worker.State newState)
            {
                if (newState == Worker.State.SUCCEEDED)
                {
                    doc = (JSObject) webEngine.executeScript("window");
                    doc.setMember("app", GoogleMaps.this);
                }
            }
        });
    }

    public void setOnMapLatLngChanged(EventHandler<MapEvent> eventHandler) {
        onMapLatLngChanged = eventHandler;
    }

    public void handle(double lat, double lng) {
        if(onMapLatLngChanged != null) {
            MapEvent event = new MapEvent(this, lat, lng);
            onMapLatLngChanged.handle(event);
            System.out.println(lat);
            System.out.println(lng);
        }
    }

    private void invokeJS(final String str) {
        if(ready) {
            doc.eval(str);
        }
        else {
            webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
            {
                @Override
                public void changed(final ObservableValue<? extends Worker.State> observableValue,
                                    final Worker.State oldState,
                                    final Worker.State newState)
                {
                    if (newState == Worker.State.SUCCEEDED)
                    {
                        doc.eval(str);
                    }
                }
            });
        }
    }

    public void setMarkerPosition(double lat, double lng) {
        String sLat = Double.toString(lat);
        String sLng = Double.toString(lng);
        invokeJS("setMarkerPosition(" + sLat + ", " + sLng + ")");
    }

    public void setMapCenter(double lat, double lng) {
        String sLat = Double.toString(lat);
        String sLng = Double.toString(lng);
        invokeJS("setMapCenter(" + sLat + ", " + sLng + ")");
    }

    public void switchSatellite() {
        invokeJS("switchSatellite()");
    }

    public void switchRoadmap() {
        invokeJS("switchRoadmap()");
    }

    public void switchHybrid() {
        invokeJS("switchHybrid()");
    }

    public void switchTerrain() {
        invokeJS("switchTerrain()");
    }

    public void startJumping() {
        invokeJS("startJumping()");
    }

    public void stopJumping() {
        invokeJS("stopJumping()");
    }

    public void setHeight(double h) {
        webView.setPrefHeight(h);
    }

    public void setWidth(double w) {
        webView.setPrefWidth(w);
    }

    public ReadOnlyDoubleProperty widthProperty() {
        return webView.widthProperty();
    }

    }
