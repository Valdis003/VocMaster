package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.DBEmu;

import java.io.IOException;

/**
 * Created by Valdis003 on 24.02.2017.
 */
public class MainController {
//TODO: Filling of the voc with text
    private static DBEmu db;

    @FXML
    public void initialize() throws IOException {
        db = DBEmu.getSingleObject();
    }

    public static DBEmu getDb() {
        return db;
    }

    protected Stage openNewModalWin(ActionEvent event, String fxmlFile, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.show();
        return stage;
    }

    public void openFillVocabularyWin(ActionEvent event) throws IOException {
        openNewModalWin(event, "../fxml/fillVocabularyWin.fxml", "Fill vocabulary");
    }

    @FXML
    public void openScanWin(ActionEvent event) throws IOException {
        openNewModalWin(event, "../fxml/scanWin.fxml", "Scan");
        ScanController.setMainController(this);
    }

    public void openSettingsWin(ActionEvent event) throws IOException {
//        openNewModalWin(event, "fxml/settingsWin.fxml", "Settings");
        //TODO: Settings modal windows don't work with menu item
    }

    public void openVocabularyWin(ActionEvent event) throws IOException {
        openNewModalWin(event, "../fxml/vocabularyWin.fxml", "Vocabulary");
    }
}
