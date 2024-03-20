/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animated_sequel.simulation;

import java.util.ArrayList;
import java.util.List;
import animated_sequel.characters.Character;
import animated_sequel.queue.PriorityQueue;

/**
 *
 * @author USUARIO
 */
public class IA {
    private List<Integer> winners;

    public IA() {
        winners = new ArrayList<>();
    }

    public void combat(Character cartoonCharacter, Character nickelodeonCharacter, PriorityQueue nickelodeonQueue, PriorityQueue cartoonQueue, PriorityQueue cartoonPriority1, PriorityQueue nickelodeonPriority1,
            PriorityQueue reinforceNickelodeon, PriorityQueue reinforceCartoon) {
        double result = Math.random();
        if (result <= 0.4) {
            Character winner = determineWinner(cartoonCharacter, nickelodeonCharacter);
            winners.add(winner.getId());
            System.out.println("The winner is: " + winner.getName());
            
            if (winner == cartoonCharacter) {
                nickelodeonQueue.remove(nickelodeonCharacter);
                reinforceNickelodeon.remove(nickelodeonCharacter);
            } else {
                cartoonQueue.remove(cartoonCharacter);
                reinforceCartoon.remove(cartoonCharacter);
            }
        } else if (result <= 0.67) {
            System.out.println("The battle ended in a draw.");
            
            cartoonCharacter.setPriorityLevel(1);
            cartoonQueue.remove(cartoonCharacter);
            cartoonPriority1.add(cartoonCharacter,cartoonCharacter.getPriorityLevel());
            
            nickelodeonCharacter.setPriorityLevel(1);
            nickelodeonQueue.remove(nickelodeonCharacter);
            nickelodeonPriority1.add(nickelodeonCharacter,nickelodeonCharacter.getPriorityLevel());
           
        } else {
            reinforceCartoon.add(cartoonCharacter, cartoonCharacter.getPriorityLevel());
            reinforceNickelodeon.add(nickelodeonCharacter, nickelodeonCharacter.getPriorityLevel());
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Character determineWinner(Character cartoonCharacter, Character nickelodeonCharacter) {
        
        int healthCartoon = cartoonCharacter.getHealthPoints();
        int strengthCartoon = cartoonCharacter.getStrength();
        int agilityCartoon = cartoonCharacter.getAgility();
        String abilityCartoon = cartoonCharacter.getAbility();

        int healthNickelodeon = nickelodeonCharacter.getHealthPoints();
        int strengthNickelodeon = nickelodeonCharacter.getStrength();
        int agilityNickelodeon = nickelodeonCharacter.getAgility();
        String abilityNickelodeon = nickelodeonCharacter.getAbility();

        int scoreCartoon = 2 * healthCartoon + 3 * strengthCartoon + agilityCartoon + calculateSpecialAbilityScore(abilityCartoon);
        int scoreNickelodeon = 2 * healthNickelodeon + 3 * strengthNickelodeon + agilityNickelodeon + calculateSpecialAbilityScore(abilityNickelodeon);

        if (scoreCartoon > scoreNickelodeon) {
            return cartoonCharacter;
        } else if (scoreNickelodeon > scoreCartoon) {
            return nickelodeonCharacter;
        } else {
            return Math.random() < 0.5 ? cartoonCharacter : nickelodeonCharacter;
        }
        
    }

    
    private int calculateSpecialAbilityScore(String specialAbility) {
        switch (specialAbility) {
            case "Fireball":
                return 10;
            case "Healing Touch":
                return 7;
            case "Stealth":
                return 5;
            case "Telekinesis":
                return 8;
            case "Ice Blast":
                return 9;
            default:
                return 0;
        }
    }

    public List<Integer> getWinners() {
        return winners;
    }
}
