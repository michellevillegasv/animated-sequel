package unimet.regular_crossover;

public class Team {
  private static final int INITIAL_CHARACTER_COUNT = 20;
  private static final double REINFORCEMENT_ODDS = 0.4;

  private final CharacterQueue[] priorityQueue = new CharacterQueue[] {
      new CharacterQueue(),
      new CharacterQueue(),
      new CharacterQueue()
  };
  private final CharacterQueue reinforcementQueue = new CharacterQueue();
  private final CharacterFactory factory;

  private final String name;
  private final String code;

  private int winCount = 0;

  public Team(String name, String code) {
    this.name = name;
    this.code = code;

    factory = new CharacterFactory(code);

    /* Crear personajes iniciales */
    for (int i = 0; i < INITIAL_CHARACTER_COUNT; i++) {
      Character character = factory.createCharacter();
      priorityQueue[character.getType()].enqueue(character);
    }
  }

  /* Crear personaje adicional */
  public void addCharacter() {
    Character character = factory.createCharacter();
    priorityQueue[character.getType()].enqueue(character);
  }

  /* Encolar en cola de prioridad nivel 1 */
  public void enqueuePriority(Character character) {
    priorityQueue[Character.EXCEPTIONAL_TYPE].enqueue(character);
  }

  /* Encolar en cola de refuerzo */
  public void enqueueReinforcement(Character character) {
    reinforcementQueue.enqueue(character);
  }

  public Character dequeueNextCharacter() {
    /* Obtener siguiente personaje */
    if (!priorityQueue[Character.EXCEPTIONAL_TYPE].isEmpty()) {
      return priorityQueue[Character.EXCEPTIONAL_TYPE].dequeue();
    } else if (!priorityQueue[Character.AVERAGE_TYPE].isEmpty()) {
      return priorityQueue[Character.AVERAGE_TYPE].dequeue();
    } else if (!priorityQueue[Character.DEFICIENT_TYPE].isEmpty()) {
      return priorityQueue[Character.DEFICIENT_TYPE].dequeue();
    }
    return null;
  }

  public boolean hasNextCharacter() {
    /* Verifica si hay un personaje en cola */
    return !priorityQueue[Character.EXCEPTIONAL_TYPE].isEmpty() && !priorityQueue[Character.AVERAGE_TYPE].isEmpty()
        && !priorityQueue[Character.DEFICIENT_TYPE].isEmpty();
  }

  public void updateQueues() {
    /* Incrementar contador de rondas en las colas de nivel 2 y 3 */
    priorityQueue[Character.AVERAGE_TYPE].forEach(character -> {
      character.incrementRoundCounter();
    });

    priorityQueue[Character.DEFICIENT_TYPE].forEach(character -> {
      character.incrementRoundCounter();
    });

    /* Subir de nivel a los personajes con 8 o más rondas inactivos */
    while (!priorityQueue[Character.AVERAGE_TYPE].isEmpty()
        && priorityQueue[Character.AVERAGE_TYPE].peek().getRoundCounter() >= 8) {
      Character character = priorityQueue[Character.AVERAGE_TYPE].dequeue();
      character.resetRoundCounter();
      priorityQueue[Character.EXCEPTIONAL_TYPE].enqueue(character);
    }

    while (!priorityQueue[Character.DEFICIENT_TYPE].isEmpty()
        && priorityQueue[Character.DEFICIENT_TYPE].peek().getRoundCounter() >= 8) {
      Character character = priorityQueue[Character.DEFICIENT_TYPE].dequeue();
      character.resetRoundCounter();
      priorityQueue[Character.AVERAGE_TYPE].enqueue(character);
    }

    if (!reinforcementQueue.isEmpty()) {
      /*
       * Subir de nivel o enviar al final al primer personaje de la cola de refuerzo
       */
      if (Math.random() < REINFORCEMENT_ODDS) {
        priorityQueue[Character.EXCEPTIONAL_TYPE].enqueue(reinforcementQueue.dequeue());
      } else {
        reinforcementQueue.enqueue(reinforcementQueue.dequeue());
      }
    }
  }

  public int incrementWinCount() {
    winCount += 1;
    return winCount;
  }

  public int getWinCount() {
    return winCount;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public CharacterQueue[] getPriorityQueue() {
    return priorityQueue;
  }

  public CharacterQueue getReinforcementQueue() {
    return reinforcementQueue;
  }
}
