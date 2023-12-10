package edu.project4.transformation;


import edu.project4.model.Point;
import static java.lang.Math.sin;

public class SinusTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        return new Point(sin(point.x()), sin(point.y()));
    }
}
