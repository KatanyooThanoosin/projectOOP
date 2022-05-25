package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class infoSceneController{

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String user;

    @FXML
    private Label nameLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label v1Label;
    @FXML
    private Label v2Label;
    @FXML
    private Label v3Label;
    @FXML
    private Label v4Label;

    public void vaccineSetLabel(Label v,String t){
        if(t.equals("None")){
            v.setText("ยังไม่ได้ฉีด");
        }
        else{
            v.setText(t);
        }
    }

    public void getData(String username){
        user = username;
        try{
            String data = "";
            FileReader r = new FileReader("c:/Users/lenovo/Desktop/demo/src/main/dataBase/data.txt");
            int tmp;
            while((tmp=r.read())!=-1) {
                data += (char)tmp;
            }

            String[] dat = data.split("[\\r\\n]+");
            for(int i=0;i<dat.length;i++){
                String[] t = dat[i].split(" ");
                if(username.equals(t[0])){
                    usernameLabel.setText(t[0]);
                    nameLabel.setText(t[1]);
                    ageLabel.setText(t[2]);
                    genderLabel.setText(t[3]);
                    vaccineSetLabel(v1Label,t[4]);
                    vaccineSetLabel(v2Label,t[5]);
                    vaccineSetLabel(v3Label,t[6]);
                    vaccineSetLabel(v4Label,t[7]);
                    break;
                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void switchToEditScene(ActionEvent event) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_info.fxml"));
        root = loader.load();

        editInfoSceneController edit = loader.getController();
        edit.getData(user);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLoginScene(ActionEvent event) throws java.io.IOException {
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
