package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Window;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Tonodus (http://tonodus.github.io) on 17.08.2014.
 */
public abstract class AbstractDrawHelper implements DrawHelper {
    private final WorkerThread worker;

    private BufferedImage inProcess;
    private Graphics2D graphics;
    private Window window = null;

    private boolean needRedraw = false;

    private volatile boolean finishedAsync = true;
    private volatile boolean finishedWindowChange = true;

    public AbstractDrawHelper(int width, int height, WorkerThread worker) {
        this.worker = worker;
        this.inProcess = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.graphics = inProcess.createGraphics();
    }

    @Override
    public void onTick(MapView view, MapCanvas canvas, Player player) {
        if (!needRedraw || !finishedAsync || !finishedWindowChange || window == null)
            return;
        needRedraw = false;

        window.updateSync();
        finishedAsync = false;
        worker.addToQueue(new DrawRunnable(canvas));
    }

    private void drawAsync(MapCanvas canvas) {
        window.drawAsync(graphics);
        progressImageAndSend(inProcess, canvas);
        finishedAsync = true;
    }

    @Override
    public void setNewWindow(final Window window) {
        finishedWindowChange = false;
        worker.addToQueue(new Runnable() {
            @Override
            public void run() {
                AbstractDrawHelper.this.window = window;
                invalidate();
                finishedWindowChange = true;
            }
        });
    }

    @Override
    public void dispose() {
        worker.addToQueue(new Runnable() {
            @Override
            public void run() {
                graphics.dispose();
                graphics = null;
                inProcess = null;
                window = null;
            }
        });
    }

    @Override
    public void invalidate() {
        needRedraw = true;
    }

    protected abstract void progressImageAndSend(BufferedImage image, MapCanvas on);

    private class DrawRunnable implements Runnable {
        private final MapCanvas canvas;

        public DrawRunnable(MapCanvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void run() {
            drawAsync(canvas);
        }
    }
}
