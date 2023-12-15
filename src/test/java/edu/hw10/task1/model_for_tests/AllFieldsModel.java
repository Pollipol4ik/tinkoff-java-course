package edu.hw10.task1.model_for_tests;

import edu.hw10.task1.annotation.Max;
import edu.hw10.task1.annotation.Min;
import edu.hw10.task1.annotation.NotNull;

public record AllFieldsModel(boolean check, @Min(1) @Max(4) byte b, @Min(3) @Max(40) double d,
                             @Min(40) @Max(333) float f,
                             @Min(10) @Max(15) int i, @Min(10) @Max(40) long l, @Min(2) @Max(5) short s,
                             @NotNull String string) {

}
