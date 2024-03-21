/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animated_sequel.simulation;
import animated_sequel.studios.CartoonNetwork;
import animated_sequel.studios.Nickelodeon;
import animated_sequel.queue.PriorityQueue;
import animated_sequel.characters.Character;

/**
 *
 * @author USUARIO
 */

public class Administrator {
    private CartoonNetwork cartoon;
    private Nickelodeon nickelodeon;
    private IA ia;

    public Administrator(){
        cartoon = new CartoonNetwork();
        nickelodeon = new Nickelodeon();
        ia = new IA();
    }

    public void assignCharacters() {
        cartoon.generateCharacters();
        nickelodeon.generateCharacters();

        PriorityQueue<Character> cartoonQueue1 = cartoon.getPriorityQueue1();
        PriorityQueue<Character> cartoonQueue2 = cartoon.getPriorityQueue2();
        PriorityQueue<Character> cartoonQueue3 = cartoon.getPriorityQueue3();
        PriorityQueue<Character> cartoonReinforceQueue = cartoon.getReinforceQueue();

        PriorityQueue<Character> nickelodeonQueue1 = nickelodeon.getPriorityQueue1();
        PriorityQueue<Character> nickelodeonQueue2 = nickelodeon.getPriorityQueue2();
        PriorityQueue<Character> nickelodeonQueue3 = nickelodeon.getPriorityQueue3();
        PriorityQueue<Character> nickelodeonReinforceQueue = nickelodeon.getReinforceQueue();

        while (!cartoonReinforceQueue.isEmpty()) {
            Character character = cartoonReinforceQueue.poll();
            int priorityLevel = character.getPriorityLevel();
            switch (priorityLevel) {
                case 1:
                    cartoonQueue1.add(character,character.getPriorityLevel());
                    break;
                case 2:
                    cartoonQueue2.add(character, character.getPriorityLevel());
                    break;
                case 3:
                    cartoonQueue3.add(character, character.getPriorityLevel());
                    break;
            }
        }

        while (!nickelodeonReinforceQueue.isEmpty()) {
            Character character = nickelodeonReinforceQueue.poll();
            int priorityLevel = character.getPriorityLevel();
            switch (priorityLevel) {
                case 1:
                    nickelodeonQueue1.add(character, character.getPriorityLevel());
                    break;
                case 2:
                    nickelodeonQueue2.add(character, character.getPriorityLevel());
                    break;
                case 3:
                    nickelodeonQueue3.add(character, character.getPriorityLevel());
                    break;
            }
        }
    }
    
    public void performCombatAssignment() {
        int countRound=0;
        PriorityQueue<Character>[] cartoonQueues = new PriorityQueue[]{
            cartoon.getPriorityQueue1(),
            cartoon.getPriorityQueue2(),
            cartoon.getPriorityQueue3()
        };

        PriorityQueue<Character>[] nickelodeonQueues = new PriorityQueue[]{
            nickelodeon.getPriorityQueue1(),
            nickelodeon.getPriorityQueue2(),
            nickelodeon.getPriorityQueue3()
        };

        for (int i = 0; i < cartoonQueues.length; i++) {
            PriorityQueue<Character> cartoonQueue = cartoonQueues[i];
            PriorityQueue<Character> nickelodeonQueue = nickelodeonQueues[i];

            while (!cartoonQueue.isEmpty() && !nickelodeonQueue.isEmpty()) {
                if(countRound==2){
                    addNewCharacters();
                    countRound=0;
                }
                Character characterCartoon = cartoonQueue.poll();
                Character characterNickelodeon = nickelodeonQueue.poll();

                ia.combat(characterCartoon, characterNickelodeon, nickelodeonQueue, cartoonQueue, cartoon.getPriorityQueue1(), nickelodeon.getPriorityQueue1(), nickelodeon.getReinforceQueue(), cartoon.getReinforceQueue());
                verifyCounter(cartoon.getPriorityQueue1(), cartoon.getPriorityQueue2(), cartoon.getPriorityQueue3(), nickelodeon.getPriorityQueue1(), nickelodeon.getPriorityQueue2(), nickelodeon.getPriorityQueue3());
                countRound++;
            }
        }
    }
    
    public void addNewCharacters() {
        double randomValue = Math.random();

        if (randomValue <= 0.8) {

            Character newCartoonCharacter = cartoon.createCharacter();
            switch (newCartoonCharacter.getPriorityLevel()) {
                case 1 -> cartoon.getPriorityQueue1().add(newCartoonCharacter, newCartoonCharacter.getPriorityLevel());
                case 2 -> cartoon.getPriorityQueue2().add(newCartoonCharacter, newCartoonCharacter.getPriorityLevel());
                default -> cartoon.getPriorityQueue3().add(newCartoonCharacter, newCartoonCharacter.getPriorityLevel());
            }
                
            Character newNickelodeonCharacter = nickelodeon.createCharacter();
            switch (newNickelodeonCharacter.getPriorityLevel()) {
                case 1 -> nickelodeon.getPriorityQueue1().add(newNickelodeonCharacter, newNickelodeonCharacter.getPriorityLevel());
                case 2 -> nickelodeon.getPriorityQueue2().add(newNickelodeonCharacter, newNickelodeonCharacter.getPriorityLevel());
                default -> nickelodeon.getPriorityQueue3().add(newNickelodeonCharacter, newNickelodeonCharacter.getPriorityLevel());
            }
            
        }
    }
    
    public void verifyCounter(PriorityQueue<Character> cartoonQueue1,PriorityQueue<Character> cartoonQueue2, PriorityQueue<Character> cartoonQueue3,PriorityQueue<Character> nickelodeonQueue1, PriorityQueue<Character> nickelodeonQueue2, PriorityQueue<Character> nickelodeonQueue3) {
        verifyAndMoveCharacters(cartoonQueue3, cartoonQueue2);
        verifyAndMoveCharacters(cartoonQueue2, cartoonQueue1);
        verifyAndMoveCharacters(nickelodeonQueue3, nickelodeonQueue2);
        verifyAndMoveCharacters(nickelodeonQueue2, nickelodeonQueue1);
    }

    private void verifyAndMoveCharacters(PriorityQueue<Character> sourceQueue, PriorityQueue<Character> targetQueue) {
        PriorityQueue auxQueue = new PriorityQueue();
        
        while (!sourceQueue.isEmpty()) {
            Character character = sourceQueue.poll();
            character.increaseCounter();

            if (character.getCounter() >= 8) {
                character.resetCounter();
                if(character.getPriorityLevel()==2){
                    character.setPriorityLevel(1);
                } else if(character.getPriorityLevel()==3){
                    character.setPriorityLevel(2);
                }
                targetQueue.add(character, character.getPriorityLevel());
                sourceQueue.remove(character);
            } else {
                auxQueue.add(character, character.getPriorityLevel());
            }
        }
        
        sourceQueue = auxQueue;
    }
}
    
    