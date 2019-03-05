// Implementation of Linear classifier of Iris Data Set
// Created by Arnes Respati Putri on February 27 2019
// Main class

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class mLineChart extends Application {
    public ArrayList<Double> sepal_length = new ArrayList<>();
    public ArrayList<Double> sepal_width = new ArrayList<>();
    public ArrayList<Double> petal_length = new ArrayList<>();
    public ArrayList<Double> petal_width = new ArrayList<>();

    public Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mLineChart main = new mLineChart();


        String filename = "/Users/arnes/Desktop/Codes/JAVA/IRIS/out/production/IRIS/iris.csv";
        main.readCSV(filename);

        controller = new Controller(main, main.sepal_length, main.sepal_width, main.petal_length, main.petal_width);
        controller.classify(100, 0.1);

        initAccuracy();
        initError();
    }

    public void initAccuracy(){
        Stage stage = new Stage();
        stage.setTitle("Accuracy Chart");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Epoch");
        yAxis.setLabel("Accuracy");

        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Accuracy Chart");

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();

        series1.setName("Y1");
        series2.setName("Y2");

        for (int i = 0; i < 100 ; i++) {
            series1.getData().add(new XYChart.Data(String.valueOf(i+1), controller.getAcc1()[i]));
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series1, series2);

        stage.setScene(scene);
        stage.show();
    }

    public void initError(){
        Stage stage = new Stage();
        stage.setTitle("Error Chart");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Epoch");
        yAxis.setLabel("Error");

        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Error Chart");

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();

        series1.setName("Y1");
        series2.setName("Y2");

        for (int i = 0; i < 100 ; i++) {
            series1.getData().add(new XYChart.Data(String.valueOf(i+1), controller.getErr1()[i]));
            series2.getData().add(new XYChart.Data(String.valueOf(i+1), controller.getErr2()[i]));
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series1, series2);

        stage.setScene(scene);
        stage.show();
    }

    private void readCSV(String file){

        //adapted from post by App Shah on July 16th 2017
        // https://crunchify.com/how-to-read-convert-csv-comma-separated-values-file-to-arraylist-in-java-using-split-operation/
        //Retrieved February 28th 2019

        BufferedReader bufferedReader = null;

        try {
            String line;
            bufferedReader = new BufferedReader(new FileReader(file));

            //Read file in java line by line
            while ((line = bufferedReader.readLine()) != null) {
                String [] splitLine = line.split(",");

                sepal_length.add(Double.valueOf(splitLine[0]));
                sepal_width.add(Double.valueOf(splitLine[1]));
                petal_length.add(Double.valueOf(splitLine[2]));
                petal_width.add(Double.valueOf(splitLine[3]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }
    }
}
