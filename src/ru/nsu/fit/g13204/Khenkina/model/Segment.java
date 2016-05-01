package  ru.nsu.fit.g13204.Khenkina.model;


import java.awt.*;


public class Segment {
    private Point startPoint;
    private Point endPoint;
    private Color color;


    public Segment(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
     //  color = Color.RED;
    }
    public Segment(Point startPoint, Point endPoint, Color color) {
        this.color = color;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Color getColor() {
        return color;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
