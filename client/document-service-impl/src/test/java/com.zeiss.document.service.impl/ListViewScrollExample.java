package com.zeiss.document.service.impl;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListViewScrollExample extends Application {

    private ListView<TextFlow> listView;

    @Override
    public void start(Stage stage) throws Exception {
        listView = new ListView<>();

        String wordToFind = "ma";

        String item = "Adam Martin madam";

        Pattern pattern = Pattern.compile(wordToFind, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(item);

        List<IndexWrapper> wrappers = new ArrayList<IndexWrapper>();

        while (matcher.find()) {
            int end = matcher.end();
            int start = matcher.start();
            IndexWrapper wrapper = new IndexWrapper(start, end);
            wrappers.add(wrapper);
        }

        TextFlow flow = new TextFlow();

        wrappers.forEach(indexWrapper -> {
            String substring = item.substring(indexWrapper.getStart(), indexWrapper.getEnd());
            System.out.println(substring);
            String[] split = item.split(substring, Pattern.CASE_INSENSITIVE);
            for (String s : split) {
                Text text = new Text(s);
                text.setStyle("-fx-font-weight: regular");
                flow.getChildren().addAll(text);
            }

            Text text2 = new Text(substring);
            text2.setStyle("-fx-font-weight: bold");
            text2.setFill(Color.BLUE);
            flow.getChildren().addAll(text2);
            System.out.println(split);

        });




        item.split(wordToFind, Pattern.CASE_INSENSITIVE);




        listView.setItems(FXCollections.observableArrayList(flow));

        Scene scene = new Scene(listView);
        stage.setScene(scene);
        stage.show();
    }

    public void addItem(String item) {
        Label label = new Label(item);
        label.setWrapText(true);
        label.maxWidthProperty().bind(listView.widthProperty());
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}