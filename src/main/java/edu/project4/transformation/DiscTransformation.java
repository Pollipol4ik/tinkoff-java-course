package edu.project4.transformation;

import edu.project4.model.Point;

public class DiscTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double oldX = point.x();
        double oldY = point.y();
        double arg = Math.PI * Math.sqrt(oldX * oldX + oldY * oldY);
        double preCalculated = (1 / Math.PI) * Math.atan(oldY / oldX);
        return new Point(preCalculated * Math.sin(arg), preCalculated * Math.cos(arg));
    }
}
