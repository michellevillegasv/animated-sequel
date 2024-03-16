package unimet.regular_crossover;

public class ArtificialIntelligence {
  private static final double TIE_ODDS = 0.27;
  private static final double SUSPEND_ODDS = 0.33;

  private static final double TYPE_WEIGHT = 0.4;
  private static final double STATS_WEIGHT = 0.4;
  private static final double RANDOM_WEIGHT = 1 - TYPE_WEIGHT - STATS_WEIGHT;

  public static final int LEFT_CHARACTER_WON = 0;
  public static final int RIGHT_CHARACTER_WON = 1;
  public static final int FIGHT_TIED = 2;
  public static final int FIGHT_SUSPENDED = 3;

  public static final int WAITING_STATUS = 0;
  public static final int DECIDING_STATUS = 1;
  public static final int ANNOUNCING_STATUS = 2;

  private static final int DECIDING_DELAY = 10;

  private final Simulation simulation;

  private int status = WAITING_STATUS;

  public ArtificialIntelligence(Simulation simulation) {
    this.simulation = simulation;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

  public void simulateRound() {
    /* Obtener los personajes que pelearán */
    Character leftCharacter = simulation.getLeftTeam().dequeueNextCharacter();
    Character rightCharacter = simulation.getRightTeam().dequeueNextCharacter();

    /* Dormir mientras decide */
    setStatus(DECIDING_STATUS);
    simulation.sleepSeconds(DECIDING_DELAY);

    /* Tomar decisión */
    int result = performFight(leftCharacter, rightCharacter);

    setStatus(ANNOUNCING_STATUS);

    switch (result) {
      case LEFT_CHARACTER_WON:
        /* Agregar personaje izquierdo a lista de ganadores */
        simulation.getWinners().enqueue(leftCharacter);
        simulation.getLeftTeam().incrementWinCount();
        break;
      case RIGHT_CHARACTER_WON:
        /* Agregar personaje derecho a lista de ganadores */
        simulation.getWinners().enqueue(rightCharacter);
        simulation.getRightTeam().incrementWinCount();
        break;
      case FIGHT_TIED:
        /* Mover personajes a cola de nivel 1 */
        simulation.getLeftTeam().enqueuePriority(leftCharacter);
        simulation.getRightTeam().enqueuePriority(rightCharacter);
        break;
      case FIGHT_SUSPENDED:
        /* Mover personajes a cola de refuerzo */
        simulation.getLeftTeam().enqueueReinforcement(leftCharacter);
        simulation.getRightTeam().enqueueReinforcement(rightCharacter);
        break;
    }

    setStatus(WAITING_STATUS);
  }

  public int performFight(Character leftCharacter, Character rightCharacter) {
    double randomResult = Math.random();

    if (randomResult < TIE_ODDS) {
      return FIGHT_TIED;
    } else if (randomResult < TIE_ODDS + SUSPEND_ODDS) {
      return FIGHT_SUSPENDED;
    } else {
      /*
       * Calcular ganador en base a un ponderado de la calidad total, la estadísticas
       * (Fuerza->Vida y Habilidad->Agilidad), y un factor aleatorio
       */
      double randomFactor = Math.random();
      double leftCharacterPoints = TYPE_WEIGHT * compare(leftCharacter.getType(), rightCharacter.getType())
          + STATS_WEIGHT * (compare(leftCharacter.getStrength(), rightCharacter.getLifePoints()) +
              compare(leftCharacter.getSkills(), rightCharacter.getAgility())) / 2
          + RANDOM_WEIGHT * randomFactor;
      double rightCharacterPoints = TYPE_WEIGHT * compare(rightCharacter.getType(), leftCharacter.getType())
          + STATS_WEIGHT * (compare(rightCharacter.getStrength(), leftCharacter.getLifePoints()) +
              compare(rightCharacter.getSkills(), leftCharacter.getAgility())) / 2
          + RANDOM_WEIGHT * (1 - randomFactor);

      return leftCharacterPoints >= rightCharacterPoints ? LEFT_CHARACTER_WON : RIGHT_CHARACTER_WON;
    }
  }

  private int compare(int a, int b) {
    return a > b ? 1 : 0;
  }
}
