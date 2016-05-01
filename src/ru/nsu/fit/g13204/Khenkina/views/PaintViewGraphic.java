package  ru.nsu.fit.g13204.Khenkina.views;

import javafx.geometry.Point2D;
import ru.nsu.fit.g13204.Khenkina. model.BSplineShape;
import  ru.nsu.fit.g13204.Khenkina.model.Model;
import  ru.nsu.fit.g13204.Khenkina.model.Shape;
import  ru.nsu.fit.g13204.Khenkina.utils.BSplineCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;


public class PaintViewGraphic extends JPanel {
    private Model model;
    private BufferedImage image;
    private int currentWidth;
    private int currentHeight;
    private int n;
    private int m;

    private Point2D currentPoint;

    private boolean drag;

    private static final int WIDTH_CELL = 30;
    private static final int HEIGHT_CELL = 30;

    private Shape shape;

    public PaintViewGraphic(final Model model, final Shape currentShape) {
        this.model = model;
        this.shape = currentShape;

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int viewX = e.getX();
                int viewY = e.getY();
                if(viewX < 0) viewX = 0;
                if(viewX >= getWidth()) viewX = getWidth() - 1;

                if(viewY < 0) viewY = 0;
                if(viewY >= getHeight()) viewY = getHeight() - 1;

                if(!drag) {
                    List<Point2D> points = ((BSplineShape) shape).getPoints();
                    for (Point2D point : points) {
                        double x = (getWidth()) * (point.getX() - model.getA()) / (model.getC() - model.getA());
                        double y = (getHeight()) * (point.getY() - model.getB()) / (model.getD() - model.getB());
                        double d = Math.sqrt(Math.pow(viewX - x, 2) + Math.pow(viewY - y, 2));
                        if (d < 10) {
                            currentPoint = point;
                        }
                    }
                    if(currentPoint == null) {
                        Point2D point2D = ((BSplineShape) shape).getRotatePoint();
                        double x = (getWidth()) * (point2D.getX() - model.getA()) / (model.getC() - model.getA());
                        if (Math.abs(x - viewX) < 10) {
                            currentPoint = point2D;
                        }
                    }
                    System.out.println("point: " + currentPoint);
                }

                drag = true;

                if(currentPoint != null) {
                    double x = model.getA() + (model.getC() - model.getA()) * viewX / getWidth();
                    double y = model.getB() + (model.getD() - model.getB()) * viewY / getHeight();
                    Point2D point2D = new Point2D(x, y);
                    List<Point2D> points = ((BSplineShape) shape).getPoints();
                    int index = points.indexOf(currentPoint);
                    if(index != -1) {
                        points.set(index, point2D);
                    } else {
                        ((BSplineShape) shape).setRotatePoint(point2D);
                    }
                    currentPoint = point2D;
                    repaint();
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                drag = false;
                currentPoint = null;
                model.notifyListeners();
            }
        });

        setPreferredSize(new Dimension(WIDTH_CELL * model.getN(), HEIGHT_CELL * model.getM()));
    }

    private void clearImage() {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRGB(i, j, Color.WHITE.getRGB());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentHeight != getHeight() || currentWidth != getWidth() || image == null || n != model.getN() || m != model.getM()) {

            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            currentHeight = getHeight();
            currentWidth = getWidth();
            n = model.getN();
            m = model.getM();

        }
        clearImage();

        for (int i = 0; i <= n; i++) {
            int cellWidth = (int) (i * image.getWidth() / n + 0.5);
            if (cellWidth >= image.getWidth()) {
                cellWidth = image.getWidth() - 1;
            }
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRGB(cellWidth, j, Color.RED.getRGB());
            }
        }

        for (int i = 0; i <= m; i++) {
            int cellHeight = (int) (i * image.getHeight() / m + 0.5);
            if (cellHeight >= image.getHeight()) {
                cellHeight = image.getHeight() - 1;
            }
            for (int j = 0; j < image.getWidth(); j++) {
                image.setRGB(j, cellHeight, Color.RED.getRGB());
            }
        }

        List<Shape> shapes = model.getScene().getShapes();

        BSplineShape shape = (BSplineShape) shapes.get(model.getCurrentShape());
        this.shape = shape;
        List<Point2D> points = shape.getPoints();

        int rotateX = (int) ((shape.getRotatePoint().getX() - model.getA()) * getWidth() / (model.getC() - model.getA()) + 0.5);

        for(int i = 0; i < image.getHeight(); i++) {
            image.setRGB(rotateX, i, Color.BLACK.getRGB());
       }

        for (int i = 0; i < points.size(); i++) {
            Point2D point2D = points.get(i);
            int x = (int) ((point2D.getX() - model.getA()) * getWidth() / (model.getC() - model.getA()) + 0.5);
            int y = (int) ((point2D.getY() - model.getB()) * getHeight() / (model.getD() - model.getB()) + 0.5);


            drawCircle(image, x, y, 5, Color.BLACK.getRGB());
        }

        drawBSpline(image, points, model.getK());
        g.drawImage(image, 0, 0, null);
    }

    private void drawCircle(BufferedImage image, int x, int y, int radius, int color) {
        int width = image.getWidth();
        int height = image.getHeight();
        int radius2 = radius * radius;

        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                if (i < 0 || i >= width) continue;
                if (j < 0 || j >= height) continue;

                int length = (x - i) * (x - i) + (y - j) * (y - j);
                if (length < radius2) {
                    image.setRGB(i, j, color);
                }
            }
        }
    }

    private void drawBSpline(BufferedImage image, List<Point2D> points, int k) {
        Point2D prev = null;
        List<Point2D> spline = BSplineCalculator.calculate(points, k);
        for (Point2D point : spline) {
            if (prev != null) {
                int startX = (int) ((prev.getX() - model.getA()) * image.getWidth() / (model.getC() - model.getA()) + 0.5);
                int startY = (int) ((prev.getY() - model.getB()) * image.getHeight() / (model.getD() - model.getB()) + 0.5);

                int endX = (int) ((point.getX() - model.getA()) * image.getWidth() / (model.getC() - model.getA()) + 0.5);
                int endY = (int) ((point.getY() - model.getB()) * image.getHeight() / (model.getD() - model.getB()) + 0.5);

                drawLine(image, startX, startY, endX, endY, Color.MAGENTA.getRGB());
            }

            prev = point;

            int x = (int) ((point.getX() - model.getA()) * image.getWidth() / (model.getC() - model.getA()) + 0.5);
            int y = (int) ((point.getY() - model.getB()) * image.getHeight() / (model.getD() - model.getB()) + 0.5);
            drawCircle(image, x, y, 2, Color.BLACK.getRGB());
        }
    }

    private void drawLine(BufferedImage image, int startX, int startY, int endX, int endY, int color) {
        //System.out.println("start[" + startX + ", " + startY + "] end[" + endX + ", " + endY + "]");
        if(Math.abs(startX - endX) > Math.abs(startY - endY)) {
            if (endX < startX) {
                int tmp = endX;
                endX = startX;
                startX = tmp;
                tmp = endY;
                endY = startY;
                startY = tmp;
            }
            for(int i = startX; i < endX; i++) {
                int y = startY + (i - startX) * (endY - startY) / (endX - startX);
                int x = i;
                if(y >= image.getHeight()) continue;
                if(y < 0) continue;
                if(x >= image.getWidth()) continue;
                if(x < 0) continue;
                image.setRGB(x, y, color);
            }
        } else {
            if (endY < startY) {
                int tmp = endX;
                endX = startX;
                startX = tmp;
                tmp = endY;
                endY = startY;
                startY = tmp;
            }
            for(int i = startY; i < endY; i++) {
                int x = startX + (i - startY) * (endX - startX) / (endY - startY);
                int y = i;
                if(y >= image.getHeight()) continue;
                if(y < 0) continue;
                if(x >= image.getWidth()) continue;
                if(x < 0) continue;
                image.setRGB(x, y, color);
            }
        }
    }



    public void invalidateModel() {
        setPreferredSize(new Dimension(WIDTH_CELL * model.getN(), HEIGHT_CELL * model.getM()));
        repaint();
    }
}
