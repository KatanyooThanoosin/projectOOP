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
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminEditController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String username;
    private String data;
    private String[] vaccine = {"Astra","Pfizer","Sinopharm","Zinovac","---None---"};
    private String[] noneVac = {"---None---"};
    private String v1,v2,v3,v4;

    @FXML
    private ChoiceBox<String> firstDose;
    @FXML
    private ChoiceBox<String> secondDose;
    @FXML
    private ChoiceBox<String> thirdDose;
    @FXML
    private ChoiceBox<String>forthDose;

    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL argo, ResourceBundle arg1) {
        firstDose.getItems().addAll(vaccine);
        secondDose.getItems().addAll(noneVac);
        thirdDose.getItems().addAll(noneVac);
        forthDose.getItems().addAll(noneVac);

        firstDose.setValue("---None---");
        secondDose.setValue("---None---");
        thirdDose.setValue("---None---");
        forthDose.setValue("---None---");

        firstDose.setOnAction(this::getFirst);
        secondDose.setOnAction(this::getSecond);
        thirdDose.setOnAction(this::getThird);
        forthDose.setOnAction(this::getForth);
    }

    public void getFirst(ActionEvent event) {
        v1 = firstDose.getValue();
        if(v1.equals("---None---")){
            secondDose.getItems().clear();
            thirdDose.getItems().clear();
            forthDose.getItems().clear();

            secondDose.getItems().addAll(noneVac);
            thirdDose.getItems().addAll(noneVac);
            forthDose.getItems().addAll(noneVac);

            secondDose.setValue("---None---");
            thirdDose.setValue("---None---");
            forthDose.setValue("---None---");
        }
        else{
            secondDose.getItems().clear();
            secondDose.getItems().addAll(vaccine);
            secondDose.setValue("---None---");
        }
    }

    public void getSecond(ActionEvent event) {
        v2 = secondDose.getValue();
        if(v2.equals("---None---")) {
            thirdDose.getItems().clear();
            forthDose.getItems().clear();

            thirdDose.getItems().addAll(noneVac);
            forthDose.getItems().addAll(noneVac);

            thirdDose.setValue("---None---");
            forthDose.setValue("---None---");
        }
        else{
            thirdDose.getItems().clear();
            thirdDose.getItems().addAll(vaccine);
            thirdDose.setValue("---None---");
        }
    }

    public void getThird(ActionEvent event) {
        v3 = thirdDose.getValue();
        if(v3.equals("---None---")) {
            forthDose.getItems().clear();
            forthDose.getItems().addAll(noneVac);
            forthDose.setValue("---None---");
        }
        else{
            forthDose.getItems().clear();
            forthDose.getItems().addAll(vaccine);
            forthDose.setValue("---None---");
        }
    }

    public void getForth(ActionEvent event) {
        v4 = forthDose.getValue();
    }

    public void getData(String u){
        username = u;
        usernameLabel.setText(u);
    }

    public void setData(){
        try{
            data = "";
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
    }

    public String fvac(String v){
        if(v.equals("---None---")){
            return "None";
        }
        return v;
    }

    public void finishButtonAction(ActionEvent event) throws java.io.IOException{
        setData();

        String newData ="";
        String[] dat = data.split("[\\r\\n]+");
        for(int i=0;i< dat.length;i++){
            String[] t = dat[i].split(" ");
            if(!t[0].equals(username)) {
                newData+=dat[i];
            }
            else{
                newData+=t[0]+" "+t[1]+" "+t[2]+" "+t[3]+" "+fvac(v1)+" "+fvac(v2)+" "+fvac(v3)+" "+fvac(v4);
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_info.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void cancelButtonAction(ActionEvent event) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_info.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
