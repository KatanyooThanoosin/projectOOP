package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminInfoController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String[] userData;
    private boolean isSearched = false;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label errorLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label v1Label;
    @FXML
    private Label v2Label;
    @FXML
    private Label v3Label;
    @FXML
    private Label v4Label;

    public boolean isUserFound(String user){
        String data = "";
        try{
            FileReader r = new FileReader("c:/Users/lenovo/Desktop/demo/src/main/dataBase/data.txt");
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

        String[] dat = data.split("[\\r\\n]+");
        for(int i=0;i<dat.length;i++){
            String[] t = dat[i].split(" ");
            if(t[0].equals(user)){
                userData = dat[i].split(" ");
                return true;
            }
        }
        return false;
    }

    public void vaccineSetLabel(Label v,String t){
        if(t.equals("None")){
            v.setText("ยังไม่ได้ฉีด");
        }
        else{
            v.setText(t);
        }
    }

    public void searchButtonAction(ActionEvent event) throws java.io.IOException{
        if(searchTextField.getText().equals("")){
            errorLabel.setText("Please enter user.");
            isSearched = false;
        }
        else if(isUserFound(searchTextField.getText())){
            errorLabel.setText("");
            usernameLabel.setText(userData[0]);
            nameLabel.setText(userData[1]);
            ageLabel.setText(userData[2]);
            genderLabel.setText(userData[3]);
            vaccineSetLabel(v1Label,userData[4]);
            vaccineSetLabel(v2Label,userData[5]);
            vaccineSetLabel(v3Label,userData[6]);
            vaccineSetLabel(v4Label,userData[7]);
            isSearched = true;
        }
        else{
            errorLabel.setText("User not found.");
            usernameLabel.setText("");
            nameLabel.setText("");
            ageLabel.setText("");
            genderLabel.setText("");
            v1Label.setText("");
            v2Label.setText("");
            v3Label.setText("");
            v4Label.setText("");
            isSearched = false;
        }
    }

    public void editButtonAction(ActionEvent event) throws java.io.IOException{
        if(isSearched){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_edit.fxml"));
            root = loader.load();

            adminEditController edit = loader.getController();
            edit.getData(userData[0]);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            errorLabel.setText("Please select user first.");
        }
    }

    public void logoutButtonAction(ActionEvent event) throws java.io.IOException{
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}