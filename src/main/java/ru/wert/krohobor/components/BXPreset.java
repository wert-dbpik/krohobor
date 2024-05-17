package ru.wert.krohobor.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;
import ru.wert.krohobor.database.Preset;

import java.util.List;


public class BXPreset {

    private static Preset LAST_VAL;
    private ComboBox<Preset> cmbx;

    public void create(ComboBox<Preset> bxPreset, List<Preset> presets){
        this.cmbx = bxPreset;

        ObservableList<Preset> materials = FXCollections.observableArrayList(presets);
        bxPreset.setItems(materials);

        createCellFactory();
        //Выделяем префикс по умолчанию
        createConverter();

        bxPreset.getSelectionModel().select(0);

        if(LAST_VAL == null)
            LAST_VAL = presets.get(0);

    }

    private void createCellFactory() {
        //CellFactory определяет вид элементов комбобокса - только имя префикса
        cmbx.setCellFactory(i -> new ListCell<Preset>() {
            @Override
            protected void updateItem (Preset item,boolean empty){
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }

        });
    }

    private void createConverter() {
        cmbx.setConverter(new StringConverter<Preset>() {
            @Override
            public String toString(Preset Preset) {
                LAST_VAL = Preset; //последний выбранный префикс становится префиксом по умолчанию
                return Preset.getName();
            }

            @Override
            public Preset fromString(String string) {
                return null;
            }
        });
    }
}
