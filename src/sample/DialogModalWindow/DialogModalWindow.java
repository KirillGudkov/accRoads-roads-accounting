package sample.DialogModalWindow;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by sillybird on 25.05.2016.
 */
public class DialogModalWindow {
    private Stage stage;
    @FXML
    private Button btnForward;
    @FXML
    private Label label;
    @FXML
    private Label title;

    /**
     *
     * @param owner
     * @throws Exception
     */
    public void showDialog (Window owner, String error) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../DialogModalWindow/DialogModalWindow.fxml"));
        loader.setController(this);
        Parent root = (Parent)loader.load();
        Platform.runLater(() -> {
            stage = new Stage();
        });
        Platform.runLater(()->{stage.setScene(new Scene(root));});
        Platform.runLater(()->{stage.setMinHeight(250);});
        Platform.runLater(()->{stage.setMaxWidth(500);});
        Platform.runLater(()->{stage.getIcons().add(new Image("file:resources/error.png"));});
        Platform.runLater(()->{stage.setTitle("error");});
        Platform.runLater(()->{stage.initModality(Modality.WINDOW_MODAL);});
        Platform.runLater(()->{stage.setResizable(false);});
        Platform.runLater(()->{stage.initOwner(owner);});
        label.setWrapText(true);
        label.setText(error);
        System.out.println(owner);
        Platform.runLater(()->{stage.show();});
    }
    /**
     *
     * @param event
     */
    public void goForward(ActionEvent event) {
        Stage stage = (Stage) btnForward.getScene().getWindow();
        stage.close();
    }
}
