package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Valdis003 on 26.02.2017.
 */
public class WordsPair {
    private StringProperty word = new SimpleStringProperty();
    private StringProperty translate = new SimpleStringProperty();

    public WordsPair(String word, String translate) {
        this.word = new SimpleStringProperty(word);
        this.translate = new SimpleStringProperty(translate);
    }

    public String getWord() {
        return word.get();
    }

    public StringProperty wordProperty() {
        return word;
    }

    public void setWord(String word) {
        this.word.set(word);
    }

    public String getTranslate() {
        return translate.get();
    }

    public StringProperty translateProperty() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate.set(translate);
    }
}
