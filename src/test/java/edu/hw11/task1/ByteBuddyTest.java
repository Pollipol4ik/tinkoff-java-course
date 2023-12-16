package edu.hw11.task1;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteBuddyTest {

    @Test
    @DisplayName("say hello bytebuddy using byte buddy")
    @SneakyThrows
    public void toString_shouldPrintHelloWorld() {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.named("toString"))
            .intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();
        assertThat(dynamicType.getDeclaredConstructor().newInstance().toString()).isEqualTo("Hello, ByteBuddy!");
    }

}
