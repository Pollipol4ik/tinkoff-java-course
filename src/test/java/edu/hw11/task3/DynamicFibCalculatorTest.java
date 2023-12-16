package edu.hw11.task3;

import java.lang.reflect.Modifier;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender.Size;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.Opcodes;
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
            .defineMethod("getFib", long.class, Modifier.PUBLIC).withParameters(int.class)
            .intercept(new Implementation() {
                @Override
                public ByteCodeAppender appender(Target target) {
                    return (mv, context, methodDescription) -> {
                        Label l1 = new Label();

                        mv.visitCode();
                        // if (n < 2)
                        mv.visitVarInsn(Opcodes.ILOAD, 1);
                        mv.visitInsn(Opcodes.ICONST_2);
                        mv.visitJumpInsn(Opcodes.IF_ICMPGE, l1);

                        //  return n;
                        mv.visitVarInsn(Opcodes.ILOAD, 1);
                        mv.visitInsn(Opcodes.I2L);
                        mv.visitInsn(Opcodes.LRETURN);

                        //return getFib(n - 1) + getFib(n - 2);
                        mv.visitLabel(l1);
                        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                        mv.visitVarInsn(Opcodes.ILOAD, 1);
                        mv.visitInsn(Opcodes.ICONST_1);
                        mv.visitInsn(Opcodes.ISUB);
                        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "DynamicFibCalculator", "getFib", "(I)J");
                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                        mv.visitVarInsn(Opcodes.ILOAD, 1);
                        mv.visitInsn(Opcodes.ICONST_2);
                        mv.visitInsn(Opcodes.ISUB);
                        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "DynamicFibCalculator", "getFib", "(I)J");
                        mv.visitInsn(Opcodes.LADD);
                        mv.visitInsn(Opcodes.LRETURN);
                        mv.visitEnd();
                        return new Size(5, 2);
                    };
                }

                @Override
                public InstrumentedType prepare(InstrumentedType instrumentedType) {
                    return instrumentedType;
                }
            })
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();
    }
}
