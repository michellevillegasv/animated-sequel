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

  public SimulationPanel(Simulation simulation) {
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

    FightPanel fightPanel = new FightPanel();
    simulation.getArtificialIntelligence().getFightObserver().addObserver((
        Character[] ch) -> {
      fightPanel.setCharacters(ch[0], ch[1]);
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
    gbc.gridx = 1;
    gbc.anchor = GridBagConstraints.CENTER;
    queuesPanel.add(new TitleLabel("vs"), gbc);

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.weightx = 1;
    gbc.insets = new Insets(8, 8, 8, 8);

    gbc.gridx = 0;
    queuesPanel.add(new TitleLabel(simulation.getLeftTeam().getName()), gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 1"), gbc);
    queuesPanel.add(cnQueue1List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 2"), gbc);
    queuesPanel.add(cnQueue2List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 3"), gbc);
    queuesPanel.add(cnQueue3List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Refuerzos"), gbc);
    queuesPanel.add(cnQueueRList, gbc);

    gbc.gridx = 2;
    queuesPanel.add(new TitleLabel(simulation.getRightTeam().getName()), gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 1"), gbc);
    queuesPanel.add(nickQueue1List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 2"), gbc);
    queuesPanel.add(nickQueue2List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 3"), gbc);
    queuesPanel.add(nickQueue3List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Refuerzos"), gbc);
    queuesPanel.add(nickQueueRList, gbc);

    /* Panel de simulación */
    JPanel simulationPanel = new JPanel(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.weightx = 1;
    gbc.gridx = 0;

    simulationPanel.add(new TitleLabel("Pelea actual"), gbc);
    gbc.insets = new Insets(8, 16, 8, 16);
    simulationPanel.add(fightPanel, gbc);

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
    gbc.weightx = 1;
    this.add(queuesPanel, gbc);
    gbc.weightx = 1;
    this.add(simulationPanel, gbc);
  }
}

class TitleLabel extends JLabel {
  public TitleLabel(String text) {
    super(text);
    setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    setForeground(Color.BLUE);
  }
}

class SubtitleLabel extends JLabel {
  public SubtitleLabel(String text) {
    super(text);
    setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
  }
}

class QueueList extends JScrollPane {
  public QueueList(CharacterQueue queue) {
    JList<String> list = new JList<>(
        new QueueListModel(queue));
    setViewportView(list);
    setPreferredSize(new Dimension(96, 128));
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

class FightPanel extends JPanel {
  private JLabel leftId;
  private JLabel rightId;
  private JLabel leftIcon;
  private JLabel rightIcon;
  private Box leftBox;
  private Box rightBox;

  public FightPanel() {
    leftId = new JLabel();
    rightId = new JLabel();
    leftIcon = new JLabel();
    rightIcon = new JLabel();
    leftBox = Box.createVerticalBox();
    rightBox = Box.createVerticalBox();

    GridBagConstraints gbc;
    setLayout(new GridBagLayout());

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 2;
    this.add(new TitleLabel("VS"), gbc);

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    this.add(leftBox, gbc);
    this.add(Box.createHorizontalStrut(128), gbc);
    gbc.gridx = 4;
    this.add(rightBox, gbc);
    this.add(Box.createHorizontalStrut(128), gbc);

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 1;
    gbc.insets = new Insets(8, 8, 8, 8);
    this.add(leftIcon, gbc);
    gbc.insets = new Insets(0, 0, 0, 0);
    this.add(leftId, gbc);
    gbc.gridx = 3;
    gbc.insets = new Insets(8, 8, 8, 8);
    this.add(rightIcon, gbc);
    gbc.insets = new Insets(0, 0, 0, 0);
    this.add(rightId, gbc);

  }

  public void setCharacters(Character leftCharacter, Character rightCharacter) {
    leftId.setText(leftCharacter.getId());
    rightId.setText(rightCharacter.getId());
    leftIcon.setIcon(leftCharacter.getIcon());
    rightIcon.setIcon(rightCharacter.getIcon());

    leftBox.removeAll();
    if (leftCharacter.getLifePoints() == Character.QUALITY_STAT) {
      leftBox.add(new JLabel("Puntos de vida de calidad"));
    }
    if (leftCharacter.getStrength() == Character.QUALITY_STAT) {
      leftBox.add(new JLabel("Fuerza de calidad"));
    }
    if (leftCharacter.getAgility() == Character.QUALITY_STAT) {
      leftBox.add(new JLabel("Agilidad de calidad"));
    }
    if (leftCharacter.getSkills() == Character.QUALITY_STAT) {
      leftBox.add(new JLabel("Habilidad de calidad"));
    }

    rightBox.removeAll();
    if (rightCharacter.getLifePoints() == Character.QUALITY_STAT) {
      rightBox.add(new JLabel("Puntos de vida de calidad"));
    }
    if (rightCharacter.getStrength() == Character.QUALITY_STAT) {
      rightBox.add(new JLabel("Fuerza de calidad"));
    }
    if (rightCharacter.getAgility() == Character.QUALITY_STAT) {
      rightBox.add(new JLabel("Agilidad de calidad"));
    }
    if (rightCharacter.getSkills() == Character.QUALITY_STAT) {
      rightBox.add(new JLabel("Habilidad de calidad"));
    }
  }
}