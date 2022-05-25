package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class loginSceneController{

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label failedLoginLabel;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    String user,pass;

    public boolean isIdPasswordValid(String u,String p){
        String data = "";
        try{
            FileReader r = new FileReader("c:/Users/lenovo/Desktop/demo/src/main/dataBase/id_password.txt");
            int tmp;
            while((tmp=r.read())!=-1) {
                data += (char)tmp;
            }
            r.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        String[] idPassword = data.split("[\\r\\n]+");
        for(int i=0;i<idPassword.length;i++) {
            String[] tmp = idPassword[i].split(" ");
            if(u.equals(tmp[0]) && p.equals(tmp[1])){
                return true;
            }
        }
        return false;
    }

    public void switchToInfoScene(ActionEvent event) throws java.io.IOException {
        user = username.getText();
        pass = password.getText();

        if(user.isBlank() == false && pass.isBlank() == false){
            if(isIdPasswordValid(user,pass)){

                if(user.equals("admin")){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_info.fxml"));
                    root = loader.load();
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("info.fxml"));
                    root = loader.load();

                    infoSceneController info = loader.getController();
                    info.getData(user);
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            else{
                failedLoginLabel.setText("This username or password is invalid.");
            }
        }
        else if(user.isBlank() == false){
            failedLoginLabel.setText("Please enter password.");
        }
        else if(pass.isBlank() == false){
            failedLoginLabel.setText("Please enter username.");
        }
        else{
            failedLoginLabel.setText("Please enter username and password.");
        }

    }

    public void switchToRegisterScene(ActionEvent event) throws java.io.IOException {
        root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
