package animated_sequel.queue;

import animated_sequel.characters.Character;

public class Node {
    private Character data;
    private Node next;
    private int counter;

    public Node(Character data) {
        this.data = data;
        this.next = null;
        this.counter = 0;
    }

    public Character getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void incrementCounter() {
        counter++;
    }

    public void resetCounter() {
        counter = 0;
    }

    public boolean isCounterReachedLimit(int limit) {
        return counter >= limit;
    }
}
