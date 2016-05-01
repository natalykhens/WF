package  ru.nsu.fit.g13204.Khenkina.model;


public class Point {
    private double x;
    private double y;
    private double z;
    private double w;

    public Point(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        double n = w;
        if(w == 0) {
            n = Math.sqrt(x * x + y * y + z * z);
        }
        if(n != 1) {
            this.x /= n;
            this.y /= n;
            this.z /= n;
            this.w /= n;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }
}
