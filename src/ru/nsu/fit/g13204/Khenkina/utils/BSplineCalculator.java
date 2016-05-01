package  ru.nsu.fit.g13204.Khenkina.utils;

import javafx.geometry.Point2D;
import  ru.nsu.fit.g13204.Khenkina.model.Point;

import java.util.ArrayList;
import java.util.List;


public class BSplineCalculator {

    private static final int B_SPLINE_POW = 3;

    public static List<Point2D> calculate(List<Point2D> points, int k) {
        List<Point2D> result = new ArrayList<Point2D>();

        double t[] = new double[points.size() + B_SPLINE_POW];
        if(t.length < 2 * B_SPLINE_POW) {
            throw new IllegalArgumentException("K is too large");
        }
        int start = 0;
        int end = t.length;
        for (int i = 0; i < B_SPLINE_POW; i++) {
            t[i] = 0;
            start = i;
        }
        for (int i = t.length - 1; i >= t.length - B_SPLINE_POW; i--) {
            t[i] = t.length - 2 * B_SPLINE_POW + 1;
            end = i;
        }
        for (int i = start + 1; i < end; i++) {
            t[i] = i - start;
        }

        int iterationCount = (points.size() + 1) * k;
        for (int i = 0; i <= iterationCount; i++) {
            double u = t[0] + (i * (t[t.length - 1] - t[0])) / iterationCount;
            Point2D splinePoint = new Point2D(0, 0);
            for (int j = 0; j < points.size(); j++) {
                double n = getNValue(j, B_SPLINE_POW, u, t);
                Point2D p = points.get(j).multiply(n);
                splinePoint = splinePoint.add(p);
            }

            result.add(splinePoint);
        }
        return result;
    }

    private static double getNValue(int i, int k, double u, double[] t) {
        if(k == 1) {
            if(u >= t[i] && u < t[i + 1]) return 1;
            if (t[i + 1] == t[t.length - 1] && u == t[i + 1]) return 1;
            return 0;
        }
        double tmp1 = (t[i + k - 1] - t[i]);
        double tmp2 = (t[i + k] - t[i + 1]);
        double result = 0.0;
        if(tmp1 != 0) {
            result += ((u - t[i]) * getNValue(i, k - 1, u, t)) / tmp1;
        }
        if(tmp2 != 0)  {
            result += (t[i + k] - u) * getNValue(i + 1, k - 1, u, t) / tmp2;
        }

        return result;
    }
}
