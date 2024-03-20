/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animated_sequel.queue;

/**
 *
 * @author USUARIO
 */

public class Node<T> {
    private T data;
    private int priority;
    private Node<T> next;
    private int counter;

    public Node(T data, int priority) {
        this.data = data;
        this.priority = priority;
        this.next = null;
        this.counter = 0;
    }

    public T getData() {
        return data;
    }

    public int getPriority() {
        return priority;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
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
