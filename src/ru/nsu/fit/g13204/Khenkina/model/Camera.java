package  ru.nsu.fit.g13204.Khenkina.model;



public class Camera {
    private Point center;
    private Point viewDirection;
    private Point up;

    public Camera(Point center, Point viewDirection, Point up){
        this.center = center;
        this.viewDirection = viewDirection;
        this.up = up;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setViewDirection(Point viewDirection) {
        this.viewDirection = viewDirection;
    }

    public void setUp(Point up) {
        this.up = up;
    }

    public Point getCenter() {
        return center;
    }

    public Point getViewDirection() {
        return viewDirection;
    }

    public Point getUp() {
        return up;
    }


}
