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
import javafx.scene.control.RadioButton;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class registerSceneController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String data;
    private String data2;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField confirmTextField;
    @FXML
    private RadioButton maleCheck,femaleCheck,otherCheck;
    @FXML
    private ChoiceBox<String> ageChoice;
    private String[] num = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
            "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51",
            "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68",
            "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
            "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100"};
    String name,username,password,confirm,gender="";
    int age = -1;

    @Override
    public void initialize(URL argo, ResourceBundle arg1) {
        ageChoice.getItems().addAll(num);
        ageChoice.setOnAction(this::getAge);
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
    }

    public void setData2(){
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

    public boolean checkSpace(String t){
        for(int i=0;i<t.length();i++){
            if(t.charAt(i)==' '){
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameUsed(){
        String[] idPassword = data.split("[\\r\\n]+");
        for(int i=0;i<idPassword.length;i++) {
            String[] tmp = idPassword[i].split(" ");
            if(username.equals(tmp[0])){
                return true;
            }
        }
        return false;
    }

    public void getAge(ActionEvent event) {age = Integer.parseInt(ageChoice.getValue());}

    public void switchToInfoScene(ActionEvent event) throws java.io.IOException{
        try{
            setData();
            setData2();

            name = nameTextField.getText();
            username = usernameTextField.getText();
            password = passwordTextField.getText();
            confirm = confirmTextField.getText();

            if(maleCheck.isSelected()) gender = "Male";
            else if(femaleCheck.isSelected()) gender = "Female";
            else if(otherCheck.isSelected()) gender = "Other Sex";

            if(name==""||username==""||password==""||confirm==""||age==-1||gender==""){
                errorLabel.setText("Missing requirement.");
            }
            else if(isUsernameUsed()){
                errorLabel.setText("Username is used.");
            }
            else if(!confirm.equals(password)){
                errorLabel.setText("Password not match.");
            }
            else if(checkSpace(name)||checkSpace(username)||checkSpace(password)||checkSpace(confirm)){
                errorLabel.setText("Space(\" \") is banned.");
            }
            else{
                FileWriter w = new FileWriter("c:/Users/lenovo/Desktop/demo/src/main/dataBase/id_password.txt");
                w.write(data+"\n"+username+" "+password);
                w.close();
                FileWriter wd = new FileWriter("c:/Users/lenovo/Desktop/demo/src/main/dataBase/data.txt");
                if(data2==""){
                    wd.write(username+" "+name+" "+age+" "+gender+" None None None None");
                }
                else{
                    wd.write(data2+"\n"+username+" "+name+" "+age+" "+gender+" None None None None");
                }
                wd.close();

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
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void switchToLoginScene(ActionEvent event) throws java.io.IOException {
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
