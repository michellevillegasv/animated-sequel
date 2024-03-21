package unimet.regular_crossover.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.AbstractListModel;
import javax.swing.Box;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import unimet.regular_crossover.ArtificialIntelligence;
import unimet.regular_crossover.Character;
import unimet.regular_crossover.CharacterQueue;
import unimet.regular_crossover.Simulation;

public class SimulationPanel extends JPanel {
  private JLabel lastResultLabel;
  private JLabel iaStatusLabel;
  private JLabel leftFighterLabel;
  private JLabel rightFighterLabel;

  public SimulationPanel(Simulation simulation) {
    setPreferredSize(new Dimension(1280, 800));

    QueueList cnQueue1List = new QueueList(
        simulation.getLeftTeam().getPriorityQueue()[Character.EXCEPTIONAL_TYPE]);
    QueueList cnQueue2List = new QueueList(
        simulation.getLeftTeam().getPriorityQueue()[Character.AVERAGE_TYPE]);
    QueueList cnQueue3List = new QueueList(
        simulation.getLeftTeam().getPriorityQueue()[Character.DEFICIENT_TYPE]);
    QueueList cnQueueRList = new QueueList(simulation.getLeftTeam().getReinforcementQueue());

    QueueList nickQueue1List = new QueueList(
        simulation.getRightTeam().getPriorityQueue()[Character.EXCEPTIONAL_TYPE]);
    QueueList nickQueue2List = new QueueList(
        simulation.getRightTeam().getPriorityQueue()[Character.AVERAGE_TYPE]);
    QueueList nickQueue3List = new QueueList(
        simulation.getRightTeam().getPriorityQueue()[Character.DEFICIENT_TYPE]);
    QueueList nickQueueRList = new QueueList(simulation.getRightTeam().getReinforcementQueue());

    JList<String> winnersList = new JList<>(new LimitedReverseQueueListModel(simulation.getWinners(), 10));

    JSlider speedSlider = new JSlider(new DefaultBoundedRangeModel(1, 0, 1, 4));
    speedSlider.setMajorTickSpacing(1);
    speedSlider.setSnapToTicks(true);
    speedSlider.setPaintLabels(true);
    speedSlider.setPaintTicks(true);
    speedSlider.addChangeListener((ChangeEvent e) -> {
      simulation.setSpeed(speedSlider.getValue());
    });

    leftFighterLabel = new JLabel();
    rightFighterLabel = new JLabel();
    simulation.getArtificialIntelligence().getFightObserver().addObserver((Character[] ch) -> {
      leftFighterLabel.setText(ch[0].getId());
      rightFighterLabel.setText(ch[1].getId());
    });

    lastResultLabel = new JLabel();
    simulation.getArtificialIntelligence().getResultObserver().addObserver((Integer result) -> {
      switch (result) {
        case ArtificialIntelligence.LEFT_CHARACTER_WON:
          lastResultLabel.setText("Victoria de " + simulation.getLeftTeam().getName());
          break;
        case ArtificialIntelligence.RIGHT_CHARACTER_WON:
          lastResultLabel.setText("Victoria de " + simulation.getRightTeam().getName());
          break;
        case ArtificialIntelligence.FIGHT_TIED:
          lastResultLabel.setText("Empate");
          break;
        case ArtificialIntelligence.FIGHT_SUSPENDED:
          lastResultLabel.setText("Pelea suspendida");
          break;
      }
    });

    iaStatusLabel = new JLabel();
    simulation.getArtificialIntelligence().getStatusObserver().addObserver((Integer status) -> {
      switch (status) {
        case ArtificialIntelligence.ANNOUNCING_STATUS:
          iaStatusLabel.setText("Anunciando resultados");
          break;
        case ArtificialIntelligence.DECIDING_STATUS:
          iaStatusLabel.setText("Decidiendo ganador");
          break;
        case ArtificialIntelligence.WAITING_STATUS:
          iaStatusLabel.setText("Esperando");
          break;
      }
    });

    GridBagConstraints gbc;

    /* Panel de colas */
    JPanel queuesPanel = new JPanel(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.insets = new Insets(8, 8, 8, 8);

    gbc.fill = GridBagConstraints.NONE;
    queuesPanel.add(new TitleLabel("CN Cola 1"), gbc);
    gbc.fill = GridBagConstraints.BOTH;
    queuesPanel.add(cnQueue1List, gbc);
    gbc.fill = GridBagConstraints.NONE;
    queuesPanel.add(new TitleLabel("CN Cola 2"), gbc);
    gbc.fill = GridBagConstraints.BOTH;
    queuesPanel.add(cnQueue2List, gbc);
    gbc.fill = GridBagConstraints.NONE;
    queuesPanel.add(new TitleLabel("CN Cola 3"), gbc);
    gbc.fill = GridBagConstraints.BOTH;
    queuesPanel.add(cnQueue3List, gbc);
    gbc.fill = GridBagConstraints.NONE;
    queuesPanel.add(new TitleLabel("CN Cola Refuerzo"), gbc);
    gbc.fill = GridBagConstraints.BOTH;
    queuesPanel.add(cnQueueRList, gbc);

    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.NONE;
    queuesPanel.add(new TitleLabel("Nick Cola 1"), gbc);
    gbc.fill = GridBagConstraints.BOTH;
    queuesPanel.add(nickQueue1List, gbc);
    gbc.fill = GridBagConstraints.NONE;
    queuesPanel.add(new TitleLabel("Nick Cola 2"), gbc);
    gbc.fill = GridBagConstraints.BOTH;
    queuesPanel.add(nickQueue2List, gbc);
    gbc.fill = GridBagConstraints.NONE;
    queuesPanel.add(new TitleLabel("Nick Cola 3"), gbc);
    gbc.fill = GridBagConstraints.BOTH;
    queuesPanel.add(nickQueue3List, gbc);
    gbc.fill = GridBagConstraints.NONE;
    queuesPanel.add(new TitleLabel("Nick Cola Refuerzo"), gbc);
    gbc.fill = GridBagConstraints.BOTH;
    queuesPanel.add(nickQueueRList, gbc);

    /* Panel de simulación */
    JPanel simulationPanel = new JPanel(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.weightx = 1;
    gbc.gridx = 0;

    simulationPanel.add(new TitleLabel("Pelea actual"), gbc);
    gbc.insets = new Insets(8, 16, 8, 16);
    simulationPanel.add(leftFighterLabel, gbc);
    gbc.insets = new Insets(0, 16, 0, 16);
    simulationPanel.add(new JLabel("vs"), gbc);
    gbc.insets = new Insets(8, 16, 8, 16);
    simulationPanel.add(rightFighterLabel, gbc);

    gbc.insets = new Insets(8, 16, 8, 16);
    simulationPanel.add(new TitleLabel("Estado de la IA"), gbc);
    simulationPanel.add(iaStatusLabel, gbc);
    simulationPanel.add(new TitleLabel("Último resultado"), gbc);
    simulationPanel.add(lastResultLabel, gbc);
    simulationPanel.add(new TitleLabel("Últimos ganadores"), gbc);
    simulationPanel.add(winnersList, gbc);
    simulationPanel.add(new TitleLabel("Velocidad de simulación"), gbc);
    simulationPanel.add(speedSlider, gbc);

    setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weighty = 1;
    gbc.weightx = 1;
    this.add(Box.createHorizontalStrut(64), gbc);
    gbc.weightx = 1;
    this.add(queuesPanel, gbc);
    gbc.weightx = 2;
    this.add(simulationPanel, gbc);
    this.add(Box.createHorizontalStrut(64), gbc);
  }
}

class TitleLabel extends JLabel {
  public TitleLabel(String text) {
    super(text);
    setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
    setForeground(Color.BLUE);
  }
}

class QueueList extends JScrollPane {
  public QueueList(CharacterQueue queue) {
    JList<String> list = new JList<>(
        new QueueListModel(queue));
    setViewportView(list);
    setPreferredSize(new Dimension(64, 128));
  }
}

class QueueListModel extends AbstractListModel<String> {
  private CharacterQueue queue;

  public QueueListModel(CharacterQueue queue) {
    this.queue = queue;
    queue.getObserver().addObserver((CharacterQueue q) -> this.fireContentsChanged(this, 0, queue.getSize()));
  }

  @Override
  public String getElementAt(int index) {
    Character character = queue.getElementAt(index);
    return character != null ? character.getId() : "NULL";
  }

  @Override
  public int getSize() {
    return queue.getSize();
  }
};

class LimitedReverseQueueListModel extends AbstractListModel<String> {
  private CharacterQueue queue;
  private int limit;

  public LimitedReverseQueueListModel(CharacterQueue queue, int limit) {
    this.queue = queue;
    this.limit = limit;
    queue.getObserver()
        .addObserver((CharacterQueue q) -> this.fireContentsChanged(this, 0, Math.min(queue.getSize(), limit)));
  }

  @Override
  public String getElementAt(int index) {
    Character character = queue.getElementAt(queue.getSize() - index - 1);
    return character != null ? character.getId() : "NULL";
  }

  @Override
  public int getSize() {
    return Math.min(queue.getSize(), limit);
  }
};
