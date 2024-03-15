/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animated_sequel.characters;

import java.util.Random;

/**
 *
 * @author USUARIO
 */
public class Character {
    private static int nextId = 1;

    private int id;
    private String name;
    private String company;
    private int priorityLevel;
    private int healthPoints;
    private int strength;
    private int agility;
    private String ability;

    public Character(String name, String company) {
        this.id = nextId++;
        this.name = name;
        this.company = company;
        this.priorityLevel = 0;
    }

    public Character(String name, String company, int priorityLevel,  int healthPoints, int strength, int agility) {
        this.id = nextId++;
        this.name = name;
        this.company = company;
        this.priorityLevel = priorityLevel;
        this.healthPoints = healthPoints;
        this.strength = strength;
        this.agility = agility;
        this.ability = generateRandomAbility();
    }


    public String getName() {
        return name;
    }

  public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public String getAbility() {
        return ability;
    }

    public int getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }
    
    private String generateRandomAbility() {
        String[] abilities = {"Fireball", "Healing Touch", "Stealth", "Telekinesis", "Ice Blast"};
        Random random = new Random();
        int index = random.nextInt(abilities.length);
        return abilities[index];
    }
    
    public boolean isExceptional() {
        return isQuality(0.6);
    }

    public boolean isAverage() {
        return isQuality(0.5);
    }

    public boolean isDeficient() {
        return isQuality(0.4);
    }

    private boolean isQuality(double probability) {
        Random random = new Random();
        return random.nextDouble() <= probability;
    }
    
}