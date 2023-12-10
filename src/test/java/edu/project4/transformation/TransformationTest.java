package edu.project4.transformation;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import edu.project4.model.Point;
import static org.assertj.core.api.Assertions.assertThat;

class TransformationTest {

    @Test
    @DisplayName("CylinderTransformation#apply")
    public void apply_shouldTransformCylinder() {
        CylinderTransformation transformation = new CylinderTransformation();
        Point given = new Point(5, 5);
        Point actual = transformation.apply(given);
        assertThat(actual).isEqualTo(new Point(-0.9589242746631385, 5.0));
    }

    @Test
    @DisplayName("LinearTransformation#apply")
    public void apply_shouldTransformLinearly() {
        LinearTransformation transformation = new LinearTransformation();
        Point expected = new Point(5, 5);
        Point actual = transformation.apply(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("DiskTransformation#apply")
    public void apply_shouldTransformDisk() {
        DiscTransformation transformation = new DiscTransformation();
        Point given = new Point(5, 5);
        Point actual = transformation.apply(given);
        assertThat(actual).isEqualTo(new Point(-0.05535396184412049, -0.24379487055342172));
    }
    @Test
    @DisplayName("PolarTransformation#apply")
    public void apply_shouldTransformPolar() {
        PolarTransformation transformation = new PolarTransformation();
        Point given = new Point(5, 5);
        Point actual = transformation.apply(given);
        assertThat(actual).isEqualTo(new Point(0.25, 6.0710678118654755));
    }

    @Test
    @DisplayName("PowerTransformation#apply")
    public void apply_shouldTransformPower() {
        PowerTransformation transformation = new PowerTransformation();
        Point given = new Point(5, 5);
        Point actual = transformation.apply(given);
        assertThat(actual).isEqualTo(new Point(2.8194318904513938, 2.8194318904513933));
    }

    @Test
    @DisplayName("TangentTransformation#apply")
    public void apply_shouldTransformTangent() {
        TangentTransformation transformation = new TangentTransformation();
        Point given = new Point(5, 5);
        Point actual = transformation.apply(given);
        assertThat(actual).isEqualTo(new Point(-3.380515006246586, -3.380515006246586));
    }

    @Test
    @DisplayName("HeartTransformation#apply")
    public void apply_shouldTransformHeart() {
        HeartTransformation transformation = new HeartTransformation();
        Point given = new Point(5, 5);
        Point actual = transformation.apply(given);
        assertThat(actual).isEqualTo(new Point(-4.7132755554838255, -5.2711510638643855));
    }

    @Test
    @DisplayName("SinusTransformation#apply")
    public void apply_shouldTransformSine() {
        SinusTransformation transformation = new SinusTransformation();
        Point given = new Point(5, 5);
        Point actual = transformation.apply(given);
        assertThat(actual).isEqualTo(new Point(-0.9589242746631385, -0.9589242746631385));
    }

    @Test
    @DisplayName("SphereTransformation#apply")
    public void apply_shouldTransformSphere() {
        SphereTransformation transformation = new SphereTransformation();
        Point given = new Point(5, 5);
        Point actual = transformation.apply(given);
        assertThat(actual).isEqualTo(new Point(0.1, 0.1));
    }

    @Test
    @DisplayName("SwirlTransformation#apply")
    public void apply_shouldTransformSwirl() {
        SwirlTransformation transformation = new SwirlTransformation();
        Point given = new Point(5, 5);
        Point actual = transformation.apply(given);
        assertThat(actual).isEqualTo(new Point(-6.13670441098021, 3.5129558739409226));
    }

}
