package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Cursor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
class MoveHelper {
    private static final int UNKNOWN = -Integer.MIN_VALUE;
    private final Plugin plugin;
    private final DefaultMapGui gui;
    private final Player player;
    private final float basePitch = 1;
    private float baseYaw;
    private BukkitRunnable resetRunnable = null;

    public MoveHelper(Plugin plugin, DefaultMapGui gui) {
        this.plugin = plugin;
        this.gui = gui;
        this.player = gui.assignedPlayer();
    }

    public void start() {
        resetRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                resetView();
            }
        };

        resetRunnable.runTaskTimer(plugin, 0, 10);
    }

    public void stop() {
        resetRunnable.cancel();
        resetRunnable = null;
        baseYaw = UNKNOWN;
    }

    private float calcYaw(PlayerMoveEvent event) {
        float b = -(event.getTo().getYaw() % 180);
        float a = -(event.getFrom().getYaw() % 180);
        if (a < 90 && b > 90)
            a += 180;
        else if (b < 90 && a > 90)
            b += 180;

        float yaw = a - b;
        if (yaw > 50 || yaw < -50)
            return 0;
        return yaw;
    }

    private float calcPitch(PlayerMoveEvent event) {
        float b = -(event.getTo().getPitch() % 180);
        float a = -(event.getFrom().getPitch() % 180);
        if (a < 90 && b > 90)
            a += 180;
        else if (b < 90 && a > 90)
            b += 180;

        float pitch = a - b;
        if (pitch > 50 || pitch < -50)
            return 0;
        return pitch;
    }

    public void onMove(final PlayerMoveEvent event) {
        if (!gui.isVisible() || event.getPlayer() != gui.assignedPlayer())
            return;

        float yaw = calcYaw(event);
        float pitch = calcPitch(event);

        Cursor c = gui.getCursor();
        int ox = c.getX(), oy = c.getY();
        int nx = Math.round(Math.max(-128, Math.min(127, ox + yaw))), ny = Math.round(Math.max(-128, Math.min(127, oy + pitch)));
        c.set(nx, ny);
        gui.inputController.onMove(ox, oy, nx, ny, gui);

        if (baseYaw == UNKNOWN)
            baseYaw = event.getFrom().getYaw();

        gui.invalidate();
    }

    private void resetView() {
        Location l = player.getLocation();
        l.setYaw(baseYaw);
        l.setPitch(basePitch);
        player.teleport(l);
    }
}
