package edu.hw10.task1.generator.fields_generator;



import edu.hw10.task1.util.GeneratorUtils;
import java.lang.reflect.Parameter;
import java.util.concurrent.ThreadLocalRandom;

public class ShortGenerator implements Generator<Short> {
    @Override
    public Short generate(Parameter paramAnnotations) {
        var min =
            GeneratorUtils.getMinFromAnnotation(paramAnnotations.getAnnotations(), Short.MIN_VALUE, Short.MAX_VALUE)
                .shortValue();
        var max =
            GeneratorUtils.getMaxFromAnnotation(paramAnnotations.getAnnotations(), Short.MIN_VALUE, Short.MAX_VALUE)
                .shortValue();
        var nullable = GeneratorUtils.isNullable(paramAnnotations);

        if (nullable && Math.random() < GeneratorUtils.DEFAULT_NULL_PROBABILITY) {
            return null;
        }

        return (short) ThreadLocalRandom.current().nextInt(min, max);
    }
}
