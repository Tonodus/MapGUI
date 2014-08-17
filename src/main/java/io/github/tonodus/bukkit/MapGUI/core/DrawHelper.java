package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Window;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;

/**
 * Created by Tonodus (http://tonodus.github.io) on 17.08.2014.
 */
interface DrawHelper {
    void dispose();

    void setNewWindow(Window newWindow);

    void invalidate();

    void onTick(MapView view, MapCanvas canvas, Player player);
}
