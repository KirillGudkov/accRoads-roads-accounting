package sample;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.info.InfoRoads;

public class Controller {
    private Stage stage;
    @FXML
    TextField street;
    @FXML
    TextField part1;
    @FXML
    TextField part2;
    @FXML
    Button firstBtn;
    @FXML
    Button finalBtn;
    @FXML
    Button prev;

//    private List<String> listOneBox = new ArrayList<>();
//    private ObservableList<String> observableListOne = FXCollections.observableList(listOneBox);

    /**
     *
     * @param primaryStage
     */
    public void setStage (Stage primaryStage) {
        this.stage = primaryStage;
    }

    /**
     *
     * @param actionEvent
     */
    public void go(ActionEvent actionEvent) {
        if (!street.getText().isEmpty()) {
            ParallelTransition parallelTransition = new ParallelTransition();

            TranslateTransition animateText = new TranslateTransition(Duration.millis(300), street);
            TranslateTransition animateButton = new TranslateTransition(Duration.millis(300), firstBtn);
            animateText.setFromX(0);
            animateText.setToX(550);
            animateButton.setFromX(0);
            animateButton.setToX(550);

            TranslateTransition animatePart1 = new TranslateTransition(Duration.millis(500), part1);
            TranslateTransition animatePart2 = new TranslateTransition(Duration.millis(500), part2);
            TranslateTransition animateFinalBtn = new TranslateTransition(Duration.millis(500), finalBtn);
            TranslateTransition animatePrev = new TranslateTransition(Duration.millis(500), prev);
            animatePart1.setFromX(0);
            animatePart1.setToX(510);
            animatePart2.setFromX(0);
            animatePart2.setToX(510);
            animateFinalBtn.setFromX(0);
            animateFinalBtn.setToX(510);
            animatePrev.setFromX(0);
            animatePrev.setToX(510);

            parallelTransition.getChildren().addAll(animateText, animateButton,
                                                    animateFinalBtn, animatePart1,
                                                    animatePart2, animatePrev);
            parallelTransition.play();
        }
    }

    /**
     *
     * @param actionEvent
     * @throws Exception
     */
    public void goForward(ActionEvent actionEvent) throws Exception{
        if (!(part1.getText().isEmpty() || part2.getText().isEmpty())) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("info/InfoRoads.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 900, 500);
            InfoRoads infoRoads = fxmlLoader.getController();
            infoRoads.getStreet(street.getText());
            infoRoads.getPart1(part1.getText());
            infoRoads.getPart2(part2.getText());
            infoRoads.showInfo();
            stage.setResizable(false);
            stage.setTitle("Сведения о дороге");
            stage.getIcons().add(new Image("file:resources/road-512.png"));
            stage.setScene(scene);
            infoRoads.setStage(stage);
        }
    }

    /**
     *
     * @param actionEvent
     */
    public void prev(ActionEvent actionEvent) {
        ParallelTransition parallelTransition = new ParallelTransition();

        TranslateTransition animateText = new TranslateTransition(Duration.millis(500), street);
        TranslateTransition animateButton = new TranslateTransition(Duration.millis(500), firstBtn);
        animateText.setFromX(550);
        animateText.setToX(0);
        animateButton.setFromX(550);
        animateButton.setToX(0);

        TranslateTransition animatePart1 = new TranslateTransition(Duration.millis(300), part1);
        TranslateTransition animatePart2 = new TranslateTransition(Duration.millis(300), part2);
        TranslateTransition animateFinalBtn = new TranslateTransition(Duration.millis(300), finalBtn);
        TranslateTransition animatePrev = new TranslateTransition(Duration.millis(300), prev);
        animatePart1.setFromX(510);
        animatePart1.setToX(0);
        animatePart2.setFromX(510);
        animatePart2.setToX(0);
        animateFinalBtn.setFromX(510);
        animateFinalBtn.setToX(0);
        animatePrev.setFromX(510);
        animatePrev.setToX(0);

        parallelTransition.getChildren().addAll(animateText, animateButton, animateFinalBtn, animatePart1, animatePart2, animatePrev);
        parallelTransition.play();
    }
}
