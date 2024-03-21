package unimet.regular_crossover;

public class Simulation extends Thread {
  private ArtificialIntelligence artificialIntelligence;
  private Administrator administrator;
  private Team leftTeam;
  private Team rightTeam;
  private int speed = 1;

  private CharacterQueue winners = new CharacterQueue();

  public Simulation() {
    artificialIntelligence = new ArtificialIntelligence(this);
    administrator = new Administrator(this);
    leftTeam = new Team("Avatar: The Last Airbender", "ATLA");
    rightTeam = new Team("Regular Show", "RS");
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
