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

public class ResultController extends DBConnect implements Initializable {

    @FXML
    private TableView<Answers> tableView;
    @FXML
    private TableColumn<Answers, String> choiceCol;
    @FXML
    private TableColumn<Answers, String> resultCol;

    @FXML
    Text totalVoteAmount;
    ObservableList<Answers> answerList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(CheckController.currVoteTitle);
        loadData();
    }

    public void loadData(){ // to Load the percentage based on the current voters
        int total = countTotalVote();
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("select * from %s" , CheckController.currVoteTitle);
            ResultSet rs =  con.createStatement().executeQuery(sql);
            while (rs.next()) {
                float resultPercent = Float.parseFloat(rs.getString("result")) / total * 100;
                System.out.println(Float.parseFloat(rs.getString("result")) / total);
                String resultInString = rs.getString("result") +" ( "  + resultPercent +"%" + " )";
                answerList.add(new Answers(rs.getString("choice"), resultInString));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        choiceCol.setCellValueFactory(new PropertyValueFactory<Answers, String>("choice"));
        resultCol.setCellValueFactory(new PropertyValueFactory<Answers, String>("result"));

        // load dummy data
        tableView.setItems(answerList);
        totalVoteAmount.setText(Integer.toString(total));

    }

    public int countTotalVote(){ // get the total count in the ongoing vote
        int totalVote = 0;

        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("select * from %s" , CheckController.currVoteTitle);
            ResultSet rs =  con.createStatement().executeQuery(sql);
            while (rs.next()) {
                totalVote += Integer.parseInt(rs.getString("result"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalVote;
    }
    public void backToCheck() throws IOException { // function to return to another scene
        Parent root = FXMLLoader.load(getClass().getResource("Check.fxml"));
        Main.primaryStage.setScene(new Scene(root, 600, 400));
    }

    public void endVote() throws IOException { // exterminate current vote function
        String sql1 = String.format("drop table %s" , CheckController.currVoteTitle);
        String sql2 = String.format("delete from votes where title = '%s'" , CheckController.currVoteTitle);
        String sql3 = String.format("delete from voters where vote = '%s' " , CheckController.currVoteTitle);
        try {
            st = con.createStatement();
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);
            st.executeUpdate(sql3);
            System.out.println("SUCCESSFULLY DELETE THE VOTE FROM THE DATA");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        backToCheck();
    }

}
