public class Data {

    private Double[] theta = new Double[4];
    private Double[] d_theta = new Double[4];

    private Double bias;
    private Double d_bias;

    private Double error = 0.0;
    private Double target;
    private Double sigmoid;
    private Double [] category;

    private Double prediction;

    private Double correctPrediction = 0.0;


    public void setCorrectPrediction(int correctPrediction) {
        this.correctPrediction += correctPrediction;
    }

    public void resetError(){
        this.error = 0.0;
    }

    public void resetCorrectPrediction(){
        this.correctPrediction = 0.0;
    }

    public void setD_bias(Double d_bias) {
        this.d_bias = d_bias;
    }

    public void setD_theta(Double[] d_theta) {
        this.d_theta = d_theta;
    }

    public void setCategory(Double[] category) {
        this.category = category;
    }

    public void setPrediction(Double prediction) {
        this.prediction = prediction;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public void setSigmoid(Double sigmoid) {
        this.sigmoid = sigmoid;
    }

    public void setTotalError(Double error){
        this.error += error;
    }

    public void setTheta (Double[] theta){
        this.theta = theta;
    }

    public void setBias (Double bias){
        this.bias = bias;
    }

    public Double[] getTheta(){
        return this.theta;
    }

    public Double getError() {
        return this.error;
    }

    public Double getBias() {
        return bias;
    }

    public Double getTarget() {
        return target;
    }

    public Double [] getCategory (){
        return this.category;
    }

    public Double getSigmoid() {
        return sigmoid;
    }

    public Double getD_bias() {
        return d_bias;
    }

    public Double[] getD_theta() {
        return d_theta;
    }

    public Double getPrediction(){
        return this.prediction;
    }

    public Double getCorrectPrediction() {
        return this.correctPrediction;
    }
}
