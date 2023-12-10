package edu.project4.transformation;

import edu.project4.model.Point;

public class SphereTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double oldX = point.x();
        double oldY = point.y();
        return new Point(oldX / (oldX * oldX + oldY * oldY), oldY / (oldX * oldX + oldY * oldY));
    }
}
