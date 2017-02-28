package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.DBEmu;
import sample.WordsPair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Valdis003 on 27.02.2017.
 */
public class FillVocabularyController {
    @FXML
    TextArea textArea;
    @FXML
    TextField textField;

    Map<String, String> vocabulary;
    ArrayList<String> textWords = new ArrayList<>();
    ArrayList<String> familiarWords = new ArrayList<>();
    DBEmu dbEmu;
    public void initialize() throws IOException {
        dbEmu = DBEmu.getSingleObject();
        vocabulary = dbEmu.getWords();
    }

    @FXML
    public void addBtnClick() throws IOException {
        String regex = "([\\w']+)";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(textArea.getText());

        while (matcher.find()) {
            textWords.add(matcher.group(1));
        }

        matcher = pattern.matcher(textField.getText());

        while (matcher.find()) {
            familiarWords.add(matcher.group(1));
        }

        for (String x : familiarWords) {
            if (textWords.contains(x)) {
                textWords.remove(x);
            }
        }

        ArrayList<WordsPair> list = ScanController.translateWords(textWords);
        dbEmu.writeNewWords(list);
    }
}
