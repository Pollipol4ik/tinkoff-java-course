package edu.hw10.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomObjectGeneratorTest {
    @Test
    @DisplayName("Testing RandomObjectGenerator#nextObject for POJO")
    public void nextObjectTest1() {
        RandomObjectGenerator generator = RandomObjectGenerator.getInstance();
        assertThat(generator.nextObject(MapperTest.class)).isNotNull();
    }

    @Test
    @DisplayName("Testing RandomObjectGenerator#nextObject for POJO via fabric method")
    public void nextObjectTest2() {
        RandomObjectGenerator generator = RandomObjectGenerator.getInstance();
        assertThat(generator.nextObject(MapperTest.class, "generate")).isNotNull();
    }

    @Test
    @DisplayName("Testing RandomObjectGenerator#nextObject for record")
    public void nextObjectTest3() {
        RandomObjectGenerator generator = RandomObjectGenerator.getInstance();
        assertThat(generator.nextObject(RecordTest.class)).isNotNull();
    }

    @Test
    @DisplayName("Testing proper functioning of annotations")
    public void nextObjectTest4() {
        RandomObjectGenerator generator = RandomObjectGenerator.getInstance();
        RecordTest testRecord = generator.nextObject(RecordTest.class);
        assertTrue(
            testRecord.primitiveInt() < 200 && testRecord.primitiveInt() > 100 && testRecord.string().length() < 40);
    }
}
