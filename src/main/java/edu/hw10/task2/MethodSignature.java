package edu.hw10.task2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodSignature {
    String name;
    Class<?>[] types;
    Object[] values;
}
