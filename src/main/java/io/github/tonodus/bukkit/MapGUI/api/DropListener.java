package io.github.tonodus.bukkit.MapGUI.api;

import org.bukkit.entity.Player;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
public interface DropListener {
    /**
     * Called when a player drops the map and so the map-gui, and the event may be cancelled
     *
     * @param me     the mapgui dropped
     * @param player the player who dropped the map
     * @return true if the event should be cancelled, false otherwise
     */
    boolean onPossibleDrop(MapGUI me, Player player);

    /**
     * Called when a player drops the map and so the map-gui, and it is definitely sure, that the map will be disposed.
     * <p/>
     * This will be called, right before the map will be hidden and disposed
     *
     * @param me     the map-gui
     * @param player the player
     */
    void onDispose(MapGUI me, Player player);
}
