/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animated_sequel.queue;
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

    public void add(T data, int priority) {
        Node<T> newNode = new Node<>(data, priority);

        if (head == null || priority < head.getPriority()) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<T> curr = head;
            while (curr.getNext() != null && priority >= curr.getNext().getPriority()) {
                curr = curr.getNext();
            }
            newNode.setNext(curr.getNext());
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
}
