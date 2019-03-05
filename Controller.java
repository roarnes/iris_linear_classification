// Implementation of Linear classifier of Iris Data Set
// Created by Arnes Respati Putri on February 27 2019
// Controller class
// LEARNING RATE = 0.8 AND 0.1

import java.util.ArrayList;
import java.lang.Math;
import java.util.Objects;

public class Controller {
    private ArrayList<Double> sepal_length;
    private ArrayList<Double> sepal_width;
    private ArrayList<Double> petal_length;
    private ArrayList<Double> petal_width;

    private Double learningRate;

    private Data data1 = new Data();
    private Data data2 = new Data();
    private mLineChart lineChart;

    private Double[] acc1, err1, err2;

    public Controller(mLineChart lineChart, ArrayList<Double> sepal_length, ArrayList<Double> sepal_width, ArrayList<Double> petal_length, ArrayList<Double> petal_width) {
        this.lineChart = lineChart;
        this.sepal_length = sepal_length;
        this.sepal_width = sepal_width;
        this.petal_length = petal_length;
        this.petal_width = petal_width;
    }

    public void classify(int epoch, Double learningRate){
        this.learningRate = learningRate;

        acc1 = new Double[epoch];
        err1 = new Double[epoch];
        err2 = new Double[epoch];

        //give random values for the first iteration to theta and bias
        randomize();

        //Manual theta and bias input for testing
//        Double[] theta1 = {0.8478181837, 0.9697483372, 0.1190197063, 0.6618576636};
//        Double[] theta2 = {0.03877398397, 0.392117899, 0.1377062965, 0.7458124254};
//        Double bias1 = 0.5931691469;
//        Double bias2 = 0.1100806592;

//        data1.setTheta(theta1);
//        data2.setTheta(theta2);
//        data1.setBias(bias1);
//        data2.setBias(bias2);

        //CATEGORY
        //00 -> setosa
        //01 -> versicolor
        //10 -> virginica
        Double[] cat = {0.0, 0.0, 1.0}, cate = {0.0, 0.1, 0.0};
        data1.setCategory(cat);
        data2.setCategory(cate);

        //epoch iteration
        for (int i = 0; i < epoch ; i++) {
            System.out.println("EPOCH: " + (i+1));
            //read per line
            //save the last value of theta and bias
            for (int j = 0; j < sepal_length.size() ; j++) {

                //FINDING TARGET
                data1.setTarget(findTarget(data1.getTheta(), data1.getBias(), j));
                data2.setTarget(findTarget(data2.getTheta(), data2.getBias(), j));
//                System.out.println(data1.getTarget());
//                System.out.println(data2.getTarget());

                //FINDING SIGMOID
                data1.setSigmoid(findSigmoid(data1.getTarget()));
                data2.setSigmoid(findSigmoid(data2.getTarget()));
//                System.out.println(data1.getSigmoid());
//                System.out.println(data2.getSigmoid());

                //FIND D THETA
                data1.setD_theta(findDTheta(j, data1.getSigmoid(), data1.getCategory()));
                data2.setD_theta(findDTheta(j, data2.getSigmoid(), data2.getCategory()));

                //FINDING D BIAS
                data1.setD_bias(findDBias(j, data1.getSigmoid(), data1.getCategory()));
                data2.setD_bias(findDBias(j, data2.getSigmoid(), data2.getCategory()));

                //update theta for next iteration
                data1.setTheta(updateTheta(data1.getTheta(), data1.getD_theta()));
                data2.setTheta(updateTheta(data2.getTheta(), data2.getD_theta()));

                //update bias for next iteration
                data1.setBias(updateBias(data1.getBias(), data1.getD_bias()));
                data2.setBias(updateBias(data2.getBias(), data2.getD_bias()));

                //FINDING PREDICTION
                data1.setPrediction(findPrediction(data1.getSigmoid()));
                data2.setPrediction(findPrediction(data2.getSigmoid()));
//                System.out.println(data1.getPrediction());
//                System.out.println(data2.getPrediction());

                //CHECK WHETHER PREDICTION IS CORRECT
                data1.setCorrectPrediction(findAccuracy(j, data1.getCategory(), data2.getCategory(), data1.getPrediction(), data2.getPrediction()));
                data2.setCorrectPrediction(findAccuracy(j, data1.getCategory(), data2.getCategory(), data1.getPrediction(), data2.getPrediction()));

                //FINDING ERROR
                Double error1 = findError(j, data1.getCategory(), data1.getSigmoid());
                Double error2 = findError(j, data2.getCategory(), data2.getSigmoid());
                data1.setTotalError(error1);
                data2.setTotalError(error2);
            }
            //SAVING AND PRINTING ERROR OF EACH EPOCH
            err1[i] = data1.getError()/sepal_width.size();
            err2[i] = data2.getError()/sepal_width.size();
            System.out.println("ERROR 1: " + (err1[i]));
            System.out.println("ERROR 2: " + (err2[i]));

            //SAVING AND PRINTING ACCURACY OF EACH EPOCH
            acc1[i] = data1.getCorrectPrediction()/sepal_width.size();
//            acc2[i] = data2.getCorrectPrediction()/sepal_width.size();
            System.out.println("ACCURACY : " + (acc1[i]));
            System.out.println("_____________________________________________");

            data1.resetError();
            data2.resetError();
            data1.resetCorrectPrediction();
            data2.resetCorrectPrediction();
        }
//        lineChart.setAcc(acc1, acc2);
    }

