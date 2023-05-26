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

import java.util.List;
import java.util.stream.Collectors;

public class ProgramatorController implements BugObserverInterface {

    @FXML
    private TableColumn<Bug,String> columnDenumire;
    @FXML
    private TableColumn<Bug,String> columnDescriere;
    @FXML
    private TableColumn<Bug,String> columnGradRisc;
    @FXML
    private TableColumn<Bug,String> columnStatus;
    @FXML
    private  CheckBox checkboxNu;
    @FXML
    private  CheckBox checkboxCrescator;
    @FXML
    private  CheckBox checkboxDescrescator;
    @FXML
    private  Button updateButton;
    @FXML
    private TableView<Bug> tableBug;

    @FXML
    private CheckBox checkboxDa;

    private BugServiceInterface service;

    private final ObservableList<Bug> bugsModel = FXCollections.observableArrayList();

    private Programator programator;

    @FXML
    private void onSelectDa(ActionEvent actionEvent) {
        checkboxNu.setSelected(false);
        checkboxDa.setSelected(true);
    }

    public void setService(BugServiceInterface service) {
        this.service = service;
    }


    private void initializeColumns(List<Bug> bugs) {
        bugsModel.setAll(bugs);
        columnDescriere.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescriere()));
        columnDenumire.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDenumire()));
        columnGradRisc.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBugRisk().toString()));
        columnStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBugStatus().toString()));
        tableBug.setItems(bugsModel);
    }
    public void initializeTable() throws Exception {
        checkboxDa.setSelected(false);
        checkboxNu.setSelected(true);
        System.out.println("initializeTable");
        tableBug.getItems().clear();
        List<Bug> bugs = service.getAllBugs(this);
        initializeColumns(bugs);
    }



    public void onSelectCrescator(ActionEvent actionEvent) throws Exception {
        checkboxCrescator.setSelected(true);
        checkboxDescrescator.setSelected(false);
        tableBug.getItems().clear();
        List<Bug> bugs = service.getAllBugs(this);
        bugs.sort((o1, o2) -> {
            if (o1.getBugRisk().equals(o2.getBugRisk())) {
                return o1.getBugStatus().compareTo(o2.getBugStatus());
            }
            return o1.getBugRisk().compareTo(o2.getBugRisk());
        });
        initializeColumns(bugs);
    }

    public void onSelectDescrescator(ActionEvent actionEvent) throws Exception {
        checkboxCrescator.setSelected(false);
        checkboxDescrescator.setSelected(true);
        tableBug.getItems().clear();
        List<Bug> bugs = service.getAllBugs(this);
        bugs.sort((o1, o2) -> {
            if (o1.getBugRisk().equals(o2.getBugRisk())) {
                return o1.getBugStatus().compareTo(o2.getBugStatus());
            }
            return o2.getBugRisk().compareTo(o1.getBugRisk());
        });
        initializeColumns(bugs);
    }

    public void onUpdateButtonClick(ActionEvent actionEvent) {
        try {
            if(checkboxNu.isSelected()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Trebuie sa fii sigur ca rezolvi acest bug!");
                alert.showAndWait();
                return;
            }
            Bug bug = tableBug.getSelectionModel().getSelectedItem();
            if(bug.getBugStatus() == BugStatus.SOLVED){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Bug-ul a fost deja rezolvat!");
                alert.showAndWait();
                return;
            }
            // allow to update a bug ONLY if the risk is the highest from all the bugs
            if (!checkHighestRisk(bug))  {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Trebuie sa rezolvi bugul cu risk maxim!");
                alert.showAndWait();
                return;
            }
            if (checkboxDa.isSelected()) {
                bug.setBugStatus(BugStatus.SOLVED);
            }
            service.updateBug(bug, this);
            initializeTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkHighestRisk(Bug bug) throws Exception {

        List<Bug> bugs = service.getAllBugs(this);
        List<Bug> newBugs = bugs.stream().filter(bug1 -> bug1.getBugStatus().equals(BugStatus.NEW))
                .toList();
        BugRisk highestRisk = BugRisk.LOW;
        for(Bug b : newBugs) {
            if (b.getBugRisk().compareTo(highestRisk) > 0) {
                highestRisk = b.getBugRisk();
            }
        }
        System.out.println("Highest risk: " + highestRisk);
        System.out.println("Bug risk: " + bug.getBugRisk());
        return bug.getBugRisk() == highestRisk;
    }

    public void onSelectNu(ActionEvent actionEvent) {
        checkboxDa.setSelected(false);
        checkboxNu.setSelected(true);
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) tableBug.getScene().getWindow();
            service.logoutProgramator(programator, this);
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProgramator(Programator programator1) {
        this.programator = programator1;
    }
    @Override
    public void updateBug() throws Exception {
        Platform.runLater(() -> {
            try {
                System.out.println("UPDATE TABLE NOTIFY ALL");
                initializeTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
