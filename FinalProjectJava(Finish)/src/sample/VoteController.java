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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VoteController extends DBConnect implements Initializable {

    @FXML
    private TableView<Answers> tableView;
    @FXML
    private TableColumn<Answers, String> answerCol;

    ObservableList<Answers> answerList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }
    public void backToHome() throws IOException { // back to home scene
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Main.primaryStage.setScene(new Scene(root, 600, 400));
    }

    public void vote() throws IOException { // function to update amount of voters and result
        String choice = tableView.getSelectionModel().getSelectedItem().getChoice();
        String voteTitle = Main.activeVote;
        int currResult = Integer.parseInt(getResult(voteTitle , choice)) +1 ;
        String result = Integer.toString(currResult);
        String sql = String.format("update %s set result = '%s' where choice = '%s' " , voteTitle, result , choice);

        try{
            st = con.createStatement();
            st.executeUpdate(sql);
            System.out.println("Result Updated");

        }catch (Exception e){
            e.printStackTrace();
        }
        insertVoters(Main.activeUser , voteTitle);
        backToHome();

    }

    public void loadData(){ // load the answer for the vote
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("select * from %s" , Main.activeVote);
            ResultSet rs =  con.createStatement().executeQuery(sql);
            while (rs.next()) {
                answerList.add(new Answers(rs.getString("choice"), ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        answerCol.setCellValueFactory(new PropertyValueFactory<Answers, String>("choice"));

        // load dummy data
        tableView.setItems(answerList);
    }
}
