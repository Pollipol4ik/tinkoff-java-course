package edu.hw9.task1;


import lombok.Builder;

@Builder
public record Stat(String name, double sum, double average, double max, double min) {
}
