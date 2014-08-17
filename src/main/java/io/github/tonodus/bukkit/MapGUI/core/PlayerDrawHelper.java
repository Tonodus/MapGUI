package io.github.tonodus.bukkit.MapGUI.core;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;

import java.awt.image.BufferedImage;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
class PlayerDrawHelper extends AbstractDrawHelper {
    private final Player player;

    public PlayerDrawHelper(Player player, int width, int height, WorkerThread worker) {
        super(width, height, worker);
        this.player = player;
    }

    @Override
    protected void progressImageAndSend(BufferedImage image, MapCanvas canvas) {
        image.flush();
        canvas.drawImage(0, 0, image);
        player.sendMap(canvas.getMapView());
    }
}
