package  ru.nsu.fit.g13204.Khenkina.views;

import  ru.nsu.fit.g13204.Khenkina.model.Model;
import  ru.nsu.fit.g13204.Khenkina.model.Shape;

import javax.swing.*;


public class Graphic2D extends JFrame {
    private Model model;

    private PaintViewGraphic paintView;

    public Graphic2D(Model model, Shape shape) {
        this.model = model;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final PaintViewGraphic paintView = new PaintViewGraphic(model, shape);
        add(paintView);
        setResizable(false);
        pack();

        model.addListener(new Model.ModelChangeListener() {
            @Override
            public void onModelChange() {
                paintView.invalidateModel();
                pack();
            }
        });
    }

}
