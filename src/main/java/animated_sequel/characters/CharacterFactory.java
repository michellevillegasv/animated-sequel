package animated_sequel.characters;

import javax.swing.Icon;

public class CharacterFactory {
  private static final double QUALITY_SKILL_ODDS = 0.6;
  private static final double QUALITY_LIFE_POINTS_ODDS = 0.7;
  private static final double QUALITY_STRENGTH_ODDS = 0.5;
  private static final double QUALITY_AGILITY_ODDS = 0.4;

  private final String code;
  private final Icon[] icons;
  private int counter = 1;

  public CharacterFactory(String code, Icon[] icons) {
    this.code = code;
    this.icons = icons;
  }

  public Character createCharacter() {
    /* Calcula las calidades aleatorias de cada estadistica */
    int skills = Math.random() < QUALITY_SKILL_ODDS ? Character.QUALITY_STAT : Character.STANDARD_STAT;
    int lifePoints = Math.random() < QUALITY_LIFE_POINTS_ODDS ? Character.QUALITY_STAT : Character.STANDARD_STAT;
    int strength = Math.random() < QUALITY_STRENGTH_ODDS ? Character.QUALITY_STAT : Character.STANDARD_STAT;
    int agility = Math.random() < QUALITY_AGILITY_ODDS ? Character.QUALITY_STAT : Character.STANDARD_STAT;

    /* Crea el personaje con un ID único incremental */
    Character character = new Character(String.format("%s-%d", code, counter), skills, lifePoints, strength, agility,
        icons[counter % icons.length]);
    counter += 1;
    return character;
  }
}
