package io.github.tonodus.bukkit.MapGUI.api;

import org.bukkit.entity.Player;

/**
 * Created by Tonodus (http://tonodus.github.io) on 17.08.2014.
 */
public class MapGUIStateListenerAdapter implements MapGUIStateListener {
    @Override
    public void onShow(MapGUI me) {

    }

    @Override
    public void onHide(MapGUI me) {

    }

    @Override
    public void onDispose(MapGUI me) {

    }

    @Override
    public boolean shouldBlockDrop(MapGUI me, Player who) {
        return false;
    }
}
