package unimet.regular_crossover;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Simulation extends Thread {
  private ArtificialIntelligence artificialIntelligence;
  private Administrator administrator;
  private Team leftTeam;
  private Team rightTeam;
  private int speed = 1;

  private CharacterQueue winners = new CharacterQueue();

  public Simulation() {
    Icon[] avatarResources = new Icon[20];
    for (int i = 0; i < 20; i++) {
      avatarResources[i] = new ImageIcon(
          ClassLoader.getSystemResource((String.format("avatar-icons/%02d.png", i + 1))));
    }

    artificialIntelligence = new ArtificialIntelligence(this);
    administrator = new Administrator(this);
    leftTeam = new Team("Avatar: The Last Airbender", new CharacterFactory("ATLA", avatarResources));
    rightTeam = new Team("Regular Show", new CharacterFactory("RS", avatarResources));
  }

  @Override
  public void run() {
    /* Ciclo de ejecución: alternar entre IA y Administrador */
    while (!isInterrupted()) {
      artificialIntelligence.simulateRound();
      administrator.simulateRound();
    }
  }

  public ArtificialIntelligence getArtificialIntelligence() {
    return artificialIntelligence;
  }

  public Administrator getAdministrator() {
    return administrator;
  }

  public Team getLeftTeam() {
    return leftTeam;
  }

  public Team getRightTeam() {
    return rightTeam;
  }

  public CharacterQueue getWinners() {
    return winners;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  protected void sleepSeconds(int seconds) {
    try {
      Thread.sleep((long) (seconds * 1000 / speed));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
