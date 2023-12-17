package edu.hw10.task1;

import edu.hw10.task1.annotation.Max;
import edu.hw10.task1.annotation.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MapperTest {

    private final boolean primitiveBoolean;
    private final Integer wrappedInteger;
    private final int primitiveInteger;
    private final int[] primitiveArray;

    public static MapperTest generate(
        @Min(100) @Max(200) boolean primitiveBoolean,
        Integer wrappedInteger,
        int primitiveInteger,
        int[] primitiveArray
    ) {
        return new MapperTest(primitiveBoolean, wrappedInteger, primitiveInteger, primitiveArray);
    }

}
