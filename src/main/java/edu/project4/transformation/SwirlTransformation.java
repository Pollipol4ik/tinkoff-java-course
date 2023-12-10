package edu.project4.transformation;

import edu.project4.model.Point;

public class SwirlTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double oldX = point.x();
        double oldY = point.y();
        double squareRadius = oldX * oldX + oldY * oldY;
        return new Point(
            oldX * Math.sin(squareRadius) - oldY * Math.cos(squareRadius),
            oldX * Math.cos(squareRadius) + oldY * Math.sin(squareRadius)
        );
    }
}
