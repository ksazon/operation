package patientOperation;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class Controller {
    public Label helloWorld;

    public void talkToMe(ActionEvent actionEvent) {
        helloWorld.setText("Kocham Cie");
    }
}
