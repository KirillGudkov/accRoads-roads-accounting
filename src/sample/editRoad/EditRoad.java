package sample.editRoad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.DBConnection.DBConnection;
import sample.DialogModalWindow.DialogModalWindow;
import sample.RestrictiveField;
import sample.info.InfoRoads;

import java.sql.ResultSet;

/**
 * Created by sillybird on 12.06.2016.
 */
public class EditRoad {
    private Stage stage;
    @FXML
    private Button btnForward;
    @FXML
    private Pane paneForField;
    @FXML
    private TextField techCond;
    @FXML
    private TextField cosmCond;
    @FXML
    private TextField dateBegin;
    @FXML
    private TextField dateEnd;
    @FXML
    private TextField status;
    private RestrictiveField spentMoney = new RestrictiveField();

    private String first;
    private String second;
    private String third;

    public String getFirst(String first) {
        return this.first = first;
    }

    public String getSecond(String second) {
        return this.second = second;
    }

    public String getThird(String third) {
        return this.third = third;
    }

    /**
     *
     * @param owner
     * @throws Exception
     */

    public void showDialog (Window owner) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../editRoad/EditRoad.fxml"));
        loader.setController(this);
        Parent root = (Parent)loader.load();
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(400);
        stage.setMaxWidth(600);
        stage.getIcons().add(new Image("file:resources/add.png"));
        stage.setTitle("Изменение информации");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initOwner(owner);
        stage.centerOnScreen();
        spentMoney.setMaxLength(11);
        spentMoney.setRestrict("[0-9]");
        spentMoney.setLayoutX(14);
        spentMoney.setLayoutY(250);
        spentMoney.setMinWidth(264);
        paneForField.getChildren().add(spentMoney);
        System.out.println(owner);
        DBConnection dbConnection = new DBConnection();
        dbConnection.createConnection();
        ResultSet result = dbConnection.getInfo(first, second, third);

        if (result.next()) {
            techCond.setPromptText(result.getString("TECHNICAL_CONDITION"));
            cosmCond.setPromptText(result.getString("COSMETIC_CONDITION"));
            dateBegin.setPromptText(result.getString("DATE_BEGIN_OF_REPAIRS"));
            dateEnd.setPromptText(result.getString("DATE_END_OF_REPAIRS"));
            status.setPromptText(result.getString("STATUS"));
            spentMoney.setPromptText(result.getString("SPENT_MONEY"));
        }
        stage.show();
    }

    /**
     *
     * @param event
     * @throws Exception
     */
    public void goForward(ActionEvent event) throws Exception {
        if (!techCond.getText().isEmpty() && !cosmCond.getText().isEmpty() && !dateBegin.getText().isEmpty() && !dateEnd.getText().isEmpty() && !status.getText().isEmpty() && !spentMoney.getText().isEmpty()) {
            DBConnection dbConnection = new DBConnection();
            dbConnection.createConnection();
            dbConnection.updateInfo(techCond.getText(), cosmCond.getText(), dateBegin.getText(), dateEnd.getText(), status.getText(), spentMoney.getText(), first, second, third);
            DialogModalWindow dialogModalWindow = new DialogModalWindow();
            dialogModalWindow.showDialog(stage.getOwner(), "Обновление информации прошло успешно!", "Обновлено");
            cancel(event);
        }
        else {
            DialogModalWindow dialogModalWindow = new DialogModalWindow();
            dialogModalWindow.showDialog(stage, "Не все поля заполнены!", "Ошибка");
        }
    }

    public void cancel(ActionEvent event) {
        stage.close();
    }
}
