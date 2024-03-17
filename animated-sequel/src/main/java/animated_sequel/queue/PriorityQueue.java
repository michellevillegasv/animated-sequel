/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animated_sequel.queue;
import animated_sequel.characters.Character;
/**
 *
 * @author USUARIO
 */

public class PriorityQueue<T> {
    private Node<T> head;
    private int size;

    public PriorityQueue() {
        head = null;
        size = 0;
    }
    
    public Node getHead(){
        return head;
    }

    public void add(T data, int priority) {
        Node<T> newNode = new Node<>(data, priority);

        if (head == null) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<T> curr = head;
            while (curr.getNext() != null) {
                curr = curr.getNext();
            }
            curr.setNext(newNode);
        }

        size++;
    }

    public T peek() {
        if (head == null) {
            return null;
        }
        return head.getData();
    }

    public T poll() {
        if (head == null) {
            return null;
        }

        T data = head.getData();
        head = head.getNext();
        size--;

        return data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
    public boolean remove(T element) {
        if (head == null) {
            return false;
        }

        if (head.getData().equals(element)) {
            head = head.getNext();
            size--;
            return true;
        }

        Node<T> curr = head;
        while (curr.getNext() != null && !curr.getNext().getData().equals(element)) {
            curr = curr.getNext();
        }

        if (curr.getNext() != null) {
            curr.setNext(curr.getNext().getNext());
            size--;
            return true;
        }

        return false;
    }
    
}
