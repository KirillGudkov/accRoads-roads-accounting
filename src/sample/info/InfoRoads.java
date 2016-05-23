package sample.info;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Controller;
import sample.DBConnection.DBConnection;
import java.sql.ResultSet;

/**
 * Created by sillybird on 22.05.2016.
 */
public class InfoRoads {
    private Stage stage;
    private String street;
    private String part1;
    private String part2;

    @FXML
    private Label side;
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
     * @throws Exception
     */
    public void showInfo () throws Exception{
        DBConnection dbConnection = new DBConnection();
        ResultSet result = dbConnection.sendRequest(street, part1, part2);

        side.setText("ул."+street+": "+"от ул."+part1+" до ул."+part2);
        while (result.next()) {
            technicalCondition.setText(result.getString("TECHNICAL_CONDITION"));
            cosmeticCondition.setText(result.getString("COSMETIC_CONDITION"));
            dateBegin.setText(result.getString("DATE_BEGIN_OF_REPAIRS"));
            dateEnd.setText(result.getString("DATE_END_OF_REPAIRS"));
            status.setText(result.getString("STATUS"));
            money.setText(result.getString("SPENT_MONEY")+" рублей");
        }
        dbConnection.closeConnection();
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
