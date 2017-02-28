package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valdis003 on 22.02.2017.
 */
public class DBEmu {
    private String fileName;
    private static DBEmu dbEmu = null;
    private Map<String, String> words = new HashMap<>();


    public Map<String, String> getWords() {
        return words;
    }

    public static DBEmu getSingleObject() throws IOException {
        if (dbEmu == null) {
            dbEmu = new DBEmu("E:/files/1");
        }
        return dbEmu;
    }

    private DBEmu(String fileName) throws IOException {
        this.fileName = fileName;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

        while (reader.ready()) {
            String line = reader.readLine();
            String[] lB = line.split(" : ");
            if (lB.length > 1) {
                words.put(lB[0], lB[1]);
            }
        }
    }

    public void writeNewWords(ArrayList<WordsPair> words) throws IOException {
        BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));

        for (Map.Entry<String, String> x : this.words.entrySet()) {
            bufferedWriter.write(x.getKey() + " : " + x.getValue() + "\n");
        }

        for (WordsPair x : words) {
            bufferedWriter.write(x.getWord() + " : " + x.getTranslate());
            bufferedWriter.write("\n");
        }
        bufferedWriter.close();

        for (WordsPair x : words) {
            this.words.put(x.getWord(), x.getTranslate());
        }
    }
}
