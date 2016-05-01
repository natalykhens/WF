package  ru.nsu.fit.g13204.Khenkina.model;

import javafx.geometry.Point2D;
import  ru.nsu.fit.g13204.Khenkina.utils.BSplineCalculator;

import java.util.ArrayList;
import java.util.List;

public class BSplineShape extends Shape {
    private List<Segment> segments;
    private List<Point2D> points;
    private int k;
    private Point2D rotatePoint;

    private static final int ROTATE_STEP = 12;


    public BSplineShape(double x, double y, double z, int k) {
        super(x, y, z);
        this.k = k;
        points = new ArrayList<Point2D>();
    }

    public void addPoint(Point2D point2D) {
        points.add(point2D);
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setRotatePoint(Point2D rotatePoint) {
        this.rotatePoint = rotatePoint;
    }


    public List<Point2D> getPoints() {
        return points;
    }

    @Override
    public List<Segment> getSegments() {
        if(segments == null) {
            segments = new ArrayList<Segment>();
            calculateSegments();
        }
        return segments;
    }

    @Override
    public void invalidate() {
        segments = null;
    }

    public Point2D getRotatePoint() {
        return rotatePoint;
    }

    private void calculateSegments() {
        List<Point2D> bSplinePoints = BSplineCalculator.calculate(points, k);
        List<Point> pointList = new ArrayList<Point>();
        for (Point2D point2D : bSplinePoints) {
            pointList.add(new Point(point2D.getX(), point2D.getY(), 0, 1));
        }
        List<List<Point>> shape = new ArrayList<List<Point>>();

        for(int i = 0; i < ROTATE_STEP; i++) {
            double angle = i * 2 * Math.PI / ROTATE_STEP;
            Matrix matrixTranspose = new Matrix(new double[][] {
                    {1, 0, 0, -rotatePoint.getX()},
                    {0, 1, 0, -rotatePoint.getY()},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
            });
            Matrix matrixRotation = new Matrix(new double[][] {
                    {Math.cos(angle), 0, - Math.sin(angle), 0},
                    {0, 1, 0, 0},
                    {Math.sin(angle), 0, Math.cos(angle), 0},
                    {0, 0, 0, 1}
            });

            Matrix matrix = matrixRotation.multiply(matrixTranspose);
            List<Point> pointList1 = new ArrayList<Point>();
            for(Point point : pointList) {
                Point point1 = matrix.matrixOnVector(point);
                pointList1.add(point1);
            }
            shape.add(pointList1);
        }
        for(int i = 0; i < shape.size(); i++) {
            List<Point> group1 = shape.get(i);
            List<Point> group2 = shape.get((i + 1) % shape.size());

            for(int j = 0; j < group1.size(); j++) {
                segments.add(new Segment(group1.get(j), group2.get(j)));
                if(j > 0) {
                    segments.add(new Segment(group1.get(j - 1), group1.get(j)));
                }
            }
        }
    }
}
