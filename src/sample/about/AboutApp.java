package sample.about;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by sillybird on 12.06.2016.
 */
public class AboutApp {
    private Stage stage;
    private String OSName;
    private String OSVersion;
    @FXML private Label about;


    public void getOSName () {
        this.OSName = System.getProperty("os.name");
    }

    public void getOSVersion () {
        this.OSVersion = System.getProperty("os.version");
    }
    /**
     *
     * @param owner
     * @throws Exception
     */

    public void showDialog (Window owner) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../about/AboutApp.fxml"));
        loader.setController(this);
        Parent root = (Parent)loader.load();
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(400);
        stage.setMaxWidth(350);
        stage.getIcons().add(new Image("file:resources/about.png"));
        stage.setTitle("О программе");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initOwner(owner);
        System.out.println(owner);
        getOSName();
        getOSVersion();
        about.setText("Операционная система: " + OSName + "\n\n" + "Версия: " + OSVersion + "\n\nВерсия ПО: 1.0.1\n\n2016\n\n Все права защищены");
        stage.centerOnScreen();
        stage.show();
    }
    /**
     *
     * @param event
     */
    public void ok(ActionEvent event) {
        stage.close();
    }
}
