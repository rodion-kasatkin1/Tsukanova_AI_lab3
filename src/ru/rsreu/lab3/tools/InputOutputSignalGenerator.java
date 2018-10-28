package ru.rsreu.lab3.tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InputOutputSignalGenerator {


    public static List<InputOutputSignal> getTeachingSignals() {
        List<InputOutputSignal> signals = new ArrayList<>();
        signals.add(new InputOutputSignal("000100110101000100010001", "1000000000"));  //1
        signals.add(new InputOutputSignal("111100010001111110001111", "0100000000"));  //2
        signals.add(new InputOutputSignal("111100010010011100011111", "0010000000"));  //3
        signals.add(new InputOutputSignal("100110011111000100010001", "0001000000"));  //4
        signals.add(new InputOutputSignal("111110001000111100011111", "0000100000"));  //5
        signals.add(new InputOutputSignal("111110001000111110011111", "0000010000"));  //6
        signals.add(new InputOutputSignal("111100010001001001001000", "0000001000"));  //7
        signals.add(new InputOutputSignal("111110011001111110011111", "0000000100"));  //8
        signals.add(new InputOutputSignal("111110011111001001001000", "0000000010"));  //9
        signals.add(new InputOutputSignal("111110011001100110011111", "0000000001"));  //0
        return signals;
    }

    public static List<InputOutputSignal> getTestingSignals() {
        List<InputOutputSignal> signals = new ArrayList<>();
        signals.add(new InputOutputSignal("000100110111000101010001", "1000000000"));  //1
        signals.add(new InputOutputSignal("011100010001111110001111", "0100000000"));  //2
        signals.add(new InputOutputSignal("011100010010011100011111", "0010000000"));  //3
        signals.add(new InputOutputSignal("000110011111000100010001", "0001000000"));  //4
        signals.add(new InputOutputSignal("111010001000111100011111", "0000100000"));  //5
        signals.add(new InputOutputSignal("111010001000111110011111", "0000010000"));  //6
        signals.add(new InputOutputSignal("011100010001001001001000", "0000001000"));  //7
        signals.add(new InputOutputSignal("111110011001101110011111", "0000000100"));  //8
        signals.add(new InputOutputSignal("111110011111001001000000", "0000000010"));  //9
        signals.add(new InputOutputSignal("111111011001100110011111", "0000000001"));  //0
        return signals;
    }

    public static List<Double> getValuesPrediction() {
        List<Double> data = new ArrayList<>();
        double[] array = {30.1851,
                30.3996,
                29.93,
                29.4956,
                29.1537,
                30.74,
                31.2554,
                30.1861,
                30.5771,
                29.9086,
                30.6925,
                31.3335,
                30.6252,
                29.8018,
                28.9028,
                28.2277,
                27.3348,
                27.8751,
                27.8726,
                27.5204,
                28.9278,
                32.6799,
                30.683,
                31.3216,
                31.8729,
                30.3131,
                29.2889,
                29.3479,
                29.5937,
                32.7358,
                32.7177,
                32.5361,
                32.1998,
                31.0994,
                31.7267,
                30.6034,
                30.297,
                30.0773,
                30.8285,
                31.5664,
                31.3164,
                32.1201,
                32.4288,
                33.1224,
                32.3451,
                31.7448,
                33.1482,
                32.6587,
                34.6044,
                36.1847,
                35.6053,
                35.4971,
                34.8887,
                33.8434,
                35.4438,
                37.2945,
                38.7243};
        for (int i = 0; i < array.length; i++) {
            data.add(array[i]);
        }
        return data;
    }

    public static List<Double> normalizeData(double a, double b, List<Double> data) {
        List<Double> sortData = new ArrayList<>(data);
        sortData.sort(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return (int) (o1 * 100 - o2 * 100);
            }
        });
        double pmin = sortData.get(0);
        double pmax = sortData.get(data.size() - 1);
        double dp = pmax - pmin;
        List<Double> normalizedData = new ArrayList<>();
        for (int i = 0; i < sortData.size(); i++) {
            normalizedData.add((data.get(i) - pmin) / dp * (b - a) + a);
        }
        return normalizedData;
    }
}
