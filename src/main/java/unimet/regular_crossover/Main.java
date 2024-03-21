package unimet.regular_crossover;

import javax.swing.JFrame;

import unimet.regular_crossover.ui.SimulationPanel;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        SimulationPanel simulationPanel = new SimulationPanel(simulation);

        JFrame frame = new JFrame("Animated Sequel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(simulationPanel);
        frame.pack();
        frame.setVisible(true);
        simulation.start();
    }
}