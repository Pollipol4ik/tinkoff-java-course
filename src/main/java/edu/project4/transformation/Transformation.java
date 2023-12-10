package edu.project4.transformation;

import edu.project4.model.Point;
import java.util.function.Function;

@FunctionalInterface
public interface Transformation extends Function<Point, Point> {
}
