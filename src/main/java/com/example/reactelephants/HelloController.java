package com.example.reactelephants;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class HelloController {
    @FXML
    Button btnAddElephant;
    @FXML
    Label lblNumberOfElephant;
    @FXML
    TextField textFieldName, textFieldAge, textFieldWeight;
    @FXML
    VBox vBoxList;
    HashMap<Elephant, HBox> herdControls = new HashMap<>();
    ObservableList<Elephant> observListElephants = FXCollections.observableArrayList();

    public void initialize() {
        createNewElephan();


        observListElephants.addListener((ListChangeListener<Elephant>) change -> {
            lblNumberOfElephant.setText("Number of Elephant: " + observListElephants.size());
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Elephant currentElephant : change.getAddedSubList()) {
                        Label lblPrintElephant = new Label(currentElephant.toString());


                        TextField textFieldName = new TextField();
                        textFieldName.setText(currentElephant.getName());
                        textFieldName.textProperty().addListener((observableValue, s, t1) -> {
                            currentElephant.setName(t1);
                            lblPrintElephant.setText(currentElephant.toString());
                        });

                        TextField textFieldWeight = new TextField();
                        textFieldWeight.setText(String.valueOf(currentElephant.getWeight()));
                        textFieldWeight.textProperty().addListener((observableValue, s, t1) -> {
                            currentElephant.setWeight(Double.parseDouble(t1));
                            lblPrintElephant.setText(currentElephant.toString());
                        });

                        Button btnIncAge = new Button("Age++");

                        Button btnDeleteElephant = new Button("DEL");

                        HBox HBoxForNewElephant = new HBox();
                        HBoxForNewElephant.setSpacing(10);
                        HBoxForNewElephant.getChildren().add(textFieldName);
                        HBoxForNewElephant.getChildren().add(textFieldWeight);
                        HBoxForNewElephant.getChildren().add(btnIncAge);
                        HBoxForNewElephant.getChildren().add(btnDeleteElephant);
                        HBoxForNewElephant.getChildren().add(lblPrintElephant);

                        btnIncAge.setOnAction(actionEvent -> {
                            currentElephant.setAge(currentElephant.getAge() + 1);
                            lblPrintElephant.setText(currentElephant.toString());
                        });



                        vBoxList.getChildren().add(HBoxForNewElephant);

                        herdControls.put(currentElephant, HBoxForNewElephant);

                        btnDeleteElephant.setOnAction(actionEvent -> observListElephants.remove(currentElephant));
                    }
                }
                if (change.wasRemoved()) {
                    for (Elephant e : change.getRemoved()) {
                        vBoxList.getChildren().remove(herdControls.get(e));
                        herdControls.remove(e);
                    }
                }
            }
        });
    }

    private void createNewElephan() {
        Elephant el = Elephant.initializeRandomElephant();
        textFieldName.setText(el.getName());
        textFieldAge.setText(String.valueOf(el.getAge()));
        textFieldWeight.setText(String.valueOf(el.getWeight()));
    }


    public void onButtonAddElephantClick() {
        Elephant newElephant = new Elephant(textFieldName.getText(), Integer.parseInt(textFieldAge.getText()), Double.parseDouble(textFieldWeight.getText()));
        observListElephants.add(newElephant);
        createNewElephan();
    }

}


