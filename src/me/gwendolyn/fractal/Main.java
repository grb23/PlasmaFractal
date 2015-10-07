package me.gwendolyn.fractal;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(300,300);
        f.setTitle("Empty Frame");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.add(new PredatorHeatVision());
    }
}
