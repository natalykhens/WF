package  ru.nsu.fit.g13204.Khenkina.views;

import  ru.nsu.fit.g13204.Khenkina.model.*;
import  ru.nsu.fit.g13204.Khenkina.model.Point;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class PaintView extends JPanel {
    private Model model;
    private int mouseX;
    private int mouseY;
    public boolean flag;

    public PaintView(final Model model, Boolean flag) {
        this.model = model;
this.flag = flag;
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mouseX;
                int dy = e.getY() - mouseY;

                mouseX = e.getX();
                mouseY = e.getY();

                double rotateY = Math.toRadians(90.0) * dx / getWidth();
                double rotateX = Math.toRadians(90.0) * dy / getHeight();

                Point view = model.getScene().getCamera().getViewDirection();
                Point center = model.getScene().getCamera().getCenter();
                Point up = model.getScene().getCamera().getUp();

                Matrix matrixRotationY = getMatrixRotation(up, rotateY);
                Point vector = new Point(up.getY() * view.getZ() - up.getZ() * view.getY(), up.getZ() * view.getX() - up.getX() * view.getZ(), up.getX() * view.getY() - up.getY() * view.getX(), 0);

                Matrix matrixRotationX = getMatrixRotation(vector, rotateX);

                Matrix matrix = matrixRotationX.multiply(matrixRotationY);

                Point newCenter = matrix.matrixOnVector(center);
                Point newView = matrix.matrixOnVector(view);
                Point newUp = matrix.matrixOnVector(up);
                model.getScene().getCamera().setViewDirection(newView);
                model.getScene().getCamera().setCenter(newCenter);
                model.getScene().getCamera().setUp(newUp);

                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Point view = model.getScene().getCamera().getViewDirection();
                Point center = model.getScene().getCamera().getCenter();
                Point up = model.getScene().getCamera().getUp();
                double mouse = e.getPreciseWheelRotation();
                Matrix matrix = new Matrix(new double[][] {
                        {1, 0, 0, mouse * view.getX() / 10.0},
                        {0, 1, 0, mouse * view.getY() / 10.0},
                        {0, 0, 1, mouse * view.getZ() / 10},
                        {0, 0, 0, 1}
                });

                Point newCenter = matrix.matrixOnVector(center);
                Point newView = matrix.matrixOnVector(view);
                Point newUp = matrix.matrixOnVector(up);
                model.getScene().getCamera().setViewDirection(newView);
                model.getScene().getCamera().setCenter(newCenter);
                model.getScene().getCamera().setUp(newUp);

                repaint();
            }
        });

    }

    private Matrix getMatrixRotation(Point point, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = point.getX();
        double y = point.getY();
        double z = point.getZ();

        Matrix matrixRotation = new Matrix(new double[][] {
                {cos + x * x * (1 - cos), x * y * (1 - cos) - z * sin, x * z * (1- cos) + y * sin, 0},
                {y * x * (1 - cos) + z * sin, cos + y * y * (1 - cos), y * z * (1 - cos) - x * sin, 0},
                {z * x * (1 - cos) - y * sin, z * y * (1 - cos) + x * sin, cos + z * z * (1 - cos), 0},
                {0, 0, 0, 1}
        });

        return matrixRotation;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Scene scene = model.getScene();
        List<Segment> segments = scene.getSegments(flag);
        int widthView = getWidth();
        int heightView = getHeight();

        for (Segment segment : segments) {
            Point startPoint = segment.getStartPoint();
            Point endPoint = segment.getEndPoint();
            if(startPoint.getZ() < 0 ||startPoint.getZ() > 1.0) continue;
            if(endPoint.getZ() < 0 ||endPoint.getZ() > 1.0) continue;

            int startX = (int) (widthView * (startPoint.getX() + 1.0) / 2.0 + 0.5);
            int startY = (int) (heightView * (1.0 - startPoint.getY()) / 2.0 + 0.5);
            int endX = (int) (widthView * (endPoint.getX() + 1.0) / 2.0 + 0.5);
            int endY = (int) (heightView * (1.0 - endPoint.getY()) / 2.0 + 0.5);
           g.setColor(segment.getColor());
            g.drawLine(startX, startY, endX, endY);
        }

    }
}
