package unimet.regular_crossover;

import java.util.function.Consumer;

public class CharacterQueue {
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

  public boolean isEmpty() {
    return head == null;
  }

  public int getSize() {
    return size;
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
