package io.github.tonodus.bukkit.MapGUI.api;

import org.bukkit.entity.Player;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
public interface MapGUIStateListener {
    void onShow(MapGUI me);

    void onHide(MapGUI me);

    void onDispose(MapGUI me);

    /**
     * This is called right before the {@link io.github.tonodus.bukkit.MapGUI.api.SinglePlayerMapGUI MapGUI} disposes.
     * Hook this event to prevent the MapGUI from being dropped.
     *
     * @param me  the {@link io.github.tonodus.bukkit.MapGUI.api.MapGUI MapGui} tha will maybe dropped
     * @param who the {@link org.bukkit.entity.Player player} who caused this event, or null if it was caused because of other reasons
     * @return true if the drop should be cancelled, false otherwise
     */
    boolean shouldBlockDrop(MapGUI me, Player who);
}
