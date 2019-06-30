package sample;

import javafx.fxml.FXML;//this import is used to access the fx:id
import javafx.fxml.FXMLLoader;// this import is used to load a new fxml file into the window
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;// this import is used to set the dimensions of the window
import javafx.scene.control.TextField;// this import is used to get the text from the text areas and text fields

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;// this import is used to collect the local current date
import java.time.format.DateTimeFormatter;// this import is used to format the data dd/mm/yyyy
import java.util.ResourceBundle;

public class CreateController extends DBConnect implements Initializable {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy");
    LocalDate date = LocalDate.now();


    @FXML
    TextField titleField;
    @FXML
    TextField answer1Field;

    @FXML
    TextField answer2Field;

    @FXML
    TextField questionField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void backToHome() throws IOException { // move to another scene
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Main.primaryStage.setScene(new Scene(root, 600, 400));
    }

    public void createVote() throws IOException { // function that allows the storing of data for the newly created vote
        String sql1 = String.format("CREATE TABLE %s (\n" +
                "    id int NOT NULL AUTO_INCREMENT,\n" +
                "    choice varchar(255),\n" +
                "    result varchar(255),\n" +
                "    PRIMARY KEY(id)\n" +
                ");" , titleField.getText()) ;

        String sql2 = String.format("insert into %s(choice , result) VALUES('%s' , '0')" , titleField.getText() , answer1Field.getText() );
        String sql3 = String.format("insert into %s(choice , result) VALUES('%s' , '0')" , titleField.getText() , answer2Field.getText() );


        String sql4 = String.format("insert into votes(date , title ,question , creator) VALUES('%s' ,'%s','%s','%s')" ,dtf.format(date) ,titleField.getText() ,questionField.getText() , Main.activeUser);

        try{
            st = con.createStatement();
            st.executeUpdate(sql1);
            System.out.println("Table Created !");
            st.executeUpdate(sql2);
            st.executeUpdate(sql3);
            st.executeUpdate(sql4);

            System.out.println("Vote Created !");


        }catch (Exception e){
            e.printStackTrace();
        }
        backToHome();
    }
}
