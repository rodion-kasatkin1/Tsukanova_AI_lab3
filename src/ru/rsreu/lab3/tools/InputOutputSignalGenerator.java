package ru.rsreu.lab3.tools;

import java.util.ArrayList;
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
}
