package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CheckController extends DBConnect implements Initializable {

    public static String currVoteTitle = "";

    @FXML
    private TableView<Vote> tableView;
    @FXML
    private TableColumn<Vote, String> dateCol;
    @FXML
    private TableColumn<Vote, String> titleCol;
    ObservableList<Vote> voteList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    public void loadData(){ // show the admin created vote based on the name of the creator
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format( "select * from votes where creator = '%s' ", Main.activeUser);
            ResultSet rs =  con.createStatement().executeQuery(sql);
            while (rs.next()) {
                voteList.add(new Vote(rs.getString("date"), rs.getString("title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dateCol.setCellValueFactory(new PropertyValueFactory<Vote, String>("date"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Vote, String>("title"));

        // load dummy data
        tableView.setItems(voteList);
    }

    public void backToHome() throws IOException { // back to home scene
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Main.primaryStage.setScene(new Scene(root, 600, 400));
    }

    public void goToResult() throws IOException { // based on the selected vote, move to result scene to get the result value
        currVoteTitle =  tableView.getSelectionModel().getSelectedItem().getTitle();
        Parent root = FXMLLoader.load(getClass().getResource("Result.fxml"));
        Main.primaryStage.setScene(new Scene(root, 600, 400));
    }
}
