package edu.hw10.task1.generator.fields_generator;


import edu.hw10.task1.util.GeneratorUtils;
import java.lang.reflect.Parameter;
import java.util.concurrent.ThreadLocalRandom;

public class IntGenerator implements Generator<Integer> {
    @Override
    public Integer generate(Parameter paramAnnotations) {
        var min =
            GeneratorUtils.getMinFromAnnotation(paramAnnotations.getAnnotations(), Integer.MIN_VALUE, Integer.MAX_VALUE)
                .intValue();
        var max =
            GeneratorUtils.getMaxFromAnnotation(paramAnnotations.getAnnotations(), Integer.MIN_VALUE, Integer.MAX_VALUE)
                .intValue();
        var nullable = GeneratorUtils.isNullable(paramAnnotations);

        if (nullable && Math.random() < GeneratorUtils.DEFAULT_NULL_PROBABILITY) {
            return null;
        }

        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