    public int getDataSize(){
        return this.sepal_length.size();
    }

    public void randomize(){
        Double [] theta1 = new Double[4];
        Double [] theta2 = new Double[4];

        for (int i = 0; i < 4 ; i++) {
            theta1[i] = Math.random();
            theta2[i] = Math.random();
            System.out.println(theta1[i]);
            System.out.println(theta2[i]);
        }

        data1.setTheta(theta1);
        data2.setTheta(theta2);
        data1.setBias(Math.random());
        data2.setBias(Math.random());
    }

    public Double findTarget(Double[] theta, Double bias, int lineIndex){
        Double target = 0.0;
        target = bias;

        target += (theta[0]*sepal_length.get(lineIndex)) + (theta[1]*sepal_width.get(lineIndex))
                + (theta[2]*petal_length.get(lineIndex)) + (theta[3]*petal_width.get(lineIndex));

        return target;
    }

    public Double findSigmoid(Double target){
        return (1 / (1 + Math.exp(-target)));
    }

    public Double findPrediction(Double sigmoid){
        Double prediction;
        if (sigmoid < 0.5 ){
            prediction = 0.0;
        }
        else prediction = 1.0;

        return prediction;
    }

    //category-sigmoid

    public Double findError(int index, Double[] cat, Double sigmoid){
        Double error = 0.0;

        if (index < 50) {
            error = Math.pow(Math.abs(cat[0]-sigmoid), 2);
//            System.out.println("in if: " + 1);
        }
        else if (index > 100) {
            error = Math.pow(Math.abs(cat[2]-sigmoid), 2);
//            System.out.println("in if: " + 2);
        }
        else {
            error = Math.pow(Math.abs(cat[1]-sigmoid), 2);
//            System.out.println("in if: " + 3);
        }
        return error;
    }

    public Double[] updateTheta(Double[] thetaOld, Double[] d_theta){
        Double [] thetaNew = new Double [4];
        for (int i = 0; i < 4 ; i++) {
            thetaNew[i] = thetaOld[i] - (learningRate * d_theta[i]);
        }

        return thetaNew;
    }

    public Double updateBias(Double biasOld, Double d_bias){
        Double biasNew = 0.0;

        biasNew = biasOld - (learningRate * d_bias);

        return biasNew;
    }

    public Double[] findDTheta (int lineIndex, Double sigmoid, Double[]cat) {
        Double[] d_theta = new Double[4];
        Double category;

        if (lineIndex < 50){
            category = cat[0];
        }
        else if (lineIndex > 100) {
            category = cat[2];
        }
        else {
            category = cat[1];
        }

        d_theta[0] = 2 * (sigmoid - category) * (1 - sigmoid) * sigmoid * sepal_length.get(lineIndex);
        d_theta[1] = 2 * (sigmoid - category) * (1 - sigmoid) * sigmoid * sepal_width.get(lineIndex);
        d_theta[2] = 2 * (sigmoid - category) * (1 - sigmoid) * sigmoid * petal_length.get(lineIndex);
        d_theta[3] = 2 * (sigmoid - category) * (1 - sigmoid) * sigmoid * petal_width.get(lineIndex);

        return  d_theta;
    }

    public Double findDBias(int lineIndex, Double sigmoid, Double[]cat){
        Double d_bias;

        Double category;
        if (lineIndex < 50){
            category = cat[0];
        }
        else if (lineIndex > 100) {
            category = cat[2];
        }

        else {
            category = cat[1];
        }

        d_bias = 2*(sigmoid - category) * (1 - sigmoid) * sigmoid;

        return d_bias;
    }

    //PREDICTION/150
    public int findAccuracy (int lineIndex, Double[] cat1, Double[] cat2,
                             Double prediction1, Double prediction2){
        int counter = 0;

        if (lineIndex < 50){
            if (Objects.equals(cat1[0], prediction1) && Objects.equals(cat2[0], prediction2)){
                counter = 1;
            }
        }
        else if (lineIndex > 100) {
            if (Objects.equals(cat1[2], prediction1) && Objects.equals(cat2[0], prediction2)){
                counter = 1;
            }
        }
        else {
            if (Objects.equals(cat1[1], prediction1) && Objects.equals(cat2[0], prediction2)){
                counter = 1;
            }
        }
        return counter;
    }

    public Double[] getAcc1 (){
        return this.acc1;
    }

    public Double[] getErr1() {
        return err1;
    }

    public Double[] getErr2() {
        return err2;
    }
}
