package edu.hw11.task2;

import edu.hw11.task2.utils.ArithmeticUtils;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChangeMethodUsingByteBuddyTest {

    @Test
    @DisplayName("change ArithmeticUtils#sum using byte buddy")
    @SneakyThrows
    public void sum_shouldDoMultUsingByteBuddy() {
        ByteBuddyAgent.install();
        new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(ElementMatchers.named("sum")).intercept(MethodDelegation.to(ArithmeticUtilsDelegate.class))
            .make()
            .load(ClassLoader.getSystemClassLoader(), ClassReloadingStrategy.fromInstalledAgent())
            .getLoaded();
        int actual = ArithmeticUtils.sum(3, 3);
        assertThat(actual).isEqualTo(9);
    }

    private static final class ArithmeticUtilsDelegate {
        public static int mult(int a, int b) {
            return a * b;
        }
    }
}
