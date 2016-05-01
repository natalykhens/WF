package  ru.nsu.fit.g13204.Khenkina.model;

import javafx.geometry.Point2D;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Model {
    public static interface ModelChangeListener {
        public void onModelChange();
    }

    private List<ModelChangeListener> listeners;

    private Scene scene;
    private int currentShape =1;
    private int n;
    private int m;
    private int k;
    private double a;
    private double b;
    private double c;
    private double d;
    private double zn;
    private double zf;
    private double sw;
    private double sh;
    private int countShapes;
    private int countDot;

    private String path;

    public void notifyListeners() {
        for(int i = 0; i < getScene().getShapes().size(); i++) {
            getScene().getShapes().get(i).invalidate();
            if(i!= 0)((BSplineShape)getScene().getShapes().get(i)).setK(k);
        }
        if (listeners != null) {
            for (ModelChangeListener listener : listeners) {
                listener.onModelChange();
            }
        }
    }

    public void addListener(ModelChangeListener listener) {
        if (listeners != null) {
            listeners.add(listener);
        }
    }

    public int getCountShapes() {
        return countShapes;
    }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
        notifyListeners();
    }

    public Model(String path) {
        listeners = new ArrayList<ModelChangeListener>();
        scene = new Scene(this);
        Camera camera = new Camera(new Point(0, 0, -20, 1), new Point(0, 0, 1, 0), new Point(0, -1, 0, 0));
        scene.setCamera(camera);
        this.path = path;
        readFile();
    }

    public int getCurrentShape() {
        return currentShape;
    }

    private void readFile() {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String str = bufferedReader.readLine();
            String[] param = str.split(" ");
            if (param.length != 11) {
                throw new IllegalArgumentException("wrong parameters");
            }
            n = Integer.parseInt(param[0]);
            m = Integer.parseInt(param[1]);
            k = Integer.parseInt(param[2]);
            a = Double.parseDouble(param[3]);
            b = Double.parseDouble(param[4]);
            c = Double.parseDouble(param[5]);
            d = Double.parseDouble(param[6]);
            zn = Double.parseDouble(param[7]);
            zf = Double.parseDouble(param[8]);
            sw = Double.parseDouble(param[9]);
            sh = Double.parseDouble(param[10]);

            str = bufferedReader.readLine();
            countShapes = Integer.parseInt(str);
            str = bufferedReader.readLine();
            countDot = Integer.parseInt(str);

            for(int i = 0; i < countShapes; i++) {
                BSplineShape shape = new BSplineShape(0, 0, 0, k);
                for(int j = 0; j < countDot; j++) {
                    str = bufferedReader.readLine();
                    String []point = str.split(" ");
                    shape.addPoint(new Point2D(Double.parseDouble(point[0]), Double.parseDouble(point[1])));
                }
                int r = (int)(Math.random()*256);
                int g = (int)(Math.random()*256);
                int b = (int)(Math.random()*256);
                shape.setRotatePoint(new Point2D((a + c) / 2, 0));
                shape.setColor(new Color(r,g,b));
                scene.addShape(shape);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error: invalid data format");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getK() {
        return k;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public double getSw() {
        return sw;
    }

    public double getSh() {
        return sh;
    }

    public Scene getScene() {
        return scene;
    }

    public double getZn() {
        return zn;
    }

    public double getZf() {
        return zf;
    }

    public void setParameters(int n, int m, int k, double a, double b, double c, double d, double zn, double zf, double sw, double sh) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.zn = zn;
        this.zf = zf;
        this.sw = sw;
        this.sh = sh;
        notifyListeners();
    }
}
