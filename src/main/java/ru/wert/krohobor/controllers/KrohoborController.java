package ru.wert.krohobor.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import lombok.Getter;
import ru.wert.krohobor.components.BXPreset;
import ru.wert.krohobor.components.TFDoubleColored;
import ru.wert.krohobor.components.TFIntegerColored;
import ru.wert.krohobor.database.Preset;
import ru.wert.krohobor.database.PresetService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class KrohoborController {


    @FXML@Getter
    private TextField tfInput; //Поле ввода

    @FXML private TextField tfCutting; //Резка

    @FXML private Label lbCutting;

    @FXML private TextField tfBending; //Гибка

    @FXML private Label lbBending;

    @FXML private TextField tfWelding; //Сварка

    @FXML private Label lbWelding;

    @FXML private TextField tfLocksmith; //Слесарка

    @FXML private Label lbLocksmith;

    @FXML private TextField tfMechanic; //Мехобработка

    @FXML private Label lbMechanic;

    @FXML private Label lblSum;

    @FXML@Getter private ComboBox<Preset> cmbxPreset; //Выбор шаблона



    @FXML Button btnSavePresetAs;
    @FXML Button btnDeletePreset;
    @FXML Button btnUpdatePreset;

    public static final String DOUBLE_FORMAT = "%5.3f";
    public static final String INTEGER_FORMAT = "%10.0f";

    IntegerProperty cuttingProp = new SimpleIntegerProperty();
    IntegerProperty bendingProp = new SimpleIntegerProperty();
    IntegerProperty weldingProp = new SimpleIntegerProperty();
    IntegerProperty locksmithProp = new SimpleIntegerProperty();
    IntegerProperty mechanicProp = new SimpleIntegerProperty();

    boolean status100;

    private List<TextField> percentFields ;

    private Preset currentPreset;

    public void init(){
        percentFields = Arrays.asList(tfCutting, tfBending, tfWelding, tfLocksmith, tfMechanic);

        tfCutting.textProperty().bindBidirectional(cuttingProp, new NumberStringConverter());
        tfBending.textProperty().bindBidirectional(bendingProp, new NumberStringConverter());
        tfWelding.textProperty().bindBidirectional(weldingProp, new NumberStringConverter());
        tfLocksmith.textProperty().bindBidirectional(locksmithProp, new NumberStringConverter());
        tfMechanic.textProperty().bindBidirectional(mechanicProp, new NumberStringConverter());

        List<Preset> presets = new ArrayList<>();
        presets.add(new Preset(0L, "NONE", 20,20,20,20,20));
        presets.addAll(PresetService.getInstance().findAll());

        new BXPreset().create(cmbxPreset, presets);
        new TFDoubleColored(tfInput, this);

        for(TextField tf : percentFields) tf.setId("percent-field");

        new TFIntegerColored(tfCutting, this);
        new TFIntegerColored(tfBending, this);
        new TFIntegerColored(tfWelding, this);
        new TFIntegerColored(tfLocksmith, this);
        new TFIntegerColored(tfMechanic, this);

        loadPreset(presets.get(0));
        countNorms();

        cmbxPreset.setOnAction(e->{
            currentPreset = cmbxPreset.getValue();
            loadPreset(currentPreset);
        });

        tfInput.editableProperty().addListener(observable -> {
            countNorms();
        });

        tfInput.setText(format(DOUBLE_FORMAT, 0.0));

        btnSavePresetAs.setOnAction(e->{
            if(!status100) return;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preset.fxml"));
                Parent parent = loader.load();
                PresetController controller = loader.getController();
                controller.init(this,
                        cuttingProp.get(),
                        bendingProp.get(),
                        weldingProp.get(),
                        locksmithProp.get(),
                        mechanicProp.get());
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.setResizable(false);
                stage.setTitle("Сохранение шаблона");
                stage.initOwner(((Node)e.getSource()).getScene().getWindow());
                stage.showAndWait();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        btnDeletePreset.setOnAction(e->{
            if(currentPreset != null && status100){
                PresetService.getInstance().delete(currentPreset);

                cmbxPreset.getItems().remove(currentPreset);
                cmbxPreset.getSelectionModel().select(0);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                String s = format("Шаблон '%s' успешно удален!", currentPreset.getName());
                alert.setContentText(s);
                alert.showAndWait();
            }

        });

        btnUpdatePreset.setOnAction(e->{
            if(currentPreset != null && status100){
                currentPreset.setCutting(cuttingProp.get());
                currentPreset.setBending(bendingProp.get());
                currentPreset.setWelding(weldingProp.get());
                currentPreset.setLocksmith(locksmithProp.get());
                currentPreset.setMechanic(mechanicProp.get());

                PresetService.getInstance().update(currentPreset);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                String s = format("Шаблон '%s' успешно переписан!", currentPreset.getName());
                alert.setContentText(s);
                alert.showAndWait();
            }

        });
    }

    public void loadPreset(Preset preset){
        cuttingProp.set(preset.getCutting());
        bendingProp.set(preset.getBending());
        weldingProp.set(preset.getWelding());
        locksmithProp.set(preset.getLocksmith());
        mechanicProp.set(preset.getMechanic());

        countNorms();

    }

    public void countNorms(){
        String format = DOUBLE_FORMAT;
        String text = tfInput.getText().trim().replace(",", ".");

        double input = tfInput.getText().isEmpty() ? 0.0 : Double.parseDouble(text);
        lbCutting.setText(format(format,input * Integer.parseInt(tfCutting.getText().trim())*0.01f).trim());
        lbBending.setText(format(format,input * Integer.parseInt(tfBending.getText().trim())*0.01f).trim());
        lbWelding.setText(format(format,input * Integer.parseInt(tfWelding.getText().trim())*0.01f).trim());
        lbLocksmith.setText(format(format,input * Integer.parseInt(tfLocksmith.getText().trim())*0.01f).trim());
        lbMechanic.setText(format(format,input * Integer.parseInt(tfMechanic.getText().trim())*0.01f).trim());

        checkUp100();
    }

    public void checkUp100() {
        int sum = Integer.parseInt(tfCutting.getText().trim()) +
                Integer.parseInt(tfBending.getText().trim()) +
                Integer.parseInt(tfWelding.getText().trim()) +
                Integer.parseInt(tfLocksmith.getText().trim()) +
                Integer.parseInt(tfMechanic.getText().trim());

        lblSum.setText(String.valueOf(sum));

        if (sum != 100)
            for (TextField tf : percentFields) {
                tf.setStyle("-fx-background-color: lightpink");
                lblSum.setStyle("-fx-background-color: lightpink");
                status100 = false;
            } else
            for (TextField tf : percentFields) {
                tf.setStyle("-fx-background-color: transparent");
                lblSum.setStyle("-fx-background-color: transparent");
                status100 = true;
            }
    }



}

