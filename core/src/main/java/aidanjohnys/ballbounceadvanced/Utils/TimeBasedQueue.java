package aidanjohnys.ballbounceadvanced.Utils;

import java.util.LinkedList;

public class TimeBasedQueue<T> {
    private final LinkedList<Node<T>> buffer;

    public TimeBasedQueue() {
        this.buffer = new LinkedList<>();
    }

    public void offer(T element) {
        buffer.offer(new Node<>(element));
    }

    public float peekAliveTime() {
        assert buffer.peek() != null;
        return buffer.peek().getAliveTime();
    }

    public float getAliveTime(int index) {
        return buffer.get(index).getAliveTime();
    }

    public void updateDeltaTime(float delta) {
        for (Node<T> node : buffer) {
            node.incrementTime(delta);
        }
    }

    public void poll() {
        buffer.poll();
    }

    public T get(int index) {
        return buffer.get(index).getValue();
    }

    public T peekLast() {
        if (buffer.peekLast() != null) {
            return buffer.peekLast().getValue();
        }

        return null;
    }

    public int size() {
        return buffer.size();
    }
}

class Node<T> {
    private final T value;
    private float aliveTime;

    public Node(T value) {
        this.value = value;
        this.aliveTime = 0;
    }

    public T getValue() {
        return value;
    }

    public void incrementTime(float delta) {
        aliveTime += delta;
    }

    public float getAliveTime() {
        return aliveTime;
    }
}
