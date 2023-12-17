package edu.hw10.task1;


import edu.hw10.task1.annotation.Max;
import edu.hw10.task1.annotation.Min;
import edu.hw10.task1.annotation.NotNull;

public record RecordTest(@NotNull @Min(100) @Max(200) int primitiveInt, Integer wrappedInt,
                         @Min(0) @Max(40) String string, char primitiveChar, short primitiveShort, Byte wrappedByte,
                         Long wrappedLong, Float wrappedFloat, double primitiveDouble) {
}
