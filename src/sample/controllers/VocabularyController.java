package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DBEmu;
import sample.WordsPair;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Valdis003 on 25.02.2017.
 */
public class VocabularyController {
    @FXML
    private TableView table;
    @FXML
    private TableColumn<WordsPair, String> word;
    @FXML
    private TableColumn<WordsPair, String> translation;
    private Map<String, String> words;
    private ObservableList<WordsPair> list;

    public void initialize() throws IOException {
        list = FXCollections.observableArrayList();
        words = DBEmu.getSingleObject().getWords();

        table.getColumns().setAll(word, translation);

        for (Map.Entry<String, String> x : words.entrySet()) {
            WordsPair current = new WordsPair(x.getKey(), x.getValue());
            list.add(current);
        }

        word.setCellValueFactory(new PropertyValueFactory<WordsPair, String>("word"));
        translation.setCellValueFactory(new PropertyValueFactory<WordsPair, String>("translate"));

        table.setItems(list);
    }
}
