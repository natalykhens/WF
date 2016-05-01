package  ru.nsu.fit.g13204.Khenkina.views;

import  ru.nsu.fit.g13204.Khenkina.controller.Controller;
import  ru.nsu.fit.g13204.Khenkina.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ShapeChoicePanel extends JPanel {
    private Model model;
    private Controller controller;
    List<JRadioButton> radioButtons = new ArrayList<JRadioButton>();

    private JRadioButton shapeButton1 = new JRadioButton("1");
    private JRadioButton shapeButton2 = new JRadioButton("2");
    private JRadioButton shapeButton3 = new JRadioButton("3");
    private JRadioButton shapeButton4 = new JRadioButton("4");
    private JRadioButton shapeButton5 = new JRadioButton("5");
    private JRadioButton shapeButton6 = new JRadioButton("6");
    private JRadioButton shapeButton7 = new JRadioButton("7");
    private JRadioButton shapeButton8 = new JRadioButton("8");
    private JRadioButton shapeButton9 = new JRadioButton("9");
    private JRadioButton shapeButton10 = new JRadioButton("10");
    ButtonGroup group = new ButtonGroup();

    public ShapeChoicePanel(final Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        group.add(shapeButton1);
        group.add(shapeButton2);
        group.add(shapeButton3);
        group.add(shapeButton4);
        group.add(shapeButton5);
        group.add(shapeButton6);
        group.add(shapeButton7);
        group.add(shapeButton8);
        group.add(shapeButton9);
        group.add(shapeButton10);

        setLayout(layout);
        add(shapeButton1);
        add(shapeButton2);
        add(shapeButton3);
        add(shapeButton4);
        add(shapeButton5);
        add(shapeButton6);
        add(shapeButton7);
        add(shapeButton8);
        add(shapeButton9);
        add(shapeButton10);
        radioButtons.add(shapeButton1);
        radioButtons.add(shapeButton2);
        radioButtons.add(shapeButton3);
        radioButtons.add(shapeButton4);
        radioButtons.add(shapeButton5);
        radioButtons.add(shapeButton6);
        radioButtons.add(shapeButton7);
        radioButtons.add(shapeButton8);
        radioButtons.add(shapeButton9);
        radioButtons.add(shapeButton10);

        int currentShape = model.getCurrentShape() - 1;
        radioButtons.get(currentShape).setSelected(true);
        for(final JRadioButton button : radioButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = radioButtons.indexOf(button) + 1;
                    model.setCurrentShape(index);
                }
            });
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int count = model.getCountShapes();
        for(int i = 9; i >= count; i--) {
            radioButtons.get(i).setVisible(false);
        }


    }
}
