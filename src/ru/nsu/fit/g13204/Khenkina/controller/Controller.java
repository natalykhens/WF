package ru.nsu.fit.g13204.Khenkina.controller;

import  ru.nsu.fit.g13204.Khenkina.model.Model;

public class Controller {
    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void setParameters(int n, int m, int k, double a, double b, double c, double d, double zn, double zf, double sw, double sh) {
        model.setParameters(n, m, k, a, b, c, d, zn, zf, sw, sh);

    }
    public void deleteShape() {
    }
    public void addShape() {
    }
}
