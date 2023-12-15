package edu.hw10.task1;


import edu.hw10.task1.generator.RandomObjectGenerator;
import edu.hw10.task1.model_for_tests.AllFieldsModel;
import edu.hw10.task1.model_for_tests.Student;
import edu.hw10.task1.model_for_tests.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RandomObjectGeneratorTest {

    @Test
    @DisplayName("#nextObject using constructor test")
    public void nextObject_shouldReturnRandomGeneratedObjectUsingItsAllArgsConstructor() {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        Student student = generator.nextObject(Student.class);
        Assertions.assertAll(
            () -> org.assertj.core.api.Assertions.assertThat(student.getName()).isNotNull(),
            () -> org.assertj.core.api.Assertions.assertThat(student.getAge()).isBetween(20, 30)
        );
    }

    @Test
    @DisplayName("#nextObject using factory method test")
    public void nextObject_shouldReturnRandomGeneratedObjectUsingItsFactoryMethod() {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        Teacher teacher = generator.nextObject(Teacher.class, "create");
        Assertions.assertAll(
            () -> org.assertj.core.api.Assertions.assertThat(teacher.getAge()).isBetween(30, 60),
            () -> org.assertj.core.api.Assertions.assertThat(teacher.getName().length()).isBetween(10, 15)
        );
    }

    @Test
    @DisplayName("#nextObject with invalid factory method name test")
    public void nextObject_shouldThrowException_whenFactoryMethodNameIsInvalid() {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> generator.nextObject(Teacher.class, "getTeacher"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("nextObject with all types test")
    public void nextObject_shouldReturnRandomGeneratedObjectUsingItsConstructor() {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        AllFieldsModel model = generator.nextObject(AllFieldsModel.class);
        Assertions.assertAll(
            () -> org.assertj.core.api.Assertions.assertThat(model.string()).isNotNull(),
            () -> org.assertj.core.api.Assertions.assertThat(model.l()).isBetween(10L, 40L),
            () -> org.assertj.core.api.Assertions.assertThat(model.s()).isBetween(
                (short) 2,
                (short) 5
            ),
            () -> org.assertj.core.api.Assertions.assertThat(model.d()).isBetween(3d, 40d),
            () -> org.assertj.core.api.Assertions.assertThat(model.f()).isBetween(40f, 333f),
            () -> org.assertj.core.api.Assertions.assertThat(model.i()).isBetween(10, 15),
            () -> org.assertj.core.api.Assertions.assertThat(model.b()).isBetween(
                (byte) 1,
                (byte) 4
            )
        );
    }

}
