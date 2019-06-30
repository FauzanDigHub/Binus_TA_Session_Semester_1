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
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController extends DBConnect implements Initializable {

    @FXML
    Text usernameText;

    @FXML
    private TableView<Vote> tableView;
    @FXML
    private TableColumn<Vote, String> dateCol;
    @FXML
    private TableColumn<Vote, String> titleCol;



    ObservableList<Vote> voteList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameText.setText(Main.activeUser);
        loadData();
    }

    public void logout() throws IOException { // back to login scene
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Main.primaryStage.setScene(new Scene(root, 600, 400));
    }

    public void goToCreate() throws IOException { // go to create vote scene
        Parent root = FXMLLoader.load(getClass().getResource("Create.fxml"));
        Main.primaryStage.setScene(new Scene(root, 600, 400));
    }

    public void loadData(){ // get the created data of available votes that exist
        try {
            Connection con = DBConnect.getConnection();
            String sql = "select * from votes";
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

    public void vote() throws IOException { // function that allow the user to vote and checked if it already voted
        String title = tableView.getSelectionModel().getSelectedItem().getTitle();
        int counter = 0;
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("select * from voters where username = '%s'" , Main.activeUser) ;
            ResultSet rs =  con.createStatement().executeQuery(sql);
            while (rs.next()) {
                if(rs.getString("vote").equals(title)){
                    counter = 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(counter == 0){
            Main.activeVote = title;
            Parent root = FXMLLoader.load(getClass().getResource("Vote.fxml"));
            Main.primaryStage.setScene(new Scene(root, 600, 400));
        }
        else
            System.out.println(String.format("UDAH VOTE SI %s" , Main.activeUser));


    }

    public void goToCheck() throws IOException { // go to check scene
        Parent root = FXMLLoader.load(getClass().getResource("Check.fxml"));
        Main.primaryStage.setScene(new Scene(root, 600, 400));
    }
}
