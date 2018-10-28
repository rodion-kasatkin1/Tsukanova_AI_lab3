package ru.rsreu.lab3.perseptio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PerseptronPredicator {
    private static final double A = 0.5;
    private static final int LENGTH_WINDOW = 10;
    private static final int COUNT_ERA = 100000;
    private static final double ERROR = 0.05;

    private double speed;
    private List<NeuronLayer> neuronLayers;

    public PerseptronPredicator(double speed) {
        neuronLayers = new ArrayList<>();
        neuronLayers.add(new NeuronLayer(LENGTH_WINDOW, 1));
        this.speed = speed;
    }

    public void addNeuronLayer(NeuronLayer neuronLayer) {
        neuronLayers.add(neuronLayer);
    }

    public void addNextNeuronLayer(int countInputs) {
        int countInputsLastNeuron = neuronLayers.remove(neuronLayers.size() - 1).getCountInput();
        neuronLayers.add(new NeuronLayer(countInputsLastNeuron - 1, countInputs));
        neuronLayers.add(new NeuronLayer(countInputs, 1));

    }

    private double computeSigmoida(double a, double S) {
        return 1 / (1 + Math.exp(-a * S));
    }

    private double computeDiffSigmoida(double a, double S) {
        return a * S * (1 - S);
    }



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
                if (neuronLayers.size() - 1 != k) {
                    output.add(computeSigmoida(A, sumWeightOnIntput));
                } else {
                    //если слой последний то выходом является просто сумма
                    output.add(sumWeightOnIntput);
                }
            }
            neuronLayers.get(k).setOutput(output);
            input = output;  // Now input is Last output Layer
        }
    }

    //обратное распространение ошибки
    private double doBackPropagationError(List<Double> trueOutput) {
        double resultError = 0;
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
                    resultError += error[j] * error[j];
                    for (int m = 0; m < neuronLayers.get(k).getCountInput() - 1; m++) {
                        neuronLayers.get(k).getDeltaWeightCoef()[j][m] = error[j]   * neuronLayers.get(k).getInput().get(m);
                        sum[m] += error[j]  * neuronLayers.get(k).getWeightCoef()[j][m + 1];
                    }
                    //если уровень не последний
                    //"сумма" считается на основании предыдущих "сумм"
                } else {
                    df.get(ind)[j] = computeDiffSigmoida(A, neuronLayers.get(k).getOutput().get(j + 1));
                    for (int m = 0; m < neuronLayers.get(k).getCountInput() - 1; m++) {
                        neuronLayers.get(k).getDeltaWeightCoef()[j][m] = df.get(ind)[j] * neuronLayers.get(k).getInput().get(m) * sums.get(ind - 1)[j];
                        sum[m] += sums.get(ind - 1)[j] * df.get(ind)[j] * neuronLayers.get(k).getWeightCoef()[j][m + 1];
                    }
                }
            }
            sums.add(sum);
        }
        return resultError;
    }

    public void teach(List<Double> teachValues) {

        for (int i = 0; i < COUNT_ERA; i++) {
            double error = 0;
            for (int j = 0; j < teachValues.size() - LENGTH_WINDOW; j++) { // по обучающим примерам
                List<Double> input = new ArrayList<>();
                input.add(1.0);// потому что у0=1 нужно только на входном и промежуточных слоях
                input.addAll(teachValues.subList(j, j + LENGTH_WINDOW));
                //прямой ход
                doNeuronWebGeDirectMove(input);
                // обрасное распространение ошибки
                error += doBackPropagationError(teachValues.subList(j + LENGTH_WINDOW, j + LENGTH_WINDOW + 1));

                //обновление коэффициентов
                for (int k = 0; k < neuronLayers.size(); k++) {
                    for (int n = 0; n < neuronLayers.get(k).getCountoutput(); n++) {
                        for (int m = 0; m < neuronLayers.get(k).getCountInput(); m++) {
                            neuronLayers.get(k).getWeightCoef()[n][m] += speed * neuronLayers.get(k).getDeltaWeightCoef()[n][m];
                        }
                    }
                }
            }


            error /= (teachValues.size() - LENGTH_WINDOW);
            if (error <= ERROR) {
                break;
            }
            if (i == COUNT_ERA - 1) {
                System.out.println(i + ":  " + error);
            } else {
                //System.out.println(i + ":  " + error);
            }
        }

    }

    public void test(List<Double> testValues) {


        double error = 0;
        System.out.println("Тестовая нормализованная выборка: " + testValues);
        System.out.println("Окно q = " + (LENGTH_WINDOW - 1));
        for (int j = 0; j < testValues.size() - LENGTH_WINDOW; j++) { // по обучающим примерам
            List<Double> input = new ArrayList<>();
            input.add(1.0);// потому что у0=1 нужно только на входном и промежуточных слоях
            input.addAll(testValues.subList(j, j + LENGTH_WINDOW));
            //прямой ход
            doNeuronWebGeDirectMove(input);
            for (int i = 0; i < neuronLayers.get(neuronLayers.size() - 1).getOutput().size(); i++) {
                error += Math.abs(testValues.get(j + LENGTH_WINDOW) - neuronLayers.get(neuronLayers.size() - 1).getOutput().get(i));
                System.out.println("выход " + i + ": real = " + testValues.get(j + LENGTH_WINDOW) + " count= " + neuronLayers.get(neuronLayers.size() - 1).getOutput().get(i));

            }

        }
        error /= (testValues.size() - LENGTH_WINDOW);
        System.out.println("Средняя ошибка по всем примерам: " + error);
    }
}
