package edu.project4.transformation;

import edu.project4.model.Point;
import static java.lang.Math.pow;

public class SphereTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        return new Point(
            point.x() / (pow(point.x(), 2) + pow(point.y(), 2)),
            point.y() / (pow(point.x(), 2) + pow(point.y(), 2))
        );
    }
}
