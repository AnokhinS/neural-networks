package sample;

import javafx.scene.image.WritableImage;

import java.math.BigDecimal;

public class Neuron {
    double[][] weights;
    int[][] boolMatrix;
    double V = 1;

    public double sum(WritableImage img){
        if(weights == null){
            weights = randomWeights((int) img.getWidth(),(int) img.getHeight());
        }
        boolMatrix = Utils.createBoolMatrix(img);

        double res = 0;
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                res += weights[i][j] * boolMatrix[i][j];
            }
        }
        System.out.println(res);
        return res > 0 ? 1:0;
    }
    private double[][] randomWeights(int width, int height){
        double[][] result = new double[height][width];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = BigDecimal.valueOf(-0.5+Math.random()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }
        return result;
    }

    public void learnNeuron(double delta){
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = weights[i][j] + V * delta * boolMatrix[i][j];
            }
        }
    }

}
