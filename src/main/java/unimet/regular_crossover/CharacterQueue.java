package unimet.regular_crossover;

import java.util.function.Consumer;

import unimet.regular_crossover.utils.Observer;

public class CharacterQueue {
  private final Observer<CharacterQueue> observer = new Observer<>();

  private Node head;
  private Node tail;
  private int size;

  public void enqueue(Character character) {
    Node node = new Node(character);
    if (isEmpty()) {
      head = tail = node;
    } else {
      tail.setNext(node);
      tail = node;
    }
    size += 1;

    observer.notify(this);
  }

  public Character dequeue() {
    if (isEmpty()) {
      return null;
    }
    Character character = head.getCharacter();
    head = head.getNext();
    if (head == null) {
      tail = null;
    }
    size -= 1;

    observer.notify(this);
    return character;
  }

  public Character peek() {
    if (isEmpty()) {
      return null;
    }
    return head.getCharacter();
  }

  public void forEach(Consumer<Character> consumer) {
    for (Node node = head; node != null; node = node.getNext()) {
      consumer.accept(node.getCharacter());
    }
  }

  public Character getElementAt(int index) {
    int i = 0;
    for (Node node = head; node != null; node = node.getNext()) {
      if (index == i++) {
        return node.getCharacter();
      }
    }
    return null;
  }

  public boolean isEmpty() {
    return head == null;
  }

  public int getSize() {
    return size;
  }

  public Observer<CharacterQueue> getObserver() {
    return observer;
  }

  private static class Node {
    private Node next;
    private Character character;

    public Node(Character character) {
      this.character = character;
    }

    public Node getNext() {
      return next;
    }

    public void setNext(Node next) {
      this.next = next;
    }

    public Character getCharacter() {
      return character;
    }
  }
}
