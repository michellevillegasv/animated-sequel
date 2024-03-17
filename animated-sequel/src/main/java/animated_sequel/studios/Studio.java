/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animated_sequel.studios;

import java.util.Random;
import animated_sequel.characters.Character;
import animated_sequel.queue.PriorityQueue;

/**
 *
 * @author USUARIO
 */
class Studio {
    private static final int NUM_PERSONAJES = 20;

    private PriorityQueue<Character> priorityQueue1;
    private PriorityQueue<Character> priorityQueue2;
    private PriorityQueue<Character> priorityQueue3;
    private PriorityQueue<Character> reinforceQueue;
    private String studioName;
   

    public Studio(String name) {
        priorityQueue1 = new PriorityQueue<>();
        priorityQueue2 = new PriorityQueue<>();
        priorityQueue3 = new PriorityQueue<>();
        reinforceQueue = new PriorityQueue<>();
        this.studioName = name;
    }
    
    public PriorityQueue<Character> getPriorityQueue1() {
        return priorityQueue1;
    }

    public void setPriorityQueue1(PriorityQueue<Character> priorityQueue1) {
        this.priorityQueue1 = priorityQueue1;
    }

    public PriorityQueue<Character> getPriorityQueue2() {
        return priorityQueue2;
    }

    public void setPriorityQueue2(PriorityQueue<Character> priorityQueue2) {
        this.priorityQueue2 = priorityQueue2;
    }

    public PriorityQueue<Character> getPriorityQueue3() {
        return priorityQueue3;
    }

    public void setPriorityQueue3(PriorityQueue<Character> priorityQueue3) {
        this.priorityQueue3 = priorityQueue3;
    }

    public PriorityQueue<Character> getReinforceQueue() {
        return reinforceQueue;
    }

    public void setReinforceQueue(PriorityQueue<Character> reinforceQueue) {
        this.reinforceQueue = reinforceQueue;
    }

    public void generateCharacters() {
        for (int i = 0; i < NUM_PERSONAJES; i++) {
            Character character = createCharacter();
            /*if (character.getPriorityLevel() == 1) {
                priorityQueue1.add(character, character.getPriorityLevel());
            } else if (character.getPriorityLevel() == 2) {
                priorityQueue2.add(character, character.getPriorityLevel());
            } else {
                priorityQueue3.add(character, character.getPriorityLevel());
            }*/
            reinforceQueue.add(character, character.getPriorityLevel());
        }
    }

    protected Character createCharacter() {
        
        var name = "";
        var company = studioName;
        int priorityLevel = determinePriorityLevel();
        int healthPoints = 0;
        int strength = 0;
        int agility = 0;
        int counter = 0;

        if (priorityLevel == 1) {
            name = "Exceptional Character";
            healthPoints = 100;
            strength = 100;
            agility = 100;
        } else if (priorityLevel == 2) {
            name = "Average Character";
            healthPoints = 80;
            strength = 80;
            agility = 80;
        } else {
            name = "Deficient Character";
            healthPoints = 50;
            strength = 50;
            agility = 50;
        }

        return new Character(name, company, priorityLevel, healthPoints, strength, agility, counter);
    }

    private int determinePriorityLevel() {
        boolean[] quality = {
                isQuality(0.6), // Habilidades
                isQuality(0.7), // Puntos de vida
                isQuality(0.5), // Fuerza
                isQuality(0.4)  // Agilidad
        };

        if (quality[0] && quality[1] && quality[2] && quality[3]) {
            return 1; // Excepcional
        } else if (quality[0] || quality[1] || quality[2] || quality[3]) {
            return 2; // Promedio
        } else {
            return 3; // Deficiente
        }
    }

    private boolean isQuality(double probability) {
        Random random = new Random();
        return random.nextDouble() < probability;
    }
}
