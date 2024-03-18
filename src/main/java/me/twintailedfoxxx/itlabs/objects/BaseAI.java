package me.twintailedfoxxx.itlabs.objects;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class BaseAI extends Thread {
    protected ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    protected boolean running = false;

    @Override
    public void run() {
        while (running) {
            lock.writeLock().lock();
            try {
                move();
            } finally {
                lock.writeLock().unlock();
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopAI() throws InterruptedException {
        running = false;
        wait();
    }

    public void resumeAI() {
        running = true;
        notify();
    }

    public abstract void move();
}