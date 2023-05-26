package com.example;

import com.example.observer.BugObserverInterface;
import com.example.observer.BugServiceInterface;
import com.example.service.Service;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;

public class TesterController implements BugObserverInterface {


    @FXML
    private TableColumn<Bug, String> columnDenumire;
    @FXML
    private TableColumn<Bug, String> columnGradRisc;
    @FXML
    private TableColumn<Bug, String> columnStatus;
    @FXML
    private TableColumn<Bug,String> columnDescriere;
    @FXML
    private TableView<Bug> tableBug;
    @FXML
    private ComboBox<BugRisk> comboBoxGradRisc;

    @FXML
    private TextField denumireTextField;

    @FXML
    private TextField descriereTextField;

    private BugServiceInterface service;
    private final ObservableList<Bug> bugsModel = FXCollections.observableArrayList();

    private Tester tester;

    private void initComboBox(){
        comboBoxGradRisc.getItems().addAll(BugRisk.values());
    }

    public void initializeTable() throws Exception {
        comboBoxGradRisc.getItems().clear();
        denumireTextField.clear();
        descriereTextField.clear();
        initComboBox();
        System.out.println("initializeTable");
        List<Bug> bugs = service.getAllBugs(this);
        bugs.sort(Comparator.comparingInt((Bug bug) -> getRiskValue(bug.getBugRisk())).reversed()
                .thenComparing(bug -> getStatusValue(bug.getBugStatus())));

        tableBug.getItems().clear();
        bugsModel.setAll(bugs);
        columnDescriere.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescriere()));
        columnDenumire.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDenumire()));
        columnGradRisc.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBugRisk()));
        columnStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBugStatus()));
        tableBug.setItems(bugsModel);

    }
    private int getStatusValue(String status) {
        return switch (status) {
            case "NEW" -> 0;
            case "SOLVED" -> 1;
            default -> -1;
        };
    }

    private int getRiskValue(String risk) {
        return switch (risk) {
            case "LOW" -> 0;
            case "MEDIUM" -> 1;
            case "HIGH" -> 2;
            default -> -1;
        };
    }

    @FXML
    void onAdaugaBugClick(ActionEvent event) throws Exception {
        String denumire = denumireTextField.getText();
        String descriere = descriereTextField.getText();
        String gradRisc = comboBoxGradRisc.getValue().toString();
        String status = BugStatus.NEW.toString();
        Bug bug = new Bug(denumire,descriere,gradRisc,status);
        service.addBug(bug, this);
        initializeTable();
    }


    public void setService(BugServiceInterface service) {
        this.service = service;
    }


    public void onLogoutButtonClick(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) tableBug.getScene().getWindow();
            service.logoutTester(tester, this);
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTester(Tester tester1) {
        this.tester = tester1;
    }
    @Override
    public void updateBug() throws Exception {
        Platform.runLater(() -> {
            try {
                System.out.println("UPDATE REFRESH");
                initializeTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
