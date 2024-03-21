package animated_sequel.characters;

import javax.swing.Icon;

public class Character {
  public static final int EXCEPTIONAL_TYPE = 0;
  public static final int AVERAGE_TYPE = 1;
  public static final int DEFICIENT_TYPE = 2;

  public static final int STANDARD_STAT = 0;
  public static final int QUALITY_STAT = 1;

  private final String id;
  private final int type;
  private final int skills;
  private final int lifePoints;
  private final int strength;
  private final int agility;
  private final Icon icon;

  private int roundCounter = 0;

  public Character(String id, int skills, int lifePoints, int strength, int agility, Icon icon) {
    this.id = id;
    this.skills = skills;
    this.lifePoints = lifePoints;
    this.strength = strength;
    this.agility = agility;
    this.icon = icon;

    int rating = skills + lifePoints + strength + agility;

    if (rating == 4) {
      this.type = EXCEPTIONAL_TYPE;
    } else if (rating == 3) {
      this.type = AVERAGE_TYPE;
    } else {
      this.type = DEFICIENT_TYPE;
    }
  }

  public int incrementRoundCounter() {
    roundCounter += 1;
    return roundCounter;
  }

  public void resetRoundCounter() {
    roundCounter = 0;
  }

  public int getRoundCounter() {
    return roundCounter;
  }

  public String getId() {
    return id;
  }

  public int getAgility() {
    return agility;
  }

  public int getLifePoints() {
    return lifePoints;
  }

  public int getSkills() {
    return skills;
  }

  public int getStrength() {
    return strength;
  }

  public int getType() {
    return type;
  }

  public Icon getIcon() {
    return icon;
  }
}
