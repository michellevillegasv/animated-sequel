package animated_sequel.queue;

import java.util.function.Consumer;

import animated_sequel.characters.Character;
import animated_sequel.utils.Observer;

public class CharacterQueue {
    private final Observer<CharacterQueue> observer = new Observer<>();

    private Node head;
    private Node tail;
    private int size;

    public void add(Character data) {
        Node newNode = new Node(data);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }

        size++;

        observer.notify(this);
    }

    public Character peek() {
        if (head == null) {
            return null;
        }
        return head.getData();
    }

    public Character poll() {
        if (isEmpty()) {
            return null;
        }

        Character data = head.getData();
        head = head.getNext();
        if (head == null) {
            tail = null;
        }

        size--;

        observer.notify(this);
        return data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void forEach(Consumer<Character> consumer) {
        for (Node node = head; node != null; node = node.getNext()) {
            consumer.accept(node.getData());
        }
    }

    public Character getElementAt(int index) {
        int i = 0;
        for (Node node = head; node != null; node = node.getNext()) {
            if (index == i++) {
                return node.getData();
            }
        }
        return null;
    }

    public Observer<CharacterQueue> getObserver() {
        return observer;
    }

}
