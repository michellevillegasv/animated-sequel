package animated_sequel.ui;

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

import animated_sequel.simulation.Administrator;
import animated_sequel.simulation.IA;
import animated_sequel.characters.Character;
import animated_sequel.queue.CharacterQueue;

public class SimulationPanel extends JPanel {
  private JLabel lastResultLabel;
  private JLabel iaStatusLabel;
  private JLabel leftWinsLabel;
  private JLabel rightWinsLabel;

  public SimulationPanel(Administrator administrator) {
    QueueList leftQueue1List = new QueueList(
        administrator.getLeftStudio().getPriorityQueue()[Character.EXCEPTIONAL_TYPE]);
    QueueList leftQueue2List = new QueueList(
        administrator.getLeftStudio().getPriorityQueue()[Character.AVERAGE_TYPE]);
    QueueList leftQueue3List = new QueueList(
        administrator.getLeftStudio().getPriorityQueue()[Character.DEFICIENT_TYPE]);
    QueueList leftQueueRList = new QueueList(administrator.getLeftStudio().getReinforceQueue());

    QueueList rightQueue1List = new QueueList(
        administrator.getRightStudio().getPriorityQueue()[Character.EXCEPTIONAL_TYPE]);
    QueueList rightQueue2List = new QueueList(
        administrator.getRightStudio().getPriorityQueue()[Character.AVERAGE_TYPE]);
    QueueList rightQueue3List = new QueueList(
        administrator.getRightStudio().getPriorityQueue()[Character.DEFICIENT_TYPE]);
    QueueList rightQueueRList = new QueueList(administrator.getRightStudio().getReinforceQueue());

    JList<String> winnersList = new JList<>(new LimitedReverseQueueListModel(administrator.getIA().getWinners(), 10));

    JSlider speedSlider = new JSlider(new DefaultBoundedRangeModel(1, 0, 1, 4));
    speedSlider.setMajorTickSpacing(1);
    speedSlider.setSnapToTicks(true);
    speedSlider.setPaintLabels(true);
    speedSlider.setPaintTicks(true);
    speedSlider.addChangeListener((ChangeEvent e) -> {
      administrator.setSpeed(speedSlider.getValue());
    });

    FightPanel fightPanel = new FightPanel();
    administrator.getIA().getFightObserver().addObserver((
        Character[] ch) -> {
      fightPanel.setCharacters(ch[0], ch[1]);
    });

    lastResultLabel = new JLabel();
    administrator.getIA().getResultObserver().addObserver((Integer result) -> {
      switch (result) {
        case IA.LEFT_CHARACTER_WON:
          lastResultLabel.setText("Victoria de " + administrator.getLeftStudio().getStudioName());
          break;
        case IA.RIGHT_CHARACTER_WON:
          lastResultLabel.setText("Victoria de " + administrator.getRightStudio().getStudioName());
          break;
        case IA.FIGHT_TIED:
          lastResultLabel.setText("Empate");
          break;
        case IA.FIGHT_SUSPENDED:
          lastResultLabel.setText("Pelea suspendida");
          break;
      }
    });

    iaStatusLabel = new JLabel();
    administrator.getIA().getStatusObserver().addObserver((Integer status) -> {
      switch (status) {
        case IA.ANNOUNCING_STATUS:
          iaStatusLabel.setText("Anunciando resultados");
          break;
        case IA.DECIDING_STATUS:
          iaStatusLabel.setText("Decidiendo ganador");
          break;
        case IA.WAITING_STATUS:
          iaStatusLabel.setText("Esperando");
          break;
      }
    });

    leftWinsLabel = new JLabel("0");
    leftWinsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    administrator.getLeftStudio().getWinsObserver()
        .addObserver((Integer wins) -> leftWinsLabel.setText(String.valueOf(wins)));

    rightWinsLabel = new JLabel("0");
    rightWinsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    administrator.getRightStudio().getWinsObserver()
        .addObserver((Integer wins) -> rightWinsLabel.setText(String.valueOf(wins)));

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
    queuesPanel.add(new TitleLabel(administrator.getLeftStudio().getStudioName()), gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 1"), gbc);
    queuesPanel.add(leftQueue1List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 2"), gbc);
    queuesPanel.add(leftQueue2List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 3"), gbc);
    queuesPanel.add(leftQueue3List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Refuerzos"), gbc);
    queuesPanel.add(leftQueueRList, gbc);

    gbc.gridx = 2;
    queuesPanel.add(new TitleLabel(administrator.getRightStudio().getStudioName()), gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 1"), gbc);
    queuesPanel.add(rightQueue1List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 2"), gbc);
    queuesPanel.add(rightQueue2List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Nivel 3"), gbc);
    queuesPanel.add(rightQueue3List, gbc);
    queuesPanel.add(new SubtitleLabel("Cola de Refuerzos"), gbc);
    queuesPanel.add(rightQueueRList, gbc);

    JPanel totalWinsPanel = new JPanel(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridy = 0;
    gbc.weightx = 1;
    totalWinsPanel.add(leftWinsLabel, gbc);
    gbc.weightx = 0;
    totalWinsPanel.add(new TitleLabel("vs"), gbc);
    gbc.weightx = 1;
    totalWinsPanel.add(rightWinsLabel, gbc);

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
    simulationPanel.add(new TitleLabel("Puntaje total"), gbc);
    simulationPanel.add(totalWinsPanel, gbc);
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
    queue.getObserver().addObserver((CharacterQueue q) -> this.fireContentsChanged(this, 0, q.size()));
  }

  @Override
  public String getElementAt(int index) {
    Character character = queue.getElementAt(index);
    return character != null ? character.getId() : "NULL";
  }

  @Override
  public int getSize() {
    return queue.size();
  }
};

class LimitedReverseQueueListModel extends AbstractListModel<String> {
  private CharacterQueue queue;
  private int limit;

  public LimitedReverseQueueListModel(CharacterQueue queue, int limit) {
    this.queue = queue;
    this.limit = limit;
    queue.getObserver()
        .addObserver((CharacterQueue q) -> this.fireContentsChanged(this, 0, Math.min(queue.size(), limit)));
  }

  @Override
  public String getElementAt(int index) {
    Character character = queue.getElementAt(queue.size() - index - 1);
    return character != null ? character.getId() : "NULL";
  }

  @Override
  public int getSize() {
    return Math.min(queue.size(), limit);
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