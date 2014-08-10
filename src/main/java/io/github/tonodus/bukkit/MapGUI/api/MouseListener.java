package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public interface MouseListener {
    public void onLeftClick(int x, int y, boolean withShift);

    public void onRightClick(int x, int y, boolean withShift);

    public void onMove(int oldX, int oldY, int newX, int newY);

}
