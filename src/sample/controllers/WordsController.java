package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.WordsPair;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Valdis003 on 24.02.2017.
 */
public class WordsController {
    @FXML
    private TableView table;

    @FXML
    private TableColumn<WordsPair, String> word;
    @FXML
    private TableColumn<WordsPair, String> translation;
    private ObservableList<WordsPair> list;

    @FXML
    public void initialize() {
        table.getColumns().setAll(word, translation);
        list = FXCollections.observableArrayList(ScanController.getWords());

        word.setCellValueFactory(new PropertyValueFactory<WordsPair, String>("word"));
        translation.setCellValueFactory(new PropertyValueFactory<WordsPair, String>("translate"));

        table.setItems(list);
    }

    @FXML
    public void exportToCSV() throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:/files/2.csv")));
        for (WordsPair x : list) {
            writer.write(x.getWord() + ";" + x.getTranslate());
            writer.write('\n');
        }
        writer.close();
    }
}
