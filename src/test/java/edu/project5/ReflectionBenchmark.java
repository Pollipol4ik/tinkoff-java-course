package edu.project5;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import lombok.SneakyThrows;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class ReflectionBenchmark {

    private record Student(String name, String surname) {}

    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private Function<Student, String> lambdaFunction;

    @Setup
    @SneakyThrows
    public void setup() {
        student = new Student("Polina", "Kuptsova");
        method = Student.class.getDeclaredMethod("name");
        method.setAccessible(true);
        methodHandle = MethodHandles.lookup().findGetter(Student.class, "name", String.class);
        CallSite callSite = LambdaMetafactory.metafactory(
            MethodHandles.lookup(),
            "apply",
            MethodType.methodType(Function.class),
            MethodType.methodType(Object.class, Object.class),
            MethodHandles.lookup().findVirtual(Student.class, "name", MethodType.methodType(String.class)),
            MethodType.methodType(String.class, Student.class)
        );
        lambdaFunction = ((Function<Student, String>) callSite.getTarget().invokeExact());
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    @SneakyThrows
    public void reflectionAccess(Blackhole bh) {
        Object name = method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    @SneakyThrows
    public void methodHandlesAccess(Blackhole bh) {
        Object name = methodHandle.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    @SneakyThrows
    public void lambdaMetaFactoryAccess(Blackhole bh) {
        String name = lambdaFunction.apply(student);
        bh.consume(name);
    }

    @SneakyThrows
    public static void main(String[] args) {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(5))
            .build();

        new Runner(options).run();
    }
}
