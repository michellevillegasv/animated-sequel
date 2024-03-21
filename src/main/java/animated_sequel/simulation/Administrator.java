package animated_sequel.simulation;

import animated_sequel.studios.Studio;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import animated_sequel.characters.Character;
import animated_sequel.characters.CharacterFactory;

/**
 *
 * @author USUARIO
 */

public class Administrator extends Thread {
    private Studio leftStudio;
    private Studio rightStudio;
    private IA ia;

    private int roundCounter = 0;
    private int speed = 1;

    public Administrator() {
        Icon[] avatarResources = new Icon[20];
        Icon[] regularShowResources = new Icon[20];
        for (int i = 0; i < 20; i++) {
            avatarResources[i] = new ImageIcon(
                    ClassLoader.getSystemResource((String.format("avatar-icons/%02d.png", i + 1))));
            regularShowResources[i] = new ImageIcon(
                    ClassLoader.getSystemResource((String.format("regular-show-icons/%02d.png", i + 1))));
        }

        leftStudio = new Studio("Avatar: The Last Airbender", new CharacterFactory("ATLA", avatarResources));
        rightStudio = new Studio("Regular Show", new CharacterFactory("RS", regularShowResources));

        ia = new IA(this);
    }

    @Override
    public void run() {
        /* Ciclo de ejecución: alternar entre IA y Administrador */
        while (!isInterrupted()) {
            if (!leftStudio.hasNextCharacter() || !rightStudio.hasNextCharacter()) {
                continue;
            }

            Character leftCharacter = leftStudio.getNextCharacter();
            Character rightCharacter = rightStudio.getNextCharacter();

            ia.combat(leftCharacter, rightCharacter);

            roundCounter = (roundCounter + 1) % 2;

            updateQueues(leftStudio);
            updateQueues(rightStudio);

            if (!leftStudio.getReinforceQueue().isEmpty() && !rightStudio.getReinforceQueue().isEmpty()) {
                /*
                 * Subir de nivel o enviar al final al primer personaje de la cola de refuerzo
                 */
                if (Math.random() < 0.4) {
                    leftStudio.getPriorityQueue()[Character.EXCEPTIONAL_TYPE]
                            .add(leftStudio.getReinforceQueue().poll());
                    rightStudio.getPriorityQueue()[Character.EXCEPTIONAL_TYPE]
                            .add(rightStudio.getReinforceQueue().poll());
                } else {
                    leftStudio.getReinforceQueue().add(leftStudio.getReinforceQueue().poll());
                    rightStudio.getReinforceQueue().add(rightStudio.getReinforceQueue().poll());
                }
            }

            /*
             * Cada dos turnos existe un 80% de probabilidad de crear un par de personajes
             */
            if (roundCounter == 0) {
                addNewCharacters();
            }
        }
    }

    public void addNewCharacters() {
        double randomValue = Math.random();

        if (randomValue <= 0.8) {
            leftStudio.addCharacter();
            rightStudio.addCharacter();
        }
    }

    public void updateQueues(Studio studio) {
        /* Incrementar contador de rondas en las colas de nivel 2 y 3 */
        studio.getPriorityQueue()[Character.AVERAGE_TYPE].forEach(character -> {
            character.incrementRoundCounter();
        });

        studio.getPriorityQueue()[Character.DEFICIENT_TYPE].forEach(character -> {
            character.incrementRoundCounter();
        });

        /* Subir de nivel a los personajes con 8 o más rondas inactivos */
        while (!studio.getPriorityQueue()[Character.AVERAGE_TYPE].isEmpty()
                && studio.getPriorityQueue()[Character.AVERAGE_TYPE].peek().getRoundCounter() >= 8) {
            Character character = studio.getPriorityQueue()[Character.AVERAGE_TYPE].poll();
            character.resetRoundCounter();
            studio.getPriorityQueue()[Character.EXCEPTIONAL_TYPE].add(character);
        }

        while (!studio.getPriorityQueue()[Character.DEFICIENT_TYPE].isEmpty()
                && studio.getPriorityQueue()[Character.DEFICIENT_TYPE].peek().getRoundCounter() >= 8) {
            Character character = studio.getPriorityQueue()[Character.DEFICIENT_TYPE].poll();
            character.resetRoundCounter();
            studio.getPriorityQueue()[Character.AVERAGE_TYPE].add(character);
        }
    }

    public Studio getRightStudio() {
        return rightStudio;
    }

    public Studio getLeftStudio() {
        return leftStudio;
    }

    public IA getIA() {
        return ia;
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
