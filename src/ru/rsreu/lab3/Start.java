package ru.rsreu.lab3;

import ru.rsreu.lab3.perseptio.NeuronLayer;
import ru.rsreu.lab3.perseptio.Perseptron;
import ru.rsreu.lab3.perseptio.PerseptronPredicator;
import ru.rsreu.lab3.tools.InputOutputSignal;
import ru.rsreu.lab3.tools.InputOutputSignalGenerator;

import java.util.List;

public class Start {
    public static void main(String[] args) {
//        Perseptron perseptron = new Perseptron(0.5);
//        perseptron.addNeuronLayer(new NeuronLayer(24,16));
//        perseptron.addNeuronLayer(new NeuronLayer(16,10));
//
//        perseptron.teach(InputOutputSignalGenerator.getTeachingSignals());
//        List<InputOutputSignal> testSignals = InputOutputSignalGenerator.getTestingSignals();
//
//        for (int i = 0; i < testSignals.size(); i++) {
//            perseptron.test(testSignals.get(i));
//        }

        PerseptronPredicator perseptronPredicator = new PerseptronPredicator(0.5);
        //изначально у персепртрона уже есть один нейрон с количеством входов = ширине окна q, и выходов = 1
        //методом addNext добавляется еще один слой нейронов, у прошлого слоя изменяется количество нейронов (и выходов соответственно)
        perseptronPredicator.addNextNeuronLayer(8);
        List<Double> data = InputOutputSignalGenerator.getValuesPrediction();

        perseptronPredicator.teach(InputOutputSignalGenerator.normalizeData(1, 5, data).subList(0, 44));
        System.out.println("Тестовая выборка: " + data.subList(44, 57));
        perseptronPredicator.test(InputOutputSignalGenerator.normalizeData(1, 5, data).subList(44, 57));


    }
}
