package  ru.nsu.fit.g13204.Khenkina.views;

import  ru.nsu.fit.g13204.Khenkina.controller.Controller;
import  ru.nsu.fit.g13204.Khenkina.model.Model;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {
    private static final String TITLE = "WF";
    private static final int WINDOW_HEIGHT = 740;
    private static final int WINDOW_WIDTH = 620;

    public MainWindow() {

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        Model model = new Model("FIT_13204_Khenkina_Natalia_WF_Data/input.txt");
        Controller controller = new Controller(model);
        add(new ControlPanel(controller, model));
        final ShapeChoicePanel shapeChoicePanel = new ShapeChoicePanel(model, new Controller(model));
        add(shapeChoicePanel);


        final PaintView view = new PaintView(model, true);
        Graphic2D graphic2D =  new Graphic2D(model, model.getScene().getShapes().get(model.getCurrentShape()));
        graphic2D.setVisible(true);
        add(view);
        final PaintView viewAll = new PaintView(model, false );
        add(viewAll);
        model.addListener(new Model.ModelChangeListener() {
            @Override
            public void onModelChange() {
                view.repaint();
                viewAll.repaint();

            }
        });
        model.addListener(new Model.ModelChangeListener() {
            @Override
            public void onModelChange() {
                shapeChoicePanel.repaint();
            }
        });
    }
}
