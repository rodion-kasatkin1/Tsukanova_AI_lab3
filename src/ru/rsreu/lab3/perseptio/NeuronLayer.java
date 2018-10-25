package ru.rsreu.lab3.perseptio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuronLayer {
    private int countInput;
    private int countoutput;
    private double[][] weightCoef;
    private double[][] deltaWeightCoef;
    private List<Double> output;
    private List<Double> input;



    public NeuronLayer(int countInput, int countoutput) {
        output = new ArrayList<>();
        input = new ArrayList<>();
        this.countInput = countInput+1; // +1 потому что нужно учесть w0;
        this.countoutput = countoutput;
        weightCoef = new double[countoutput][this.countInput];
        deltaWeightCoef = new double[countoutput][this.countInput];
        Random rnd = new Random();
        for (int i = 0; i < countoutput; i++) {
            for (int j = 0; j < this.countInput; j++) {
                weightCoef[i][j] = rnd.nextDouble();
            }
        }
    }

    public int getCountInput() {
        return countInput;
    }

    public int getCountoutput() {
        return countoutput;
    }

    public void setCountInput(int countInput) {
        this.countInput = countInput;
    }

    public void setCountoutput(int countoutput) {
        this.countoutput = countoutput;
    }

    public double[][] getWeightCoef() {
        return weightCoef;
    }

    public double[][] getDeltaWeightCoef() {
        return deltaWeightCoef;
    }

    public void setDeltaWeightCoef(double[][] deltaWeightCoef) {
        this.deltaWeightCoef = deltaWeightCoef;
    }

    public List<Double> getOutput() {
        return output;
    }

    public void setOutput(List<Double> output) {
        this.output = output;
    }

    public List<Double> getInput() {
        return input;
    }

    public void setInput(List<Double> input) {
        this.input = input;
    }
    @Override
    public String toString() {
        String result="[";
        for (int i = 0; i < output.size(); i++) {
            result+=Math.round(output.get(i));
            result+=", ";
        }
        result+="]";
        return result;
    }

}
