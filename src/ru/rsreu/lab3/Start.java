package ru.rsreu.lab3;

import ru.rsreu.lab3.perseptio.NeuronLayer;
import ru.rsreu.lab3.perseptio.Perseptron;
import ru.rsreu.lab3.tools.InputOutputSignal;
import ru.rsreu.lab3.tools.InputOutputSignalGenerator;

import java.util.List;

public class Start {
    public static void main(String[] args) {
        Perseptron perseptron = new Perseptron(0.5);
        perseptron.addNeuronLayer(new NeuronLayer(24,16));
        perseptron.addNeuronLayer(new NeuronLayer(16,10));

        perseptron.teach(InputOutputSignalGenerator.getTeachingSignals());
        List<InputOutputSignal> testSignals = InputOutputSignalGenerator.getTestingSignals();

        for (int i = 0; i < testSignals.size(); i++) {
            perseptron.test(testSignals.get(i));
        }


    }
}
