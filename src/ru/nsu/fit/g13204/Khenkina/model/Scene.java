package ru.nsu.fit.g13204.Khenkina.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Scene {
    private List<Shape> shapes;
    private Camera camera;
    private Model model;

    public Scene(Model model) {
        this.model = model;
        shapes = new ArrayList<Shape>();
        addShape(new XYZ(0, 0, 0));
    }


    public void setCamera(Camera camera) {
        this.camera = camera;

    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public Camera getCamera() {
        return camera;
    }

    public List<Segment> getSegments(Boolean flag) {
        List<Segment> segments = new ArrayList<Segment>();
        Matrix matrixTranspose = new Matrix(new double[][]{
                {1.0, 0, 0, -camera.getCenter().getX()},
                {0, 1.0, 0, -camera.getCenter().getY()},
                {0, 0, 1.0, -camera.getCenter().getZ()},
                {0, 0, 0, 1}});
        Matrix matrixTransposeBack = new Matrix(new double[][]{
                {1.0, 0, 0, camera.getCenter().getX()},
                {0, 1.0, 0, camera.getCenter().getY()},
                {0, 0, 1.0, camera.getCenter().getZ()},
                {0, 0, 0, 1}});

        Matrix matrixProjection = new Matrix(new double[][]{
                {2.0 * model.getZn() / model.getSw(), 0, 0, 0},
                {0, 2.0 * model.getZn() / model.getSh(), 0, 0},
                {0, 0, model.getZf() / (model.getZf() - model.getZn()), -model.getZf() * model.getZn() / (model.getZf() - model.getZn())},
                {0, 0, 1, 0}
        });


        Point view = camera.getViewDirection();
        Point up = camera.getUp();
        Point right = new Point(up.getY() * view.getZ() - up.getZ() * view.getY(), up.getZ() * view.getX() - up.getX() * view.getZ(), up.getX() * view.getY() - up.getY() * view.getX(), 0);
        Matrix matrixRotation = new Matrix(new double[][]{
                {right.getX(), right.getY(), right.getZ(), 0},
                {up.getX(), up.getY(), up.getZ(), 0},
                {view.getX(), view.getY(), view.getZ(), 0},
                {0, 0, 0, 1}
        });
        Matrix matrixCamera = matrixTransposeBack.multiply(matrixRotation).multiply(matrixTranspose);

        if (flag == true) {

            for (Shape shape : shapes) {
                Matrix matrixTransposeShape = new Matrix(new double[][]{
                        {1.0, 0, 0, -shape.getCenter().getX()},
                        {0, 1.0, 0, -shape.getCenter().getY()},
                        {0, 0, 1.0, -shape.getCenter().getZ()},
                        {0, 0, 0, 1}});
                Matrix matrix = matrixProjection.multiply(matrixTranspose).multiply(matrixCamera).multiply(matrixTransposeShape);
                for (Segment segment : shape.getSegments()) {
                    Point point1 = matrix.matrixOnVector(segment.getStartPoint());
                    Point point2 = matrix.matrixOnVector(segment.getEndPoint());
                    Segment segment1 = new Segment(point1, point2);
                    segment1.setColor(shape.getColor());
                    segments.add(segment1);
                    segment.setColor(shape.getColor());

                }

            }
        }
            Matrix matrixTransposeShape1 = new Matrix(new double[][]{
                    {1.0, 0, 0, -shapes.get(0).getCenter().getX()},
                    {0, 1.0, 0, -shapes.get(0).getCenter().getY()},
                    {0, 0, 1.0, -shapes.get(0).getCenter().getZ()},
                    {0, 0, 0, 1}});


            Matrix matrix1 = matrixProjection.multiply(matrixTranspose).multiply(matrixCamera).multiply(matrixTransposeShape1);
            for (Segment segment : shapes.get(0).getSegments()) {
                Point point1 = matrix1.matrixOnVector(segment.getStartPoint());
                Point point2 = matrix1.matrixOnVector(segment.getEndPoint());
                Segment segment1 = new Segment(point1, point2);
                segment1.setColor(segment.getColor());
//segment1.setColor(new Color(0,0,0));
                segments.add(segment1);
            }
            Matrix matrixTransposeShape2 = new Matrix(new double[][]{
                    {1.0, 0, 0, -shapes.get(model.getCurrentShape()).getCenter().getX()},
                    {0, 1.0, 0, -shapes.get(model.getCurrentShape()).getCenter().getY()},
                    {0, 0, 1.0, -shapes.get(model.getCurrentShape()).getCenter().getZ()},
                    {0, 0, 0, 1}});

            Matrix matrix2 = matrixProjection.multiply(matrixTranspose).multiply(matrixCamera).multiply(matrixTransposeShape2);
            for (Segment segment : shapes.get(model.getCurrentShape()).getSegments()) {
                Point point1 = matrix2.matrixOnVector(segment.getStartPoint());
                Point point2 = matrix2.matrixOnVector(segment.getEndPoint());
                Segment segment1 = new Segment(point1, point2);
                segment1.setColor(shapes.get(model.getCurrentShape()).getColor());
                segments.add(segment1);
            }
            return segments;
        }
    }
