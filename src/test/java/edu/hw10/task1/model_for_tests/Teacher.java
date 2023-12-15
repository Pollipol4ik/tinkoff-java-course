package edu.hw10.task1.model_for_tests;

import edu.hw10.task1.annotation.Max;
import edu.hw10.task1.annotation.Min;
import edu.hw10.task1.annotation.NotNull;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public final class Teacher {

    private final String name;
    private final int age;

    private Teacher(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static Teacher create(@NotNull @Min(10) @Max(15) String name, @Min(30) @Max(60) int age) {
        return new Teacher(name, age);
    }
}
