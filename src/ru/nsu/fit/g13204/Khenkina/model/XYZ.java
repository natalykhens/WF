package  ru.nsu.fit.g13204.Khenkina.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class XYZ extends Shape {
    private List<Segment> segments;

    public XYZ(double x, double y, double z) {
        super(x, y, z);
        init();
    }

    public XYZ(Point center) {
        super(center);
    }

    @Override
    public List<Segment> getSegments() {
        return segments;
    }

    @Override
    public void invalidate() {

    }


    private void init() {
        segments = new ArrayList<Segment>();
        segments.add(new Segment(new Point(-10,0,0, 1), new Point(10, 0, 0, 1), Color.RED));
        segments.add(new Segment(new Point(0,-10,0, 1), new Point(0, 10, 0, 1), Color.RED));
        segments.add(new Segment(new Point(0,0,-10, 1), new Point(0, 0, 10, 1), Color.BLUE));
    }
}
