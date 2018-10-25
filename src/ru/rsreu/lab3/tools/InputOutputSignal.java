package ru.rsreu.lab3.tools;

import java.util.ArrayList;
import java.util.List;

public class InputOutputSignal {

    private List<Double> input;
    private List<Double> output;
    private double error;



    public InputOutputSignal(String inputString, String outputString) {
        input = new ArrayList<>();
        String[] inputStringArray = inputString.split("");
        for (int i = 0; i < inputStringArray.length; i++) {
            input.add((double)Integer.parseInt(inputStringArray[i]));
        }

        output = new ArrayList<>();
        String[] outputStringArray = outputString.split("");
        for (int i = 0; i < outputStringArray.length; i++) {
            output.add((double)Integer.parseInt(outputStringArray[i]));
        }

    }

    public List<Double> getInput() {
        return input;
    }

    public void setInput(List<Double> input) {
        this.input = input;
    }

    public List<Double> getOutput() {
        return output;
    }

    public void setOutput(List<Double> output) {
        this.output = output;
    }
    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

}
