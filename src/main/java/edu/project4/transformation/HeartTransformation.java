package edu.project4.transformation;

import edu.project4.model.Point;

public class HeartTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double oldX = point.x();
        double oldY = point.y();
        return new Point(
            Math.sqrt(oldX * oldX + oldY * oldY) * Math.sin(
                Math.sqrt(oldX * oldX + oldY * oldY) * Math.atan(oldY / oldX)),
            -Math.sqrt(oldX * oldX + oldY * oldY) * Math.cos(
                Math.sqrt(oldX * oldX + oldY * oldY) * Math.atan(oldY / oldX))
        );
    }
}
