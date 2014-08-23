package io.github.tonodus.bukkit.MapGUI.core;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.07.2014.
 */
public class WorkerThread implements Runnable {
    private final Queue<Runnable> toDo;
    private Thread thread;

    public WorkerThread() {
        toDo = new LinkedList<Runnable>();
    }

    public void start() {
        thread = new Thread(this, "WorkerThread");
        thread.start();
    }

    public void addToQueue(Runnable runnable) {
        synchronized (toDo) {
            toDo.add(runnable);
        }

        thread.resume();
    }

    public void stop() {
        thread.interrupt();
    }

    public void stopWhenFinished() {
        addToQueue(new Runnable() {
            @Override
            public void run() {
                WorkerThread.this.stop();
            }
        });
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Runnable run;
            synchronized (toDo) {
                run = toDo.poll();
            }
            if (run == null)
                Thread.currentThread().suspend();
            else
                run.run();
        }
    }
}
