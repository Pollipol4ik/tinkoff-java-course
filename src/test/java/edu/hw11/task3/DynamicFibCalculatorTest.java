package edu.hw11.task3;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DynamicFibCalculatorTest {

    @Test
    @DisplayName("creating dynamic fib calculator using byte buddy and asm")
    @SneakyThrows
    public void shouldCreateNewClassWhichCanCalculateFibNumbers() {
        Class<?> createdClass = createClass();
        long actual = (long) createdClass.getDeclaredMethod("getFib", int.class)
            .invoke(createdClass.getDeclaredConstructor().newInstance(), 10);
        assertThat(actual).isEqualTo(55);
    }

    private Class<?> createClass() {
        return new ByteBuddy()
            .subclass(Object.class)
            .name("DynamicFibCalculator")
            .defineMethod("getFib", long.class).withParameters(int.class)
            .intercept(MethodDelegation.to(FibonacciCalculator.class))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();
    }

    public static class FibonacciCalculator {
        public static long getFib(int n) {
            if (n < 2) {
                return n;
            } else {
                return getFib(n - 1) + getFib(n - 2);
            }
        }
    }
}
