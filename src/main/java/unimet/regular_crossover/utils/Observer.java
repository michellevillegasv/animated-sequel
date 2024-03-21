package unimet.regular_crossover.utils;

import java.util.function.Consumer;

public class Observer<T> {
  private Observer<T> nextObserver;
  private Consumer<T> consumer;

  public Observer() {
  }

  public Observer(Consumer<T> consumer) {
    this.consumer = consumer;
  }

  public void notify(T value) {
    if (consumer != null) {
      consumer.accept(value);
    }
    if (nextObserver != null) {
      nextObserver.notify(value);
    }
  }

  public void addObserver(Consumer<T> consumer) {
    if (nextObserver == null) {
      nextObserver = new Observer<>(consumer);
    } else {
      nextObserver.addObserver(consumer);
    }
  }
}
