package animated_sequel.simulation;

import animated_sequel.characters.Character;
import animated_sequel.queue.CharacterQueue;
import animated_sequel.utils.Observer;

/**
 *
 * @author USUARIO
 */
public class IA {
    public static final int LEFT_CHARACTER_WON = 0;
    public static final int RIGHT_CHARACTER_WON = 1;
    public static final int FIGHT_TIED = 2;
    public static final int FIGHT_SUSPENDED = 3;

    public static final int WAITING_STATUS = 0;
    public static final int DECIDING_STATUS = 1;
    public static final int ANNOUNCING_STATUS = 2;

    private final Observer<Integer> resultObserver = new Observer<>();
    private final Observer<Integer> statusObserver = new Observer<>();
    private final Observer<Character[]> fightObserver = new Observer<>();

    private Administrator administrator;
    private CharacterQueue winners;
    private int status;

    public IA(Administrator administrator) {
        this.administrator = administrator;
        winners = new CharacterQueue();
    }

    public void combat(Character leftCharacter, Character rightCharacter) {
        fightObserver.notify(new Character[] { leftCharacter, rightCharacter });

        administrator.sleepSeconds(3);
        setStatus(DECIDING_STATUS);
        administrator.sleepSeconds(4);
        setStatus(ANNOUNCING_STATUS);

        double result = Math.random();

        if (result <= 0.4) {
            Character winner = determineWinner(leftCharacter, rightCharacter);
            winners.add(winner);
            System.out.println("The winner is: " + winner.getId());

            if (winner == leftCharacter) {
                administrator.getLeftStudio().incrementWinCount();
                resultObserver.notify(LEFT_CHARACTER_WON);
            } else {
                administrator.getRightStudio().incrementWinCount();
                resultObserver.notify(RIGHT_CHARACTER_WON);
            }
        } else if (result <= 0.67) {
            System.out.println("The battle ended in a draw.");

            administrator.getLeftStudio().getPriorityQueue()[Character.EXCEPTIONAL_TYPE].add(leftCharacter);
            administrator.getRightStudio().getPriorityQueue()[Character.EXCEPTIONAL_TYPE].add(rightCharacter);

            resultObserver.notify(FIGHT_TIED);

        } else {
            System.out.println("The battle was suspended.");

            administrator.getLeftStudio().getReinforceQueue().add(leftCharacter);
            administrator.getRightStudio().getReinforceQueue().add(rightCharacter);

            resultObserver.notify(FIGHT_SUSPENDED);
        }

        administrator.sleepSeconds(3);
        setStatus(WAITING_STATUS);
    }

    public Character determineWinner(Character cartoonCharacter, Character nickelodeonCharacter) {
        int scoreCartoon = 2 * cartoonCharacter.getLifePoints() + 3 * cartoonCharacter.getStrength()
                + cartoonCharacter.getAgility()
                + 4 * cartoonCharacter.getSkills();
        int scoreNickelodeon = 2 * nickelodeonCharacter.getLifePoints() + 3 * nickelodeonCharacter.getStrength()
                + nickelodeonCharacter.getAgility()
                + 4 * nickelodeonCharacter.getSkills();

        if (scoreCartoon > scoreNickelodeon) {
            return cartoonCharacter;
        } else if (scoreNickelodeon > scoreCartoon) {
            return nickelodeonCharacter;
        } else {
            return Math.random() < 0.5 ? cartoonCharacter : nickelodeonCharacter;
        }

    }

    public void setStatus(int status) {
        this.status = status;
        statusObserver.notify(status);
    }

    public int getStatus() {
        return status;
    }

    public CharacterQueue getWinners() {
        return winners;
    }

    public Observer<Character[]> getFightObserver() {
        return fightObserver;
    }

    public Observer<Integer> getResultObserver() {
        return resultObserver;
    }

    public Observer<Integer> getStatusObserver() {
        return statusObserver;
    }
}
