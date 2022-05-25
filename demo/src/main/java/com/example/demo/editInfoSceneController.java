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

public class editInfoSceneController{

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String username;
    private String password;
    private String data,data2;
    private String[] idPassword;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label errorLabel;

    @FXML
    private TextField newUsernameTextField;
    @FXML
    private TextField oldPasswordTextField;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private TextField confirmPasswordTextField;

    public void getData(String u){
        username = u;
        usernameLabel.setText(u);
    }

    public boolean checkSpace(String t){
        for(int i=0;i<t.length();i++){
            if(t.charAt(i)==' '){
                return true;
            }
        }
        return false;
    }

    public void setData(){
        try{
            data = "";
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

        try{
            data2 = "";
            FileReader r = new FileReader("c:/Users/lenovo/Desktop/demo/src/main/dataBase/data.txt");
            int tmp;
            while((tmp=r.read())!=-1) {
                data2 += (char)tmp;
            }
            r.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean isUsernameUsed(){
        idPassword = data.split("[\\r\\n]+");
        for(int i=0;i<idPassword.length;i++) {
            String[] t = idPassword[i].split(" ");
            if(!t[0].equals(username)&&t[0].equals(newUsernameTextField.getText())){
                return true;
            }
            else if(t[0].equals(username)){
                password = t[1];
            }
        }
        return false;
    }

    public void editUser2(){
        String newData ="";
        String[] dat = data2.split("[\\r\\n]+");
        for(int i=0;i< dat.length;i++){
            String[] t = dat[i].split(" ");
            if(!t[0].equals(username)) {
                newData+=dat[i];
            }
            else{
                newData+=newUsernameTextField.getText()+" "+t[1]+" "+t[2]+" "+t[3]+" "+t[4]+" "+t[5]+" "+t[6]+" "+t[7];
            }
            if(i!=dat.length-1){
                newData+="\n";
            }
        }

        try{
            FileWriter w = new FileWriter("c:/Users/lenovo/Desktop/demo/src/main/dataBase/data.txt");
            w.write(newData);
            w.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        username = newUsernameTextField.getText();
    }

    public void editUser(){
        String newData ="";
        for(int i=0;i< idPassword.length;i++){
            String[] t = idPassword[i].split(" ");
            if(!t[0].equals(username)) {
                newData+=idPassword[i];
            }
            else{
                newData+=newUsernameTextField.getText()+" "+newPasswordTextField.getText();
            }
            if(i!=idPassword.length-1){
                newData+="\n";
            }
        }

        try{
            FileWriter w = new FileWriter("c:/Users/lenovo/Desktop/demo/src/main/dataBase/id_password.txt");
            w.write(newData);
            w.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        if(!username.equals(newUsernameTextField.getText())){
            editUser2();
        }
    }

    public void finishButtonAction(ActionEvent event) throws  java.io.IOException{
        setData();
        if(isUsernameUsed()){
            errorLabel.setText("Username is used.");
        }
        else if(!password.equals(oldPasswordTextField.getText())){
            errorLabel.setText("Password Incorrect.");
        }
        else if(newUsernameTextField.getText()==""||oldPasswordTextField.getText()==""||
                newPasswordTextField.getText()==""|| confirmPasswordTextField.getText()=="") {
            errorLabel.setText("Missing requirement.");
        }
        else if(!confirmPasswordTextField.getText().equals(newPasswordTextField.getText())){
            errorLabel.setText("Password not match.");
        }
        else if(checkSpace(newUsernameTextField.getText())||checkSpace(newPasswordTextField.getText())||checkSpace(confirmPasswordTextField.getText())){
            errorLabel.setText("Space(\" \") is banned.");
        }
        else{
            editUser();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("info.fxml"));
            root = loader.load();

            infoSceneController info = loader.getController();
            info.getData(username);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void cancelButtonAction(ActionEvent event) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("info.fxml"));
        root = loader.load();

        infoSceneController info = loader.getController();
        info.getData(username);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
