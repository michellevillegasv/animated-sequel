/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animated_sequel.simulation;
import animated_sequel.studios.CartoonNetwork;
import animated_sequel.studios.Nickelodeon;
import animated_sequel.queue.PriorityQueue;
import animated_sequel.characters.Character;
import java.util.ArrayList;
import java.util.List;
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
                Character characterCartoon = cartoonQueue.poll();
                Character characterNickelodeon = nickelodeonQueue.poll();

                ia.combat(characterCartoon, characterNickelodeon, nickelodeonQueue, cartoonQueue, cartoon.getPriorityQueue1(), nickelodeon.getPriorityQueue1(), nickelodeon.getReinforceQueue(), cartoon.getReinforceQueue());
            }
        }
    }
   public void verifyCounter(PriorityQueue cartoonQueue2, PriorityQueue cartoonQueue3, PriorityQueue nickelodeonQueue2, PriorityQueue nickelodeonQueue3){
       
       
   }
}
    
    