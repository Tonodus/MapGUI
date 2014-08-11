package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public interface MouseListener<O> {
    public void onLeftClick(int x, int y, boolean withShift, O owner);

    public void onRightClick(int x, int y, boolean withShift, O owner);

    public void onMove(int oldX, int oldY, int newX, int newY, O owner);

}
