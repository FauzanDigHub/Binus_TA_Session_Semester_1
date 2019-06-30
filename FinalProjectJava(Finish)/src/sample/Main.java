package sample;

import javafx.application.Application; // this import is used to connect with the javafx application in main, to override their start function
import javafx.fxml.FXMLLoader;// this import is used to load a new fxml file into the window
import javafx.scene.Parent;
import javafx.scene.Scene;// this import is used to set the dimensions of the window
import javafx.stage.Stage;// this import is used to import the stage, so every page will create a new stage

public class Main extends Application { // runs the program
    public static String activeUser = ""; // empty
    public static Stage primaryStage;
    public static String activeVote = "";
    @Override
    public void start(Stage primaryStage) throws Exception{ // start the program with login
        this.primaryStage = primaryStage;
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            primaryStage.setTitle("Vote Anything");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
