package com.example.Calculadora_Final;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private double result;
    private List<Double> numbers = new ArrayList<>();

    public void add(double number) {
        numbers.add(number);
        result += number;
    }

    public void subtract(double number) {
        numbers.add(number);
        result -= number;
    }

    public void multiply(double number) {
        numbers.add(number);
        result *= number;
    }

    public void divide(double number) {
        if (number == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        numbers.add(number);
        result /= number;
    }

    public double getResult() {
        return result;
    }

    public List<Double> getNumbers() {
        return numbers;
    }
}

