package sample.info;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import sample.Controller;
import sample.DBConnection.DBConnection;
import sample.about.AboutApp;
import sample.addRoad.addRoad;
import javafx.event.EventHandler;
import sample.DialogModalWindow.DialogModalWindow;
import sample.editRoad.EditRoad;
import sample.maps.GoogleMaps;
import sample.maps.MapEvent;

import java.io.IOException;
import java.sql.ResultSet;

/**
 * Created by sillybird on 22.05.2016.
 */
public class InfoRoads {
    private Stage stage;
    private String street;
    private String part1;
    private String part2;
    private ResultSet result;
    private EventHandler<ActionEvent> actionEvent;
    private Pane paneForLoading = new Pane();
    private DBConnection dbConnection = new DBConnection();
    private boolean ready;
    private WebView webView;
    private WebEngine webEngine;
    private JSObject doc;
    private EventHandler<MapEvent> onMapLatLngChanged;

    @FXML private AnchorPane anchor;
    @FXML private Label side;
    @FXML private Pane paneForLogo;
    @FXML private Pane paneForLabels;
    @FXML private ImageView logo;
    @FXML private Label technicalCondition;
    @FXML private Label cosmeticCondition;
    @FXML private Label dateBegin;
    @FXML private Label dateEnd;
    @FXML private Label status;
    @FXML private Label money;
    @FXML private MenuItem chooseAnotherPart;
    @FXML private MenuItem addRoad;
    @FXML private MenuItem removeRoad;
    @FXML private MenuItem changeStatus;
    @FXML private MenuItem exit;
    @FXML private MenuItem copy;
    @FXML private MenuItem paste;
    @FXML private MenuItem about;
    @FXML private MenuItem update;
    @FXML private MenuItem refresh;
    @FXML private MenuItem print;

