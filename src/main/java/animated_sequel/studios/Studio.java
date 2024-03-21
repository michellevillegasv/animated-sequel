package animated_sequel.studios;

import animated_sequel.characters.Character;
import animated_sequel.characters.CharacterFactory;
import animated_sequel.queue.CharacterQueue;
import animated_sequel.utils.Observer;

/**
 *
 * @author USUARIO
 */
public class Studio {
    private static final int NUM_PERSONAJES = 20;

    private final Observer<Integer> winsObserver = new Observer<>();

    private CharacterQueue[] priorityQueue;
    private CharacterQueue reinforceQueue;
    private String studioName;

    private CharacterFactory factory;

    private int winCount = 0;

    public Studio(String name, CharacterFactory factory) {
        this.studioName = name;
        this.factory = factory;

        priorityQueue = new CharacterQueue[] {
                new CharacterQueue(),
                new CharacterQueue(),
                new CharacterQueue()
        };
        reinforceQueue = new CharacterQueue();

        for (int i = 0; i < NUM_PERSONAJES; i++) {
            addCharacter();
        }
    }

    public void addCharacter() {
        Character character = factory.createCharacter();
        priorityQueue[character.getType()].add(character);
    }

    public Character getNextCharacter() {
        /* Obtener siguiente personaje */
        if (!priorityQueue[Character.EXCEPTIONAL_TYPE].isEmpty()) {
            return priorityQueue[Character.EXCEPTIONAL_TYPE].poll();
        } else if (!priorityQueue[Character.AVERAGE_TYPE].isEmpty()) {
            return priorityQueue[Character.AVERAGE_TYPE].poll();
        } else if (!priorityQueue[Character.DEFICIENT_TYPE].isEmpty()) {
            return priorityQueue[Character.DEFICIENT_TYPE].poll();
        }
        return null;
    }

    public boolean hasNextCharacter() {
        /* Verifica si hay un personaje en cola */
        return !priorityQueue[Character.EXCEPTIONAL_TYPE].isEmpty() || !priorityQueue[Character.AVERAGE_TYPE].isEmpty()
                || !priorityQueue[Character.DEFICIENT_TYPE].isEmpty();
    }

    public int incrementWinCount() {
        winCount += 1;
        winsObserver.notify(winCount);
        return winCount;
    }

    public String getStudioName() {
        return studioName;
    }

    public CharacterQueue[] getPriorityQueue() {
        return priorityQueue;
    }

    public CharacterQueue getReinforceQueue() {
        return reinforceQueue;
    }

    public int getWinCount() {
        return winCount;
    }

    public Observer<Integer> getWinsObserver() {
        return winsObserver;
    }
}
