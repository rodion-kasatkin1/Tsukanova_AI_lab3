package ru.rsreu.lab3.perseptio;

import ru.rsreu.lab3.tools.InputOutputSignal;

import java.util.ArrayList;
import java.util.List;

public class Perseptron {
    private static final double A = 1;
    private double speed;
    List<NeuronLayer> neuronLayers;


    public Perseptron(double speed) {
        neuronLayers = new ArrayList<>();
        this.speed = speed;
    }

    public void addNeuronLayer(NeuronLayer neuronLayer) {
        neuronLayers.add(neuronLayer);
    }

    //прямой ход
    private void doNeuronWebGeDirectMove(List<Double> input) {
        for (int k = 0; k < neuronLayers.size(); k++) {
            neuronLayers.get(k).setInput(new ArrayList<>(input));
            List<Double> output = new ArrayList<>();
            if (k != neuronLayers.size() - 1) { // потому что у0=1 нужно только на входном и промежуточных слоях
                output.add(1.0);// y(0) = 1

            }
            for (int out = 0; out < neuronLayers.get(k).getCountoutput(); out++) {
                double sumWeightOnIntput = 0;
                for (int in = 0; in < neuronLayers.get(k).getCountInput(); in++) {
                    sumWeightOnIntput += input.get(in) * neuronLayers.get(k).getWeightCoef()[out][in];
                }
                output.add(computeSigmoida(A, sumWeightOnIntput));
            }
            neuronLayers.get(k).setOutput(output);
            input = output;  // Now input is Last output Layer
        }
    }
    //обратное распространение ошибки
    private void doBackPropagationError(List<Double> trueOutput){
        double[] error = new double[neuronLayers.get(neuronLayers.size() - 1).getCountoutput()]; //количество ошибок равно количеству выходов последнего слоя
        List<double[]> df = new ArrayList<>();
        List<double[]> sums = new ArrayList<>();
        //начинаем с конца
        for (int k = neuronLayers.size() - 1; k >= 0; k--) {
            int ind = neuronLayers.size() - 1 - k; //чтобы индексы шли от нуля
            df.add(new double[neuronLayers.get(k).getCountoutput()]); // dY/dS
            double[] sum = new double[neuronLayers.get(k).getCountInput()];
            for (int j = 0; j < neuronLayers.get(k).getCountoutput(); j++) {
                //если это последний уровень, то только здесь считается ошибка общего выхода,
                // а так же начальное значение "суммы" ("сумма" - это обратно распространяемая ошибка)
                // dW считается не так как на остальных слоях
                if (k == neuronLayers.size() - 1) {
                    error[j] = trueOutput.get(j) - neuronLayers.get(k).getOutput().get(j);
                    df.get(ind)[j] = computeDiffSigmoida(A, neuronLayers.get(k).getOutput().get(j));
                    for (int m = 0; m < neuronLayers.get(k).getCountInput() - 1; m++) {
                        neuronLayers.get(k).getDeltaWeightCoef()[j][m] = error[j] * df.get(ind)[j] * neuronLayers.get(k).getInput().get(m);
                        sum[m] += error[j] * df.get(ind)[j] * neuronLayers.get(k).getWeightCoef()[j][m + 1];
                    }
                    //если уровень не последний
                    //"сумма" считается на основании предыдущих "сумм"
                }else {
                    df.get(ind)[j] = computeDiffSigmoida(A, neuronLayers.get(k).getOutput().get(j+1));
                    for (int m = 0; m < neuronLayers.get(k).getCountInput() - 1; m++) {
                        neuronLayers.get(k).getDeltaWeightCoef()[j][m] = df.get(ind)[j] * neuronLayers.get(k).getInput().get(m) * sums.get(ind - 1)[j];
                        sum[m] += sums.get(ind - 1)[j] * df.get(ind)[j] * neuronLayers.get(k).getWeightCoef()[j][m + 1];
                    }
                }
            }
            sums.add(sum);
        }
    }

    public void teach(List<InputOutputSignal> teachingSignals) {
        double dopError = 1000;
        do {
            for (int i = 0; i < teachingSignals.size(); i++) {
                List<Double> input = new ArrayList<>();
                input.add(1.0);// потому что у0=1 нужно только на входном и промежуточных слоях
                input.addAll(teachingSignals.get(i).getInput());

                //прямой ход
                doNeuronWebGeDirectMove(input);
                //обратное распространение ошибки
                doBackPropagationError(teachingSignals.get(i).getOutput());

                //обновление коэффициентов
                for (int k = 0; k < neuronLayers.size(); k++) {
                    for (int n = 0; n < neuronLayers.get(k).getCountoutput(); n++) {
                        for (int m = 0; m < neuronLayers.get(k).getCountInput(); m++) {
                            neuronLayers.get(k).getWeightCoef()[n][m] += speed * neuronLayers.get(k).getDeltaWeightCoef()[n][m];
                        }
                    }
                }
            }
        } while ((dopError-- > 0));

        System.out.println();


    }

    private double computeSigmoida(double a, double S) {
        return 1 / (1 + Math.exp(-a * S));
    }

    private double computeDiffSigmoida(double a, double S) {
        return a * S * (1 - S);
    }


    public void test(InputOutputSignal signal) {

        List<Double> input = new ArrayList<>();
        input.add(1.0);// потому что у0=1 нужно только на входном и промежуточных слоях
        input.addAll(signal.getInput());
        int countErrors = 0;
        doNeuronWebGeDirectMove(input);

        for (int i = 0; i < signal.getOutput().size(); i++) {
            if (signal.getOutput().get(i) == 1.0) {
                System.out.println("Тестируется значение " + (i + 1));
            }
            double error = neuronLayers.get(neuronLayers.size() - 1).getOutput().get(i) - signal.getOutput().get(i);

            if (Math.round(Math.round(neuronLayers.get(neuronLayers.size() - 1).getOutput().get(i))) - signal.getOutput().get(i) != 0) {
                countErrors++;
            }
        }

        System.out.println("действиетльное значение: " + signal.getOutput());
        System.out.println("Расчетное значение: " + neuronLayers.get(neuronLayers.size() - 1));
        System.out.println("Количество ошибок: " + countErrors);
        System.out.println();


    }


}

