package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import sample.DBEmu;
import sample.WordsPair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Created by Valdis003 on 24.02.2017.
 */
public class ScanController {
    private static MainController mainController;
    @FXML
    private TextArea tArea;

    private static ArrayList<WordsPair> list = new ArrayList<>();

    public static ArrayList<WordsPair> getWords() {
        return list;
    }

    public static void setMainController(MainController mainController) {
        ScanController.mainController = mainController;
    }

    private DBEmu dB;
    private Map<String, String> usersVocabulary;

    @FXML
    public void initialize() throws IOException {
        dB = DBEmu.getSingleObject();
    }

    public static ArrayList<WordsPair> translateWords(ArrayList<String> beforeTranslation) {
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
        ArrayList<WordsPair> list = new ArrayList<>();

        int wordsCount = 0;
        String request = "";
        for (String x : beforeTranslation) {
            if (wordsCount < 15) {
                request += x + ".";
                wordsCount++;
            } else {
                wordsCount = 0;
                String response = executePost(url, request);
                String[] listTranslate = responseParser(response);
                String[] listWords = request.split("\\.");

                for (int i = 0; i < listTranslate.length; i++) {
                    System.out.println(listWords[i] + " " + listTranslate[i]);
                    list.add(new WordsPair(listWords[i], listTranslate[i]));
                }
                request = "";
            }
        }

        if (beforeTranslation.size() < 15) {
            String response = executePost(url, request);
            String[] listTranslate = responseParser(response);
            String[] listWords = request.split("\\.");

            for (int i = 0; i < listTranslate.length; i++) {
                System.out.println(listWords[i] + " " + listTranslate[i]);
                list.add(new WordsPair(listWords[i], listTranslate[i]));
            }
        }
        return list;
    }

    @FXML
    public void scanAction(ActionEvent actionEvent) throws IOException {
        usersVocabulary = dB.getWords();

        String regex = "([\\w']+)";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(tArea.getText());

        ArrayList<String> foundedWords = new ArrayList<>();

        while (matcher.find()) {
            if (!usersVocabulary.containsKey(matcher.group(1))
                    && (!foundedWords.contains(matcher.group(1))))
                foundedWords.add(matcher.group(1));
        }

        list = translateWords(foundedWords);
        openWordsWin(actionEvent);
    }

    private void openWordsWin(ActionEvent event) throws IOException {
        mainController.openNewModalWin(event, "../fxml/wordsWin.fxml", "Words");
    }

    public static String executePost(String targetURL, String text) {
        HttpURLConnection connection = null;

        String key = "trnsl.1.1.20161025T064753Z.5642f4005c41f9c5.abea688a7cfc18342faba9ae7f1fad0347c0b44a";

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            String urlParameters = "key=" + key + "&text=" + text + "&lang=en-ru";

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String[] responseParser(String response) {
        int index = response.indexOf("text");

        String lineWords = response.substring(index + 8, response.length() - 4);
        String[] words = lineWords.split("\\.");

        return words;
    }
}
