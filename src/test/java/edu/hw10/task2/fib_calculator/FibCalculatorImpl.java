package edu.hw10.task2.fib_calculator;


public class FibCalculatorImpl implements FibCalculator {

    @Override
    public long getFib(int number) {
        if (number == 1 || number == 0) {
            return number;
        }
        return getFib(number - 1) + getFib(number - 2);
    }
}
