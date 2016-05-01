package  ru.nsu.fit.g13204.Khenkina.views;

import  ru.nsu.fit.g13204.Khenkina.controller.Controller;
import  ru.nsu.fit.g13204.Khenkina.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControlPanel extends JPanel {
    private JButton parametersButton;

    private static final String titleParametersButton = "Edit parameters";

    private Controller controller;
    private Model model;

    public ControlPanel(final Controller controller, final Model model) {
        this.controller = controller;
        this.model = model;

        parametersButton = new JButton(titleParametersButton);

        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        add(parametersButton);

        parametersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane optionPane = new JOptionPane();
                JLabel label1 = new JLabel("n:");
                JLabel label2 = new JLabel("m:");
                JLabel label3 = new JLabel("k:");
                JLabel label4 = new JLabel("a:");
                JLabel label5 = new JLabel("b:");
                JLabel label6 = new JLabel("c:");
                JLabel label7 = new JLabel("d:");
                JLabel label8 = new JLabel("zn:");
                JLabel label9 = new JLabel("zf:");
                JLabel label10 = new JLabel("sw:");
                JLabel label11 = new JLabel("sh:");

                JTextField n = new JTextField("");
                JTextField m = new JTextField("");
                JTextField k = new JTextField("");
                JTextField a = new JTextField("");
                JTextField b = new JTextField("");
                JTextField c = new JTextField("");
                JTextField d = new JTextField("");
                JTextField zn = new JTextField("");
                JTextField zf = new JTextField("");
                JTextField sw = new JTextField("");
                JTextField sh = new JTextField("");

                n.setText(Integer.toString(model.getN()));
                m.setText(Integer.toString(model.getM()));
                k.setText(Integer.toString(model.getK()));
                a.setText(Double.toString(model.getA()));
                b.setText(Double.toString(model.getB()));
                c.setText(Double.toString(model.getC()));
                d.setText(Double.toString(model.getD()));
                zn.setText(Double.toString(model.getZn()));
                zf.setText(Double.toString(model.getZf()));
                sw.setText(Double.toString(model.getSw()));
                sh.setText(Double.toString(model.getSh()));

                JPanel panel = new JPanel();

                GroupLayout groupLayout = new GroupLayout(panel);
                panel.setLayout(groupLayout);

                groupLayout.setHorizontalGroup(
                        groupLayout.createSequentialGroup().
                                addGroup(groupLayout.createParallelGroup().
                                        addGroup(groupLayout.createSequentialGroup().addComponent(label1).addComponent(n).addComponent(label2).addComponent(m).addComponent(label3).addComponent(k)).
                                        addGroup(groupLayout.createSequentialGroup().addComponent(label4).addComponent(a).addComponent(label5).addComponent(b).addComponent(label6).addComponent(c).addComponent(label7).addComponent(d)).
                                        addGroup(groupLayout.createSequentialGroup().addComponent(label8).addComponent(zn).addComponent(label9).addComponent(zf).addComponent(label10).addComponent(sw).addComponent(label11).addComponent(sh)))
                );

                groupLayout.setVerticalGroup(
                        groupLayout.createSequentialGroup().
                                addGroup(groupLayout.createParallelGroup().addComponent(label1).addComponent(n).addComponent(label2).addComponent(m).addComponent(label3).addComponent(k)).
                                addGroup(groupLayout.createParallelGroup().addComponent(label4).addComponent(a).addComponent(label5).addComponent(b).addComponent(label6).addComponent(c).addComponent(label7).addComponent(d)).
                                addGroup(groupLayout.createParallelGroup().addComponent(label8).addComponent(zn).addComponent(label9).addComponent(zf).addComponent(label10).addComponent(sw).addComponent(label11).addComponent(sh))


                );

                optionPane.setMessage(new Object[]{panel});
                JDialog dialog = optionPane.createDialog(null, "Edit");
                dialog.setVisible(true);

                try {
                    int n1 = Integer.parseInt(n.getText());
                    int m1 = Integer.parseInt(m.getText());
                    int k1 = Integer.parseInt(k.getText());

                    double a1 = Double.parseDouble(a.getText());
                    double b1 = Double.parseDouble(b.getText());
                    double c1 = Double.parseDouble(c.getText());
                    double d1 = Double.parseDouble(d.getText());

                    double zn1 = Double.parseDouble(zn.getText());
                    double zf1 = Double.parseDouble(zf.getText());
                    double sw1 = Double.parseDouble(sw.getText());
                    double sh1 = Double.parseDouble(sh.getText());

                   controller.setParameters(n1, m1, k1, a1, b1, c1, d1, zn1, zf1, sw1, sh1);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: invalid data format");
                }
            }
        });
    }
}