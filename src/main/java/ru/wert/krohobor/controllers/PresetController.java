package ru.wert.krohobor.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.wert.krohobor.database.Preset;
import ru.wert.krohobor.database.PresetService;

public class PresetController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOk;

    @FXML
    private TextField tfName;

    public void init(KrohoborController krohoborController, int cutting, int bending, int welding, int locksmith, int mechanic){

        Preset preset = new Preset();
        ComboBox<Preset> cmbxPreset =  krohoborController.getCmbxPreset();

        preset.setCutting(cutting);
        preset.setBending(bending);
        preset.setWelding(welding);
        preset.setLocksmith(locksmith);
        preset.setMechanic(mechanic);

        btnCancel.setOnAction(e->{
            ((Node)e.getSource()).getScene().getWindow().hide();
        });

        btnOk.setOnAction(e->{
            String name = tfName.getText().trim();
            Preset foundPreset = PresetService.getInstance().findByName(name);
            if(foundPreset == null){
                preset.setName(name);
                Preset savedPreset = PresetService.getInstance().save(preset);
                if(savedPreset != null){
                    cmbxPreset.getItems().add(savedPreset);
                    cmbxPreset.getSelectionModel().select(savedPreset);
                    ((Node)e.getSource()).getScene().getWindow().hide();
                }  else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    String s = "Что-то пошло не так./nСохранение шаблона провалилось!";
                    alert.setContentText(s);
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Внимание!");
                String s = "Шаблон с таким именем уже существует!";
                alert.setContentText(s);
                alert.showAndWait();
            }

        });



    }
}
