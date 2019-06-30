package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends DBConnect implements Initializable {

    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;

    @FXML
    TextField userRegisterField;

    @FXML
    TextField passRegisterField;



    @FXML
    Text isTaken;

    @FXML
    Text successRegister;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void register(){ //create the function to register an account
        String username = userRegisterField.getText();
        String password = passRegisterField.getText();

        int counter = 0;
            try {
            Connection con = DBConnect.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from user");
            while (rs.next()) {

                if(username.equals(rs.getString("username"))){
                    isTaken.setText("Username is taken !");
                    successRegister.setText("");
                    counter = 1;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

            if(counter==0){

            String sql = String.format("insert into user(username , password) VALUES('%s' ,'%s')" , username , password);

            try{
                st = con.createStatement();
                st.executeUpdate(sql);
                System.out.println("User Registered");
                successRegister.setText("Account successfully registered !");
                isTaken.setText("");

            }catch (Exception e){
                e.printStackTrace();
            }


        }
            userRegisterField.setText("");
            passRegisterField.setText("");

    }



    public void login() throws IOException , ClassNotFoundException { // create the function to login the registered account

        String username = usernameField.getText();
        int counter = 0;

        try {
            Connection con = DBConnect.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from user");
            while (rs.next()) {

                if(username.equals(rs.getString("username"))){
                    System.out.println(rs.getString("username"));
                    Main.activeUser = username;
                    Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
                    Main.primaryStage.setScene(new Scene(root, 600, 400));
                    counter = 1;

                }

            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        if(counter== 0){
            isTaken.setText("User is'nt exists !");
            successRegister.setText("");
            usernameField.setText("");
            passwordField.setText("");
        }


    }


}
