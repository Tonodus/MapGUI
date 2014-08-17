package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Window;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
class PlayerDrawHelper implements DrawHelper {
    private final WorkerThread worker;
    private BufferedImage inProcess;
    private Graphics2D graphics;
    private Window window = null;

    private MapCanvas canvas = null;

    private boolean needRedraw = false;

    private Player player;

    private volatile boolean finishedAsync = true;
    private final Runnable drawRunnable = new Runnable() {
        @Override
        public void run() {
            window.drawAsync(graphics);
            progressImage();
            finishedAsync = true;
        }
    };
    private int fps = 0;
    private long lastFpsSecond = -1;
    private volatile boolean finishedWindowChange = true;
    private volatile boolean needSend = false;

    public PlayerDrawHelper(int width, int height, WorkerThread worker, Player player) {
        this.worker = worker;
        this.player = player;
        this.inProcess = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.graphics = inProcess.createGraphics();
    }

    public void dispose() {
        worker.addToQueue(new Runnable() {
            @Override
            public void run() {
                graphics.dispose();
                inProcess = null;
                window = null;
                canvas = null;
            }
        });
    }

    private void setNewMapCanvas(MapCanvas newCanvas) {
        this.canvas = newCanvas;
        invalidate();
    }

    @Override
    public void setNewWindow(final Window window) {
        finishedWindowChange = false;
        worker.addToQueue(new Runnable() {
            @Override
            public void run() {
                PlayerDrawHelper.this.window = window;
                finishedWindowChange = true;
            }
        });
        invalidate();
    }

    @Override
    public void invalidate() {
        needRedraw = true;
    }

    private void drawWindow() {
        if (!needRedraw || !finishedAsync || !finishedWindowChange || window == null)
            return;
        needRedraw = false;

        window.updateSync();
        finishedAsync = false;
        worker.addToQueue(drawRunnable);
    }

    @Override
    public void onTick(MapView view, MapCanvas canvas, Player player) {
       /* //fps
        if (lastFpsSecond == -1)
            lastFpsSecond = System.currentTimeMillis();
        if (System.currentTimeMillis() - lastFpsSecond >= 1000) {
            lastFpsSecond -= 1000;
            System.out.println("FPS: " + fps);
            fps = 0;
        }
        fps++;
        //end fps*/

        drawWindow();

        if (this.canvas != canvas)
            setNewMapCanvas(canvas);

        if (needSend) {
            needSend = false;
            player.sendMap(this.canvas.getMapView());
        }
    }

    private void progressImage() {
        inProcess.flush();
        /*int w = inProcess.getWidth(), h = inProcess.getHeight();
        int[] data = new int[w * h];
        inProcess.getRGB(0, 0, w, h, data, 0, w);

        int[] bData = new int[w * h];

        for (int i = 0; i < data.length; i++) {
            bData[i] = MapPalette.matchColor(new Color(data[i], false));
        }

        inProcess.setRGB(0, 0, w, h, bData, 0, w);
        inProcess.flush();
*/
        canvas.drawImage(0, 0, inProcess);
        needSend = true;
    }
}
