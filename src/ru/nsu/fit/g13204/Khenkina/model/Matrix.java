package  ru.nsu.fit.g13204.Khenkina.model;

import java.util.Arrays;


public class Matrix {
    private int size;

    private double[] elements;

    public Matrix(double[][] elements) {
        this.size = elements.length;
        this.elements = new double[size * size];
        int n = 0;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].length != size) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < elements[i].length; j++) {
                this.elements[n++] = elements[i][j];
            }
        }
    }

    public Matrix(double[] elements, int size) {
        if (size != elements.length / size) {
            throw new IllegalArgumentException("Invalid size matrix");
        }
        this.size = elements.length / size;
        this.elements = new double[size * size];
        int n = 0;
        for (int i = 0; i < elements.length; i++) {
            this.elements[n++] = elements[i];
        }
    }

    public int getSize() {
        return size;
    }

    public Matrix multiply(Matrix matrix) {
        double[] temp = new double[this.size * this.size];

        Arrays.fill(temp, 0.0);

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                for (int k = 0; k < this.size; k++) {
                    temp[i * size + j] += elements[i * size + k] * matrix.elements[k * size + j];

                }
            }
        }
        return new Matrix(temp, this.size);
    }

    public Point matrixOnVector(Point point) {
        Point resultPoint;

        double[] result = new double[4];
        double[] vector = new double[]{point.getX(), point.getY(), point.getZ(), (double) point.getW()};
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i] += vector[j] * elements[i * size + j];
            }
        }
        resultPoint = new Point(result[0], result[1], result[2], (int) result[3]);
        return resultPoint;
    }

    public Point vectorOnMatrix(Point point) {
        Point resultPoint;

        double[] result = new double[size];
        double[] vector = new double[]{point.getX(), point.getY(), point.getZ(), point.getW()};
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i] += vector[j] * elements[j * size + i];
            }
        }
        resultPoint = new Point(result[0], result[1], result[2], result[3]);
        return resultPoint;
    }
}
