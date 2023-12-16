package edu.project5;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@State(Scope.Thread)
public class ReflectionBenchmark {

    private record Student(String name, String surname) {}

    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private Function<Student, String> lambdaFunction;

    @Setup
    public void setup() throws Throwable {
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
        lambdaFunction = (Function<Student, String>) callSite.getTarget().invokeExact();
    }

    @Benchmark
    public void directAccess(Blackhole blackhole) {
        String name = student.name();
        blackhole.consume(name);
    }

    @Benchmark
    public void reflectionAccess(Blackhole blackhole) throws Throwable {
        String name = (String) method.invoke(student);
        blackhole.consume(name);
    }

    @Benchmark
    public void methodHandlesAccess(Blackhole blackhole) throws Throwable {
        String name = (String) methodHandle.invoke(student);
        blackhole.consume(name);
    }

    @Benchmark
    public void lambdaMetaFactoryAccess(Blackhole blackhole) {
        String name = lambdaFunction.apply(student);
        blackhole.consume(name);
    }

    public static void main(String[] args) throws RunnerException {
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
