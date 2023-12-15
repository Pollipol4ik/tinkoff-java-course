package edu.hw10.task1.model_for_tests;

import edu.hw10.task1.annotation.Max;
import edu.hw10.task1.annotation.Min;
import edu.hw10.task1.annotation.NotNull;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Student {

    private final String name;
    private final int age;

    public Student(@NotNull String name, @Min(20) @Max(30) int age) {
        this.name = name;
        this.age = age;
    }
}
