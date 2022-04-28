package com.example.reactelephants;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;

import java.text.DecimalFormat;
import java.util.HashMap;

public class HelloController {
    @FXML
    Button btnAddElephant;
    @FXML
    Label lblNumberOfElephant;
    @FXML
    TableView<Elephant> tblElephants;
    @FXML
    TextField textFieldName, textFieldAge, textFieldWeight;
    @FXML
    VBox vBoxList;
    HashMap<Elephant, HBox> herdControls = new HashMap<>();
    ObservableList<Elephant> observableListElephants = FXCollections.observableArrayList();

    public void initialize() {
        createNewElephant();
        observableListElephants.addListener((ListChangeListener<Elephant>) change -> {
            lblNumberOfElephant.setText("Number of Elephant: " + observableListElephants.size());
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
                        textFieldName.textProperty().bindBidirectional(currentElephant.nameProperty());
                        //ToDo : attention
                        /*textFieldWeight.textProperty().addListener((observableValue, s, t1) -> {
                            currentElephant.setWeight(Double.parseDouble(t1));
                            lblPrintElephant.setText(currentElephant.toString());
                        });*/
                        textFieldWeight.textProperty().bindBidirectional(currentElephant.weightProperty(), new DecimalFormat());

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

                        btnDeleteElephant.setOnAction(actionEvent -> observableListElephants.remove(currentElephant));
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
        initTable();
    }

    public void initTable() {

        TableColumn<Elephant, String> colName = new TableColumn("Name");
        TableColumn<Elephant, Double> colWeight = new TableColumn("Weight");
        TableColumn<Elephant, Integer> colAge = new TableColumn("Age");

        tblElephants.getColumns().clear();
        tblElephants.getColumns().addAll(colName, colWeight, colAge);
        tblElephants.setItems(observableListElephants);

        colName.setCellValueFactory(el -> el.getValue().nameProperty());
        colWeight.setCellValueFactory(new PropertyValueFactory<Elephant, Double>("weight"));
        colAge.setCellValueFactory(new PropertyValueFactory<Elephant, Integer>("age"));

        tblElephants.setEditable(true);
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colWeight.setCellFactory((TextFieldTableCell.forTableColumn(new DoubleStringConverter())));

        TableColumn<Elephant, Void> colButtonAge = new TableColumn<>("Button 'Age'");
        tblElephants.getColumns().add(colButtonAge);
        colButtonAge.setCellFactory(new Callback<TableColumn<Elephant, Void>, TableCell<Elephant, Void>>() {
            @Override
            public TableCell<Elephant, Void> call(final TableColumn<Elephant, Void> param) {
                final TableCell<Elephant, Void> cell = new TableCell<Elephant, Void>() {
                    private final Button btn = new Button("+ age");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Elephant el = getTableView().getItems().get(getIndex());
                            el.setAge(el.getAge() + 1);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });

        TableColumn<Elephant, Void> colButtonDelete = new TableColumn<>("Button 'Delete'");
        tblElephants.getColumns().add(colButtonDelete);
        colButtonDelete.setCellFactory(new Callback<TableColumn<Elephant, Void>, TableCell<Elephant, Void>>() {
            @Override
            public TableCell<Elephant, Void> call(final TableColumn<Elephant, Void> param) {
                final TableCell<Elephant, Void> cell = new TableCell<Elephant, Void>() {
                    private final Button btn = new Button("Delete");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Elephant el = getTableView().getItems().get(getIndex());
                            observableListElephants.remove(el);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void createNewElephant() {
        Elephant el = Elephant.initializeRandomElephant();
        textFieldName.setText(el.getName());
        textFieldAge.setText(String.valueOf(el.getAge()));
        textFieldWeight.setText(String.valueOf(el.getWeight()));
    }

    public void onButtonAddElephantClick() {
        Elephant newElephant = new Elephant(textFieldName.getText(), Integer.parseInt(textFieldAge.getText()), Double.parseDouble(textFieldWeight.getText()));
        observableListElephants.add(newElephant);
        createNewElephant();
    }
}


