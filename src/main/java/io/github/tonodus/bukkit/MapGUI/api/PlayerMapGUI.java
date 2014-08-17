package io.github.tonodus.bukkit.MapGUI.api;

import org.bukkit.entity.Player;

/**
 * Created by Tonodus (http://tonodus.github.io) on 17.08.2014.
 */
public interface PlayerMapGUI extends MapGUI {
    /**
     * Returns the cursor for this map
     *
     * @return the cursor
     */
    public Cursor getCursor();

    /**
     * Shows this MapGUI to the assigned player
     */
    @Override
    public void show();

    /**
     * hides this MapGUI from the assigned player, however {@link }
     */
    @Override
    public void hide();

    /**
     * Returns the player that is able to view this map
     *
     * @return the player this map is assigned to
     */
    public Player getAssignedPlayer();
}
