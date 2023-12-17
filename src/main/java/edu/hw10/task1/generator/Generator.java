package edu.hw10.task1.generator;

import java.lang.reflect.Parameter;

@FunctionalInterface
public interface Generator<T> {
    T generate(Parameter parameter);
}
