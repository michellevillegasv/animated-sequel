package animated_sequel;

import java.awt.Dimension;

import javax.swing.JFrame;

import animated_sequel.simulation.Administrator;
import animated_sequel.ui.SimulationPanel;

/**
 *
 * @author USUARIO
 */
public class AnimatedSequel {

    public static void main(String[] args) {

        Administrator simulation = new Administrator();
        SimulationPanel simulationPanel = new SimulationPanel(simulation);

        JFrame frame = new JFrame("Animated Sequel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1080, 800));
        frame.add(simulationPanel);
        frame.pack();
        frame.setVisible(true);

        simulation.start();
    }
}
