package unimet.regular_crossover;

public class Administrator {
  private static final double CREATE_CHARACTER_ODDS = 0.8;

  private final Simulation simulation;

  private int roundCounter = 0;

  public Administrator(Simulation simulation) {
    this.simulation = simulation;
  }

  public void simulateRound() {
    roundCounter = (roundCounter + 1) % 2;
    simulation.getLeftTeam().updateQueues();
    simulation.getRightTeam().updateQueues();

    /*
     * Cada dos turnos existe un 80% de probabilidad de crear un par de personajes
     */
    if (roundCounter == 0 && Math.random() < CREATE_CHARACTER_ODDS) {
      simulation.getLeftTeam().addCharacter();
      simulation.getRightTeam().addCharacter();
    }
  }
}
