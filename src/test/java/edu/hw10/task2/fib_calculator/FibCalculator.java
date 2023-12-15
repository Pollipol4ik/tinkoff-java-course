package edu.hw10.task2.fib_calculator;

import edu.hw10.task2.annotation.Cache;

public interface FibCalculator {

    @Cache
    long getFib(int number);
}
