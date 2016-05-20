package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.maps.GoogleMaps;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Stage stage;
    @FXML
    private Button button;
    @FXML
    private ComboBox sides;

    private List<String> listOneBox = new ArrayList<>();
    private ObservableList<String> observableListOne = FXCollections.observableList(listOneBox);

    public void initialize () {
        Platform.runLater(()->button.setDisable(true));
        observableListOne.addAll("Благовещенск", "Белогорск", "Райчихинск");
        Platform.runLater(()->sides.setItems(observableListOne));
    }

    public void setStage (Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void goForward(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../sample/maps/GoogleMaps.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 500);
        GoogleMaps googleMaps = new GoogleMaps();
        googleMaps.setStage(stage);
        stage.setTitle("Maps");
        stage.setScene(scene);
    }

    public void changeSide(ActionEvent actionEvent) {
        button.setDisable(false);
    }
}
