package edu.project4.transformation;

import edu.project4.model.Point;

public class PolarTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double oldX = point.x();
        double oldY = point.y();
        return new Point(Math.atan(oldY / oldX) / Math.PI, Math.sqrt(oldX * oldX + oldY * oldY) - 1);
    }
}
