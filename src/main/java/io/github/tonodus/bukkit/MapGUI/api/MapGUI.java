package io.github.tonodus.bukkit.MapGUI.api;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */

/**
 * The base interface
 */
public interface MapGUI extends InputListenerController<MapGUI> {
    /**
     * Sets the current displayed {@link Window window}
     *
     * @param window the window to be displayer
     */
    public void setWindow(Window window);

    /**
     * Returns the current displayed window
     *
     * @return the window
     */
    public Window getCurrentWindow();

    /**
     * Returns the cursor for this map
     *
     * @return the cursor
     */
    public Cursor getCursor();

    /**
     * Let the map gui redraw itself. Call this, when ever you changed something of the gui.
     */
    public void invalidate();

    /**
     * //TODO
     * NOT READY YET
     *
     * @param color
     */
    public void setBackground(DyeColor color);

    /**
     * Shows this gui to the given player.
     *
     * @param player the player to display the map to
     * @param force  whether the player can drop this map or select a other tool (no force)
     */
    public void show(Player player, boolean force);

    /**
     * Shows this gui to the given player, but doesn't force it.
     * See also {@link #show(org.bukkit.entity.Player, boolean) }
     *
     * @param player the player to display the map to
     */
    public void show(Player player);

    /**
     * hides this map from the player
     */
    public void hide();
}
