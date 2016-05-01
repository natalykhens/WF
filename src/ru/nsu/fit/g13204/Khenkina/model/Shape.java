package  ru.nsu.fit.g13204.Khenkina.model;


import java.awt.*;
import java.util.List;


public abstract class Shape {
    private Point center;
public Color color;
    public Shape(double x, double y, double z) {
        center = new Point(x, y, z, 1);
    }

    public Shape(Point center) {
        this.center = center;
    }

    public abstract List<Segment> getSegments();

    public abstract void invalidate();

    public Point getCenter() {
        return center;
    }
    public void setColor(Color color) {this.color = color;}

    public Color getColor() {
        return color;
    }

}