    /**
     * @param stage
     */
    public void setStage (Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * @param street
     */
    public void getStreet (String street) {
        this.street = street;
    }

    /**
     *
     * @param part1
     */
    public void getPart1 (String part1) {
        this.part1 = part1;
    }

    /**
     *
     * @param part2
     */
    public void getPart2 (String part2) {
        this.part2 = part2;
    }

    /**
     *
     */
    public InfoRoads () {
        actionEvent = listenMenu();
        Platform.runLater(() -> chooseAnotherPart.setOnAction(actionEvent));
        Platform.runLater(() -> addRoad.setOnAction(actionEvent));
        Platform.runLater(() -> removeRoad.setOnAction(actionEvent));
        Platform.runLater(() -> changeStatus.setOnAction(actionEvent));
        Platform.runLater(() -> exit.setOnAction(actionEvent));
        Platform.runLater(() -> copy.setOnAction(actionEvent));
        Platform.runLater(() -> paste.setOnAction(actionEvent));
        Platform.runLater(() -> about.setOnAction(actionEvent));
        Platform.runLater(() -> update.setOnAction(actionEvent));
        Platform.runLater(() -> refresh.setOnAction(actionEvent));
        Platform.runLater(() -> print.setOnAction(actionEvent));
        Image image = new Image("file:resources/road-512.png");
        Platform.runLater(() -> logo.setImage(image));
        Platform.runLater(()->init());
        Platform.runLater(() -> initCommunication());
        Platform.runLater(() -> anchor.getChildren().add(webView));
    }

    /**
     *
     */
    public void showLoading() {
        javafx.scene.image.Image image = new javafx.scene.image.Image("file:resources/loading.gif");
        ImageView imageView = new ImageView(image);
        imageView.setX(410);
        imageView.setY(180);
        Platform.runLater(()->paneForLoading.setStyle("-fx-background-color: white; -fx-min-width: 900px; -fx-min-height: 379px;"));
        Platform.runLater(()->paneForLoading.setLayoutY(71));
        Platform.runLater(() -> paneForLoading.getChildren().add(imageView));
        Platform.runLater(()-> {anchor.getChildren().add(paneForLoading);});
    }

    /**
     *
     */
    public void hideLoading() {
        Platform.runLater(() -> anchor.getChildren().remove(paneForLoading));
    }

    /**
     *
     */
    public void showPaneForLabels () {
        Platform.runLater(() -> paneForLabels.setVisible(true));
    }

    /**
     *
     */
    public void hidePaneForLabels () {
        Platform.runLater(() -> paneForLabels.setVisible(false));
    }
    /**
     *
     */
    public void showPaneForLogo () {
        Platform.runLater(() -> paneForLogo.setVisible(true));
    }

    /**
     *
     */
    public void hidePaneForLogo () {
        Platform.runLater(() -> paneForLogo.setVisible(false));
    }

    /**
     *
     * @return
     */
    public EventHandler<ActionEvent> listenMenu () {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String element = mItem.getText();

                if(element.equalsIgnoreCase("Выбрать другой участок дороги")){
                    System.out.println("Вы нажали 'выбрать другой участок'");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../sample.fxml"));
                    Parent root = null;
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root, 600, 350);
                    Controller controller = fxmlLoader.getController();
                    stage.setResizable(false);
                    stage.setTitle("Road Accounting");
                    stage.setScene(scene);
                    controller.setStage(stage);
                }
                if(element.equalsIgnoreCase("Добавить дорогу")){
                    System.out.println("Вы нажали 'Добавить дорогу'");
                    addRoad addRoad = new addRoad();
                    try {
                        addRoad.showDialog(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(element.equalsIgnoreCase("Удалить дорогу")){
                    System.out.println("Вы нажали 'Удалить дорогу'");
                    DialogModalWindow dialogModalWindow = new DialogModalWindow();
                    try {
                        dialogModalWindow.showDialog(stage, "Вы точно хотите удалить дорогу?", "Удаление");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if(element.equalsIgnoreCase("Редактировать дорогу")){
                    System.out.println("Вы нажали 'Редактировать дорогу'");
                    EditRoad editRoad = new EditRoad();
                    editRoad.getFirst(street);
                    editRoad.getSecond(part1);
                    editRoad.getThird(part2);
                    try {
                        editRoad.showDialog(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(element.equalsIgnoreCase("Составить отчёт и распечатать")){
                    System.out.println("Вы нажали 'Составить отчёт и распечатать'");
                    PrinterJob printerJob = PrinterJob.createPrinterJob();
                    printerJob.printPage(paneForLabels);
                    printerJob.endJob();
                }

                if(element.equalsIgnoreCase("Выйти")){
                    System.out.println("Вы нажали 'Выйти'");
                    stage.close();
                }
                if(element.equalsIgnoreCase("Обновить")){
                    System.out.println("Вы нажали 'Обновить'");
                }
                if(element.equalsIgnoreCase("Скопировать")){
                    System.out.println("Вы нажали 'Скопировать'");
                }
                if(element.equalsIgnoreCase("Вставить")){
                    System.out.println("Вы нажали 'Вставить'");
                }
                if(element.equalsIgnoreCase("О программе")){
                    System.out.println("Вы нажали 'О программе'");
                    AboutApp aboutApp = new AboutApp();
                    try {
                        aboutApp.showDialog(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(element.equalsIgnoreCase("Обновить приложение")){
                    System.out.println("Вы нажали 'Обновить приложение'");
                }
            }
        };
    }


    public void showInfo () throws Exception{
        dbConnection.createConnection();
        result = dbConnection.getInfo(street, part1, part2);

        if (result.next()) {
            side.setText("ул." + street + ": " + "от ул." + part1 + " до ул." + part2);
            technicalCondition.setText(result.getString("TECHNICAL_CONDITION"));
            cosmeticCondition.setText(result.getString("COSMETIC_CONDITION"));
            dateBegin.setText(result.getString("DATE_BEGIN_OF_REPAIRS"));
            dateEnd.setText(result.getString("DATE_END_OF_REPAIRS"));
            status.setText(result.getString("STATUS"));
            money.setText(result.getString("SPENT_MONEY") + " рублей");
            hidePaneForLogo();
            showPaneForLabels();
        }
        else {
            DialogModalWindow dialogModalWindow = new DialogModalWindow();
            dialogModalWindow.showDialog(stage, "Нет данных", "Ошибка базы данных");
            hidePaneForLabels();
            showPaneForLogo();
        }
        dbConnection.closeConnection();
    }

    public void init() {
        webView = new WebView();
        webEngine = webView.getEngine();
        webView.setStyle("-fx-min-width: 470px; -fx-min-height: 286px; -fx-max-width: 470px; -fx-max-height: 286px;");
        webView.setLayoutY(83);
        webView.setLayoutX(410);
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
                    doc.setMember("app", InfoRoads.this);
                }
            }
        });
    }
    public void setOnMapLatLngChanged(EventHandler<MapEvent> eventHandler) {
        onMapLatLngChanged = eventHandler;
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

    /**
     *
     * @param actionEvent
     */
    public void goForward(ActionEvent actionEvent) {

    }

    /**
     *
     * @param actionEvent
     * @throws Exception
     */
    public void goBack(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../sample.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 350);
        Controller controller = fxmlLoader.getController();
        stage.setResizable(false);
        stage.setTitle("Road Accounting");
        stage.setScene(scene);
        stage.centerOnScreen();
        controller.setStage(stage);
    }
}
