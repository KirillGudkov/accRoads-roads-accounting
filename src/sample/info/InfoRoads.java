package sample.info;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Controller;
import sample.DBConnection.DBConnection;
import javafx.event.EventHandler;
import sample.DialogModalWindow.DialogModalWindow;

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

    @FXML
    private AnchorPane anchor;
    @FXML
    private Label side;
    @FXML
    private Pane paneForLogo;
    @FXML
    private Pane paneForLabels;
    @FXML
    private ImageView logo;
    @FXML
    private Label technicalCondition;
    @FXML
    private Label cosmeticCondition;
    @FXML
    private Label dateBegin;
    @FXML
    private Label dateEnd;
    @FXML
    private Label status;
    @FXML
    private Label money;
    @FXML
    private MenuItem chooseAnotherPart;
    @FXML
    private MenuItem addRoad;
    @FXML
    private MenuItem removeRoad;
    @FXML
    private MenuItem changeStatus;
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem copy;
    @FXML
    private MenuItem paste;
    @FXML
    private MenuItem about;
    @FXML
    private MenuItem update;

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
        Platform.runLater(()->chooseAnotherPart.setOnAction(actionEvent));
        Platform.runLater(()->addRoad.setOnAction(actionEvent));
        Platform.runLater(()->removeRoad.setOnAction(actionEvent));
        Platform.runLater(()->changeStatus.setOnAction(actionEvent));
        Platform.runLater(()->exit.setOnAction(actionEvent));
        Platform.runLater(()->copy.setOnAction(actionEvent));
        Platform.runLater(()->paste.setOnAction(actionEvent));
        Platform.runLater(()->about.setOnAction(actionEvent));
        Platform.runLater(() ->update.setOnAction(actionEvent));
        Image image = new Image("file:resources/road-512.png");
        Platform.runLater(() -> logo.setImage(image));
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
        Platform.runLater(()->anchor.getChildren().remove(paneForLoading));
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
        Platform.runLater(()->paneForLogo.setVisible(true));
    }

    /**
     *
     */
    public void hidePaneForLogo () {
        Platform.runLater(()->paneForLogo.setVisible(false));
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
                }
                if(element.equalsIgnoreCase("Добавить дорогу")){
                    System.out.println("Вы нажали 'Добавить дорогу'");
                }
                if(element.equalsIgnoreCase("Удалить дорогу")){
                    System.out.println("Вы нажали 'Удалить дорогу'");
                }
                if(element.equalsIgnoreCase("Изменить статус")){
                    System.out.println("Вы нажали 'Изменить статус'");
                }
                if(element.equalsIgnoreCase("Выйти")){
                    System.out.println("Вы нажали 'Выйти'");
                }
                if(element.equalsIgnoreCase("Скопировать")){
                    System.out.println("Вы нажали 'Скопировать'");
                }
                if(element.equalsIgnoreCase("Вставить")){
                    System.out.println("Вы нажали 'Вставить'");
                }
                if(element.equalsIgnoreCase("О программе")){
                    System.out.println("Вы нажали 'О программе'");
                }
                if(element.equalsIgnoreCase("Обновить")){
                    System.out.println("Вы нажали 'Обновить'");
                }
            }
        };
    }


    public void showInfo () throws Exception{
        Thread resp = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dbConnection.createConnection();
                    result = dbConnection.sendRequest(street, part1, part2);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        DialogModalWindow dialogModalWindow = new DialogModalWindow();
                        dialogModalWindow.showDialog(stage, "Ошибка подключения к базе данных");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    if (result.next()) {
                            System.out.println(result.getClass());
                            System.out.println(result.getString("TECHNICAL_CONDITION"));
                            String tc = result.getString("TECHNICAL_CONDITION");
                            String cc = result.getString("COSMETIC_CONDITION");
                            String db = result.getString("DATE_BEGIN_OF_REPAIRS");
                            String de = result.getString("DATE_END_OF_REPAIRS");
                            String s = result.getString("STATUS");
                            String m = result.getString("SPENT_MONEY");
                            Platform.runLater(() -> {
                                side.setText("ул." + street + ": " + "от ул." + part1 + " до ул." + part2);
                                technicalCondition.setText(tc);
                                cosmeticCondition.setText(cc);
                                dateBegin.setText(db);
                                dateEnd.setText(de);
                                status.setText(s);
                                money.setText(m + " рублей");
                                hidePaneForLogo();
                                showPaneForLabels();
                            });
                    }
                    else {
                        try {
                            DialogModalWindow dialogModalWindow = new DialogModalWindow();
                            dialogModalWindow.showDialog(stage, "Нет данных");
                            hidePaneForLabels();
                            showPaneForLogo();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    dbConnection.closeConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread load = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    showLoading();
                    resp.start();
                    resp.join();
                    hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        load.start();
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
        controller.setStage(stage);
    }
}
