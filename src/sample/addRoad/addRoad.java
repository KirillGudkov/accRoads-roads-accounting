package sample.addRoad;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.RestrictiveField;

/**
 * Created by sillybird on 10.06.2016.
 */
public class addRoad {
    private Stage stage;
    @FXML
    private Button btnForward;
    @FXML
    private Pane paneForField;
    private RestrictiveField spentMoney = new RestrictiveField();

    /**
     *
     * @param owner
     * @throws Exception
     */
    public void showDialog (Window owner) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../addRoad/addRoad.fxml"));
        loader.setController(this);
        Parent root = (Parent)loader.load();
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(400);
        stage.setMaxWidth(600);
        stage.getIcons().add(new Image("file:resources/error.png"));
        stage.setTitle("Добавить дорогу");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initOwner(owner);
        spentMoney.setMaxLength(11);
        spentMoney.setRestrict("[0-9]");
        spentMoney.setLayoutX(14);
        spentMoney.setLayoutY(250);
        spentMoney.setMinWidth(264);
        paneForField.getChildren().add(spentMoney);
        System.out.println(owner);
        stage.show();
    }
    /**
     *
     * @param event
     */
    public void goForward(ActionEvent event) {
        /**
         * TODO
         */
    }

    public void cancel(ActionEvent event) {
        stage.close();
    }
}
