package ru.wert.krohobor.components;

import javafx.scene.control.TextField;
import ru.wert.krohobor.controllers.KrohoborController;

/**
 * Поле допускает ввод [0-9]
 */
public class TFIntegerColored {

    public TFIntegerColored(TextField tf, KrohoborController controller) {
        String style = tf.getStyle();
        String normStyle = controller == null ? "" : controller.getTfInput().getStyle();

        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("")) {
                tf.setStyle("-fx-border-color: #FF5555");
                if (controller != null) {
                    controller.getTfInput().setStyle("-fx-border-color: #FF5555");
                }
                return;
            }
            tf.setStyle(style);
            if (controller != null) {
                controller.getTfInput().setStyle(normStyle);
//                controller.countNorm(controller.getOpData());
            }
            controller.countNorms();
//            controller.checkUp100();
        });

        tf.setOnKeyTyped(e->{
            if(tf.isFocused() && !e.getCharacter().matches("[0-9]")) {
                e.consume();
            }
        });
    }
}
